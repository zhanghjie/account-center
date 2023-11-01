package com.hzjt.platform.account.api.service;

import com.hzjt.platform.account.api.AccountCenterUserService;
import com.hzjt.platform.account.api.model.AccountUserInfo;
import com.hzjt.platform.account.api.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * AccountCenterUserServiceImpl
 * 功能描述：AccountCenterUserServiceImpl
 *
 * @author zhanghaojie
 * @date 2023/10/26 10:37
 */
@Service
@Order(1)
public class AccountCenterUserServiceImpl implements AccountCenterUserService {

    @Value("${account.center.url}")
    private String accountCenterUrl;

    @Autowired
    private Environment environment;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 根据账号密码进行登录
     *
     * @param username
     * @param password
     */
    @Override
    public AccountUserInfo userLogin(String username, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        String remoteServiceUrl = "/user/login"; // 远程服务的URL
        return restTemplate.getForObject(accountCenterUrl + remoteServiceUrl, AccountUserInfo.class, params);
    }

    /**
     * 根据手机号验证码进行登录
     *
     * @param phone
     * @param code
     */
    @Override
    public AccountUserInfo userLoginByPhone(String phone, String code) {
        return null;
    }

    /**
     * 根据token获取用户信息
     *
     * @param token
     */
    @Override
    public AccountUserInfo getUserInfoByToken(String token) {
        Map<String, Object> params = new HashMap<>();
        params.put("accountToken", token);
        String remoteServiceUrl = "/user/login/getUserInfoByToken"; // 远程服务的URL
        return HttpClientUtil.doGet(accountCenterUrl + remoteServiceUrl, params, AccountUserInfo.class);
    }

    /**
     * 根据token获取用户信息
     *
     * @param userId
     */
    @Override
    public AccountUserInfo getUserInfoByUserId(Long userId) {
        return null;
    }

    /**
     * 根据phone发生短信验证码
     * type为验证码类型：登录、注册
     *
     * @param phone
     * @param type
     */
    @Override
    public String sendCode(String phone, int type) {
        return null;
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     */
    @Override
    public AccountUserInfo getUserInfoByUsername(String username) {
        return null;
    }

    /**
     * 根据手机号获取用户信息
     *
     * @param phone
     */
    @Override
    public AccountUserInfo getUserInfoByPhone(String phone) {
        return null;
    }

    /**
     * 注册用户,code为注册时的验证码
     *
     * @param user
     * @param code
     */
    @Override
    public AccountUserInfo registryUser(AccountUserInfo user, String code) {
        return null;
    }

    /**
     * 修改用户信息
     *
     * @param user
     */
    @Override
    public AccountUserInfo updateUserInfo(AccountUserInfo user) {
        return null;
    }

    /**
     * 根据Authorization code获取Account-token
     * 只有客户端获取过的Authorization code才可以
     *
     * @param code
     * @param clinetId
     * @param secret
     */
    @Override
    public String updateUserInfo(String code, String clinetId, String secret) {
        return null;
    }
}
