package com.hzjt.platform.account.api.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
    private LoginCheckInterceptor loginCheckInterceptor;

    @Autowired
    private PermissionsInterceptor permissionsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/**");  // 拦截所有的请求
        registry.addInterceptor(permissionsInterceptor)
                .addPathPatterns("/**");  // 拦截所有的请求
    }
}
