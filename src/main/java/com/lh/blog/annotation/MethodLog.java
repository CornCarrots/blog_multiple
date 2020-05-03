package com.lh.blog.annotation;

import java.lang.annotation.*;

/**
 * @author linhao
 * @date 2020/4/30 18:18
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodLog {
}
