package com.lh.blog.aspect;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.*;
import com.lh.blog.annotation.Check;
import com.lh.blog.enums.RuleEnum;
import com.lh.blog.exception.GlobalException;
import com.lh.blog.util.CodeMsg;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author linhao
 * @date 2020/5/2 21:57
 */
@Aspect
@Component
public class ParamsAspect {

    private static final char SEPARATOR = ':';

    /**
     * 字段信息
     */
    class FieldInfo {
        /**
         * 字段
         */
        String field;
        /**
         * 校验规则
         */
        RuleEnum rule;
    }
    private static final Logger logger = LoggerFactory.getLogger(ParamsAspect.class);

    @Pointcut("@annotation(com.lh.blog.annotation.Check)")
    public void paramCheck(){

    }

    @Around("paramCheck()")
    public Object paramsCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object obj;
            // 参数校验
            String res = doCheck(joinPoint);
            if (!"true".equals(res)) {
                String[] re = res.split(SEPARATOR + "");
                logger.info("[进行参数校验] 校验失败, 参数:{},原因:{}", re[0], re[1]);
                throw new GlobalException(CodeMsg.BIND_ERROR.fillArgs(re[0], re[1]));
            }
            // 通过校验，继续执行原有方法
            obj = joinPoint.proceed();
            return obj;
        }catch (Exception e){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
    }
    /**
     * 获取请求参数
     */
    public static Map<String, String> getAllRequestParam(HttpServletRequest request) {
        Map<String, String> res = new HashMap<>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (StrUtil.isEmpty(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }
    /**
     * 参数校验
     *
     * @param point ProceedingJoinPoint
     * @return 错误信息
     */
    private String doCheck(ProceedingJoinPoint point) {
        try {
            // 获取方法参数值
            Object[] arguments = point.getArgs();
            // 获取方法
            Method method = getMethod(point);
            // 是否存在注解
            if (!isCheck(method, arguments)){
                logger.info("[进行参数校验] 无需校验");
                return "true";
            }
            logger.info("[进行参数校验] 参数值:{}, 方法:{}", ArrayUtil.toString(arguments), method.getName());
            Check annotation = method.getAnnotation(Check.class);
            // 字段名
            String[] fields = annotation.params();
            // 参数位置
            Map<String, Object> map = (Map<String, Object>) arguments[annotation.index()];
            // 对参数进行校验
            for (String field : fields) {
                FieldInfo info = resolveField(field);
                Object vo = resolveVo(map, info.field);
                switch (info.rule){
                    case IsNotNull:
                        if (isNull(vo)){
                            return info.field + ":" + "不能为空";
                        }else{
                            break;
                        }
                    case IsNum:
                        if (!isNum(vo)){
                            return  info.field + ":" + "非法数字";
                        }else {
                            break;
                        }
                }
            }
            return "true";
        }catch (Exception e){
            logger.error("[进行参数校验] 异常", e);
            return "true";
        }
    }
    /**
     * 获取方法
     *
     * @param joinPoint ProceedingJoinPoint
     * @return 方法
     */
    private Method getMethod(ProceedingJoinPoint joinPoint) {
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

    /**
     * 判断是否符合参数规则
     *
     * @param method    方法
     * @param arguments 方法参数
     * @return 是否符合
     */
    private Boolean isCheck(Method method, Object[] arguments) {
        Boolean isCheck = Boolean.TRUE;
        // 只允许有一个参数
        if (!method.isAnnotationPresent(Check.class)
                || arguments == null) {
            isCheck = Boolean.FALSE;
        }
        return isCheck;
    }

    /**
     * 解析字段
     *
     * @param fieldStr   字段字符串
     * @return 字段信息实体类
     */
    private FieldInfo resolveField(String fieldStr) {
        FieldInfo fieldInfo = new FieldInfo();
        // 无特殊校验，只判断不为空
        if (!StrUtil.contains(fieldStr, SEPARATOR)){
            fieldInfo.rule = RuleEnum.IsNotNull;
            fieldInfo.field = fieldStr;
        }
        // 在不为空的基础上，特殊校验
        else {
            // 解析操作符
            String[] fields = StrUtil.splitToArray(fieldStr, SEPARATOR);
            fieldInfo.field = fields[0];
            fieldInfo.rule = RuleEnum.valueOf(fields[1]);
        }
        return fieldInfo;
    }

    /**
     * 解析 校验的实参
     * @return
     */
    private Object resolveVo(Map<String, Object> map, String key) {
        return map.get(key);
    }

    // -=================== 对不同类型的值进行校验 起 =======================

    /**
     * 是否不为空
     *
     * @param value       字段值
     * @return 是否不为空
     */
    private boolean isNull(Object value) {
        if (ObjectUtil.isNotNull(value)){
            if (value instanceof Collection){
                return CollUtil.isEmpty((Collection<?>) value);
            }
            else if (value instanceof Object[]){
                return ArrayUtil.isEmpty(value);
            }
            else if (value instanceof String){
                return StrUtil.isBlankOrUndefined((CharSequence) value);
            }
            return false;
        }else {
            return true;
        }
    }

    private boolean isNum(Object value){
        if (isNull(value)){
            return false;
        }
        return ObjectUtil.isValidIfNumber(value);
    }

}
