package com.aka.demo.common.annotation;

import java.lang.annotation.*;

/**
 * 标记 对方法返回结果封装
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ResponseResult {
}
