package com.hzjt.platform.account.api.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public static <T> List<T> getListBean(Class<T> type) {
        Map<String, T> beansOfType = applicationContext.getBeansOfType(type);
        if (beansOfType.size() > 0) {
            return (List<T>) beansOfType.values();
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
