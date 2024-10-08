package com.hzjt.platform.account.api.config;

import com.hzjt.platform.account.api.AccountCenterUserService;
import com.hzjt.platform.account.api.interceptor.AccountCenterWebMvcConfigurerAdapter;
import com.hzjt.platform.account.api.interceptor.LoginCheckInterceptor;
import com.hzjt.platform.account.api.interceptor.PermissionsInterceptor;
import com.hzjt.platform.account.api.service.AccountCenterUserServiceImpl;
import com.hzjt.platform.account.api.service.RegistryInterceptedClassMethod;
import com.hzjt.platform.account.api.utils.SpringContextUtil;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

/**
 * AccountCenterAutoConfiguration
 * 功能描述：AccountCenterAutoConfiguration
 *
 * @author zhanghaojie
 * @date 2023/10/26 10:35
 */
@AutoConfiguration
@Configuration
@EnableConfigurationProperties
@PropertySource("classpath:application-account.properties")
public class AccountCenterAutoConfiguration {

    @Bean
    public RegistryInterceptedClassMethod registryInterceptedClassMethod() {
        return new RegistryInterceptedClassMethod();
    }

    @Bean
    public LoginCheckInterceptor registryCustomInterceptor() {
        return new LoginCheckInterceptor();
    }

    @Bean
    public PermissionsInterceptor registryPermissionsInterceptor() {
        return new PermissionsInterceptor();
    }

    @Bean
    public SpringContextUtil registrySpringContextUtil() {
        return new SpringContextUtil();
    }

    @Bean
    public AccountCenterWebMvcConfigurerAdapter registryAccountCenterWebMvcConfigurerAdapter() {
        return new AccountCenterWebMvcConfigurerAdapter();
    }

    @Bean
    public AccountCenterUserService registryAccountCenterUserService() {
        return new AccountCenterUserServiceImpl();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public FilterRegistrationBean<AccountCenterFilter> myFilter() {
//        FilterRegistrationBean<AccountCenterFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new AccountCenterFilter());
//        registrationBean.addUrlPatterns("/*");
//        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE); // 设置过滤器的执行顺序
//        return registrationBean;
//    }
}
