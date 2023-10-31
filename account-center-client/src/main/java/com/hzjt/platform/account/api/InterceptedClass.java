package com.hzjt.platform.account.api;

import java.util.List;

/**
 * InterceptedClass
 * 功能描述：配置需要拦截类
 *
 * @author zhanghaojie
 * @date 2023/10/26 10:23
 */
public interface InterceptedClass {

    /**
     * 功能描述: 需要拦截的类
     *
     * @return List<Class>
     * @author zhanghaojie
     * @date 2023/10/26 10:23
     */
    List<Class> interceptedClass();
}
