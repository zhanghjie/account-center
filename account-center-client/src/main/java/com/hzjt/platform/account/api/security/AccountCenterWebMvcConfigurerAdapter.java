package com.hzjt.platform.account.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * WebMvcConfigurerAdapter
 * 功能描述：WebMvcConfigurerAdapter
 *
 * @author zhanghaojie
 * @date 2023/10/27 15:38
 */
@Configuration
public class AccountCenterWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Autowired
    private CustomInterceptor customInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customInterceptor)
                .addPathPatterns("/**");  // 拦截所有的请求
    }
}
