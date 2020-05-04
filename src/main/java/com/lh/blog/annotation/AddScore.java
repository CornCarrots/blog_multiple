package com.lh.blog.annotation;

import java.lang.annotation.*;

/**
 *
 * @author: linhao
 * @date: 2020/05/04/15:17
 * @description: 判断用户能否升级
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AddScore {
}
