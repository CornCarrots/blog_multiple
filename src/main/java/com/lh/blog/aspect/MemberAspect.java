package com.lh.blog.aspect;

import com.lh.blog.bean.Member;
import com.lh.blog.bean.User;
import com.lh.blog.exception.GlobalException;
import com.lh.blog.service.MemberService;
import com.lh.blog.service.UserService;
import com.lh.blog.util.CodeMsg;
import com.lh.blog.util.SpringContextUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author linhao
 * @date 2020/5/4 15:22
 * 当用户加分时，判断能否升级
 */
@Component
@Aspect
public class MemberAspect {
    private static final Logger logger = LoggerFactory.getLogger(ParamsAspect.class);

    @Pointcut("@annotation(com.lh.blog.annotation.AddScore)")
    public void addScore(){ }

    @AfterReturning("addScore()")
    public void score(JoinPoint joinPoint) throws Exception {
        try {
            User user= getUser(joinPoint);
            logger.info("[进入积分AOP] uid:{}, 积分:{}, 当前等级;{}", user.getId(), user.getScore(), user.getMid());
            MemberService memberService = SpringContextUtils.getBean(MemberService.class);
            UserService userService = SpringContextUtils.getBean(UserService.class);
            List<Member> members = memberService.list();
            for (Member member: members) {
                if (member.getId() == user.getMid()){
                    logger.info("[进入积分AOP] 用户没有升级 uid:{}, 等级:{}", user.getId(), user.getMid());
                    break;
                }
                if(member.getMin()<=user.getScore()&&member.getMax()>user.getScore())
                {
                    user.setMid(member.getId());
                    userService.update(user);
                    HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                    HttpSession session =request.getSession();
                    Object temp = session.getAttribute("user");
                    if (temp != null && ((User)temp).getId() ==  user.getId()) {
                        session.setAttribute("user", userService.get(user.getId()));
                    }
                    logger.info("[进入积分AOP] 用户升级 uid:{}, 等级:{}", user.getId(), user.getMid());
                    break;
                }
            }
        }catch (Exception e){
            logger.error("[进入积分AOP] 异常", e);
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
    }

    private User getUser(JoinPoint point){
        Object[] arguments = point.getArgs();
        Method method = getMethod(point);
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        String[] parameterNames = pnd.getParameterNames(method);
        int uid = 0;
        for (int i = 0; i < parameterNames.length; i++) {
            if ("uid".equals(parameterNames[i])){
                uid = (int) arguments[i];
                break;
            }
        }
        UserService userService = SpringContextUtils.getBean(UserService.class);
        return userService.get(uid);
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass()
                        .getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
            } catch (SecurityException | NoSuchMethodException e) {
                logger.error("", e);
            }
        }
        return method;
    }
}
