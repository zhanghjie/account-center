package com.hzjt.platform.account.user.interfaces;

import com.hzjt.platform.account.api.AccountCenterUserService;
import com.hzjt.platform.account.api.model.AccountUserInfo;
import com.hzjt.platform.account.api.model.NewAccountUserInfo;
import com.hzjt.platform.account.user.domain.AccountUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    private AccountUserInfoService accountUserInfoService;

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
    public String login(String userName, String password, String applicationId) {
        log.info("UserController-login, {}, {}, {}", userName, password, applicationId);
        return accountUserInfoService.checkLoginOAuth2(userName, password, applicationId);
    }

    @GetMapping("login")
    public AccountUserInfo directLogin(String userName, String password, String applicationId) {
        log.info("UserController-directLogin, {}, {}, {}", userName, password, applicationId);
        return accountUserInfoService.checkLogin(userName, password, applicationId);
    }

    @PostMapping("/registry/newUser")
    public Boolean registryNewUser(@RequestBody NewAccountUserInfo newAccountUserInfo) {
        return accountUserInfoService.registerNewUser(newAccountUserInfo);
    }

    @GetMapping("/login/getUserInfoByToken")
    public AccountUserInfo login12(String accountToken) {
        log.info("UserController-getUserInfoByToken");
        AccountUserInfo accountUserInfo = new AccountUserInfo();
        accountUserInfo.setUserId(123L);
        accountUserInfo.setUserName("张三");
        return accountUserInfo;
    }

    @GetMapping("/info/getUerInfoByUserId")
    public AccountUserInfo getUerInfoByUserId(Long userId) {
        log.info("UserController-getUserInfoByToken");
        AccountUserInfo accountUserInfo = new AccountUserInfo();
        accountUserInfo.setUserId(userId);
        accountUserInfo.setUserName("李四");
        return accountUserInfo;
    }
}
