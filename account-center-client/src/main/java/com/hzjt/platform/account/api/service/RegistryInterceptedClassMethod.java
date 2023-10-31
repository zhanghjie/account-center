package com.hzjt.platform.account.api.service;

import com.hzjt.platform.account.api.AccountAuthorization;
import com.hzjt.platform.account.api.IgnoreAuthorization;
import com.hzjt.platform.account.api.InterceptedClass;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RegistryInterceptedClass
 * 功能描述：注册需要拦截类
 *
 * @author zhanghaojie
 * @date 2023/10/26 10:57
 */
@Component
public class RegistryInterceptedClassMethod implements ApplicationContextAware, BeanPostProcessor, ApplicationRunner {

    private ApplicationContext applicationContext;

    // 需要被拦截校验登录态的接口
    public static Set<Method> interceptedClassMethodList = new HashSet<>();

    public RegistryInterceptedClassMethod() {
    }

    private void registryInterceptedClass() {
        // 获取到所有的InterceptedClass实现类
        Map<String, InterceptedClass> beansOfType = applicationContext.getBeansOfType(InterceptedClass.class);
        if (CollectionUtils.isEmpty(beansOfType)) {
            return;
        }
        for (InterceptedClass interceptedClass : beansOfType.values()) {
            List<Class> classes = interceptedClass.interceptedClass();
            if (CollectionUtils.isEmpty(classes)) {
                continue;
            }
            // 递归class里的方法，判断方法是有带有@IgnoreAuthorization注解，如果有则不拦截
            for (Class aClass : classes) {
                extracted(aClass);
            }
        }
    }

    private void extracted(Class aClass) {
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(IgnoreAuthorization.class)) {
                continue;
            }
            interceptedClassMethodList.add(method);
        }
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        //在 postProcessAfterInitialization 中判断Bean是否带@AccountAuthorization注解
        if (bean.getClass().isAnnotationPresent(AccountAuthorization.class)) {
            // Bean带有@AccountAuthorization注解
            extracted(bean.getClass());
        }

        return bean;
    }

    /**
     * Set the ApplicationContext that this object runs in.
     * Normally this call will be used to initialize the object.
     * <p>Invoked after population of normal bean properties but before an init callback such
     * as {@link InitializingBean#afterPropertiesSet()}
     * or a custom init-method. Invoked after {@link ResourceLoaderAware#setResourceLoader},
     * {@link ApplicationEventPublisherAware#setApplicationEventPublisher} and
     * {@link MessageSourceAware}, if applicable.
     *
     * @param applicationContext the ApplicationContext object to be used by this object
     * @throws ApplicationContextException in case of context initialization errors
     * @throws BeansException              if thrown by application context methods
     * @see BeanInitializationException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        registryInterceptedClass();
    }



}
