package com.lh.blog.annotation;

import java.lang.annotation.*;

/**
 *
 * @author: linhao
 * @Date: 2020/05/02/21:57
 * @Description:
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Check {
    // 校验的参数名+":"+校验的规则
    String[] params();
    // 校验的参数
    int[] indexs() default {1};
}
