package com.hzjt.platform.account.api.security;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * SpringContextUtil
 * 功能描述：SpringContextUtil
 *
 * @author zhanghaojie
 * @date 2023/10/30 09:29
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    public static Object getBean(Class classType) {
        return applicationContext.getBean(classType);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
