package com.lh.blog.annotation;

import com.lh.blog.bean.Manager;

import java.lang.annotation.*;

/**
 * @author linhao
 * @date 2020/4/30 18:18
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {
    String operation() default "";
    String module() default "";
    int mid() default 0;
}
