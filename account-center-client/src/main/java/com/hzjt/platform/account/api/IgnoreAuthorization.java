package com.hzjt.platform.account.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * IgnoreAuthorization
 * 功能描述：标注不需要拦截的方法
 *
 * @author zhanghaojie
 * @date 2023/10/26 10:25
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuthorization {

}