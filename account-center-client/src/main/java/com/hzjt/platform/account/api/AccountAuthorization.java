package com.hzjt.platform.account.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AccountAuthorization
 * 功能描述：标注需要拦截的方法或类
 *
 * @author zhanghaojie
 * @date 2023/10/26 10:24
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccountAuthorization {

}
