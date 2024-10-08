package com.hzjt.platform.account.user.interfaces;

import com.hzjt.platform.account.api.model.AccountUserInfo;
import com.hzjt.platform.account.api.model.NewAccountUserInfo;
import com.hzjt.platform.account.user.application.AccountUserBizServices;
import com.hzjt.platform.account.user.domain.AccountUserInfoService;
import com.hzjt.platform.account.api.model.AccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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

    @Autowired
    private AccountUserBizServices accountUserBizServices;

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

    /**
     * 授权登录
     */
    @GetMapping("login/getAccountCode")
    public void login(String userName, String password, String clientCode) {
//        log.info("UserController-login, {}, {}, {}", userName, password, clientCode);
//        return accountUserInfoService.checkLoginOAuth2(userName, password, clientCode);
    }


    /**
     * 直接登录方法
     *
     * @param userName   用户名
     * @param password   密码
     * @param clientCode 客户端代码
     * @return 登录结果
     */
    @GetMapping("login")
    public AccountResponse<AccountUserInfo> directLogin(String userName, String passWord, String clientCode) {
        log.info("UserController-directLogin, {}, {}, {}", userName, passWord, clientCode);
        return accountUserBizServices.doLogin(userName, passWord, clientCode);
    }


    /**
     * 登出方法
     *
     * @param userName     用户名
     * @param accountToken token
     * @param clientCode   客户端代码
     * @return 登录结果
     */
    @GetMapping("/login/loginOut")
    public AccountResponse<Boolean> loginOut(Long userId, String userName, String accountToken, String clientCode) {
        log.info("UserController-directLogin, {}, {}, {}", userName, accountToken, clientCode);
        return accountUserInfoService.loginOut(userId, userName, accountToken, clientCode);
    }


    /**
     * 注册新用户
     *
     * @param newAccountUserInfo 新用户的信息
     * @return 注册结果
     */
    @PostMapping("/registry/newUser")
    public AccountResponse<Boolean> registryNewUser(@RequestBody NewAccountUserInfo newAccountUserInfo) {
        return accountUserBizServices.registerNewUser(newAccountUserInfo);
    }


    /**
     * token获取用户信息
     */
    @GetMapping("/login/getUserInfoByToken")
    public AccountResponse<AccountUserInfo> getUserInfoByToken(String accountToken) {
        log.info("getAccountUerInfoByUserId-getUserInfoByToken:{}", accountToken);
        return accountUserInfoService.getAccountUserInfoByToken(accountToken);
    }


    @GetMapping("/info/getUerInfoByUserId")
    public AccountResponse<AccountUserInfo> getUerInfoByUserId(Long userId) {
        log.info("UserController-getUserInfoByToken:{}", userId);
        return accountUserInfoService.getAccountUerInfoByUserId(userId);
    }


    @PostMapping("/info/getUerInfoByUserIdList")
    public AccountResponse<List<AccountUserInfo>> getUerInfoByUserIdList(@RequestBody List<Long> userId) {
        log.info("UserController-getUerInfoByUserIdList:{}", userId);
        return accountUserInfoService.getUerInfoByUserIdList(userId);
    }


    @GetMapping("/info/getUserInfoByPhone")
    public AccountResponse<AccountUserInfo> getUserInfoByPhone(String phone) {
        log.info("UserController-getUserInfoByPhone:{}", phone);
        return accountUserInfoService.getUserInfoByPhone(phone);
    }

    @GetMapping("/info/getUserInfoByUsername")
    public AccountResponse<AccountUserInfo> getUserInfoByUsername(String userName) {
        log.info("UserController-getUserInfoByUsername:{}", userName);
        return accountUserInfoService.getUserInfoByUserName(userName);
    }
}
