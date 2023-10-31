package com.hzjt.platform.account.user.interfaces;

import com.hzjt.platform.account.api.model.AccountUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * UserController
 * 功能描述： UserController
 *
 * @author zhanghaojie
 * @date 2023/10/24 16:10
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @GetMapping("login/phone")
    public void loginByPhone(HttpServletResponse response) {
        // 根据手机号查询用户信息
        // 校验验证码
        // 生成token
        // 将token存入redis
        // 将token存入cookie
        // 将token返回给前端
        log.info("UserController-loginByPhone");
    }

    @GetMapping("login")
    public void login(HttpServletResponse response) {
        log.info("UserController-login");
    }

    @GetMapping("/login/{name}")
    public void login12(HttpServletResponse response, @PathVariable String name) {
        log.info("UserController-login2");
    }

    @GetMapping("/login/getUserInfoByToken")
    public AccountUserInfo login12(String accountToken) {
        log.info("UserController-getUserInfoByToken");
        AccountUserInfo accountUserInfo = new AccountUserInfo();
        accountUserInfo.setToken(accountToken);
        accountUserInfo.setUserId(123L);
        accountUserInfo.setUsername("张三");
        return accountUserInfo;
    }

    @GetMapping("/info/getUerInfoByUserId")
    public AccountUserInfo getUerInfoByUserId(Long userId) {
        log.info("UserController-getUserInfoByToken");
        AccountUserInfo accountUserInfo = new AccountUserInfo();
        accountUserInfo.setUserId(userId);
        accountUserInfo.setUsername("李四");
        return accountUserInfo;
    }
}
