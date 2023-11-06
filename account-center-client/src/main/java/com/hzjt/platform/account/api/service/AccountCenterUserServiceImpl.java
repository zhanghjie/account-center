package com.hzjt.platform.account.api.service;

import com.alibaba.fastjson.JSON;
import com.hzjt.platform.account.api.AccountCenterUserService;
import com.hzjt.platform.account.api.model.AccountResponse;
import com.hzjt.platform.account.api.model.AccountUserInfo;
import com.hzjt.platform.account.api.model.NewAccountUserInfo;
import com.hzjt.platform.account.api.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
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


    @Value("${account.setting.clientCode}")
    public String clientCode;

    /**
     * 根据账号密码进行登录
     *
     * @param username
     * @param password
     */
    @Override
    public AccountUserInfo userLogin(String username, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("clientCode", clientCode);
        params.put("username", username);
        params.put("password", password);
        String remoteServiceUrl = "/user/login"; // 远程服务的URL
        return HttpClientUtil.doGet(accountCenterUrl + remoteServiceUrl, params, AccountUserInfo.class);
    }

    public Boolean userLoginOut(String username) {
        Map<String, Object> params = new HashMap<>();
        params.put("clientCode", clientCode);
        params.put("username", username);
        String remoteServiceUrl = "/login/loginOut"; // 远程服务的URL
        return HttpClientUtil.doGet(accountCenterUrl + remoteServiceUrl, params, Boolean.class);
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
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        String remoteServiceUrl = "/user/info/getUerInfoByUserId"; // 远程服务的URL
        return HttpClientUtil.doGet(accountCenterUrl + remoteServiceUrl, params, AccountUserInfo.class);
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
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        String remoteServiceUrl = "/user/info/getUserInfoByUsername"; // 远程服务的URL
        return HttpClientUtil.doGet(accountCenterUrl + remoteServiceUrl, params, AccountUserInfo.class);

    }

    /**
     * 根据手机号获取用户信息
     *
     * @param phone
     */
    @Override
    public List<AccountUserInfo> getUserInfoByPhone(String phone) {
        Map<String, Object> params = new HashMap<>();
        params.put("phone", phone);
        String remoteServiceUrl = "/user/login/getUserInfoByPhone"; // 远程服务的URL
        String getResult = HttpClientUtil.doGet(accountCenterUrl + remoteServiceUrl, params);
        AccountResponse accountResponse = JSON.parseObject(getResult, AccountResponse.class);
        if (accountResponse.getIsSuccess() && accountResponse.getData() != null) {
            return JSON.parseArray(accountResponse.getData().toString(), AccountUserInfo.class);
        }
        return null;
    }

    /**
     * 注册用户
     *
     * @param user
     */
    @Override
    public Boolean registryUser(NewAccountUserInfo user) {
        user.setClientCode(clientCode);
        String remoteServiceUrl = "/registry/login/newUser"; // 远程服务的URL
        String result = HttpClientUtil.doJsonPost(accountCenterUrl + remoteServiceUrl, JSON.toJSONString(user), null);
        AccountResponse accountResponse = JSON.parseObject(result, AccountResponse.class);
        return (Boolean) accountResponse.getData();

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
    public String updateUserInfo(String code, String clientId, String secret) {
        return null;
    }


}
