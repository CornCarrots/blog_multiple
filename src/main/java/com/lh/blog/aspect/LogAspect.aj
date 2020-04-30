package com.lh.blog.aspect;

import com.lh.blog.bean.Log;
import com.lh.blog.bean.Module;
import com.lh.blog.bean.Operation;
import com.lh.blog.service.LogService;
import com.lh.blog.service.ModuleService;
import com.lh.blog.service.OperationService;
import com.lh.blog.util.SpringContextUtils;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.List;

/**
 * @Auther: linhao
 * @Date: 2020/04/30/18:09
 * @Description:
 */
public aspect LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    public pointcut AddLog(): @annotation(com.lh.blog.annotation);

    void before(): AddLog(){
        ModuleService moduleService = SpringContextUtils.getBean(ModuleService.class);
        OperationService operationService = SpringContextUtils.getBean(OperationService.class);
        LogService logService = SpringContextUtils.getBean(LogService.class);
        Object[] args = thisJoinPoint.getArgs();
//        MethodSignature methodSignature = (MethodSignature) thisJoinPoint.getSignature();
//        String[] parameterNames = methodSignature.getParameterNames();
        // 获取管理员
        Integer mid = (Integer) args[0];
        String url = args[1].toString();
        String fun = args[2].toString();

        logger.info("[日志AOP] 管理员授权 mid:{}, url:{}, fun:{}", mid, url, fun);
        // 获取资源
        url = url.substring(url.lastIndexOf("/"));
        Module module = moduleService.getByURL(url).get(0);

        // 获取操作
        Operation operation = operationService.getByName(fun);

        // 添加日志
        Log log = new Log();
        log.setMid(mid);
        log.setCreateDate(new Date());
        log.setText(operation.getDesc() + " " + module.getDesc());
        logService.add(log);
    }

}
