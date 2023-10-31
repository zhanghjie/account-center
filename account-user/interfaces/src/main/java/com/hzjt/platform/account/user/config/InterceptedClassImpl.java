package com.hzjt.platform.account.user.config;

import com.hzjt.platform.account.api.InterceptedClass;
import com.hzjt.platform.account.user.interfaces.UserController;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * InterceptedClassImpl
 * 功能描述：配置需要拦截类
 *
 * @author zhanghaojie
 * @date 2023/10/26 13:54
 */
@Service
public class InterceptedClassImpl implements InterceptedClass {
    /**
     * 功能描述: 需要拦截的类
     *
     * @return List<Class>
     * @author zhanghaojie
     * @date 2023/10/26 10:23
     */
    @Override
    public List<Class> interceptedClass() {
        List<Class> classList = new ArrayList<>();
        classList.add(UserController.class);
        return classList;
    }
}
