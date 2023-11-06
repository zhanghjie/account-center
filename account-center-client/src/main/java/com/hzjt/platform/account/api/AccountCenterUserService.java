package com.hzjt.platform.account.api;

import com.hzjt.platform.account.api.model.AccountUserInfo;
import com.hzjt.platform.account.api.model.NewAccountUserInfo;

import java.util.List;

/**
 * AccountCenterUserService
 * 功能描述：AccountCenterUserService
 *
 * @author zhanghaojie
 * @date 2023/10/26 10:26
 */
public interface AccountCenterUserService {
    /**
     * 根据账号密码进行登录
     */
    AccountUserInfo userLogin(String username, String password);

    /**
     * 根据账号进行登出
     */
    Boolean userLoginOut(String username);

    /**
     * 根据手机号验证码进行登录
     */
    AccountUserInfo userLoginByPhone(String phone, String code);

    /**
     * 根据token获取用户信息
     */
    AccountUserInfo getUserInfoByToken(String token);

    /**
     * 根据token获取用户信息
     */
    AccountUserInfo getUserInfoByUserId(Long userId);

    /**
     * 根据phone发生短信验证码
     * type为验证码类型：登录、注册
     */
    String sendCode(String phone, int type);

    /**
     * 根据用户名获取用户信息
     */
    AccountUserInfo getUserInfoByUsername(String username);

    /**
     * 根据手机号获取用户信息
     */
    List<AccountUserInfo> getUserInfoByPhone(String phone);

    /**
     * 注册用户,code为注册时的验证码
     */
    Boolean registryUser(NewAccountUserInfo user);

    /**
     * 修改用户信息
     */
    AccountUserInfo updateUserInfo(AccountUserInfo user);

    /**
     * 根据Authorization code获取Account-token
     * 只有客户端获取过的Authorization code才可以
     */
    String updateUserInfo(String code, String clinetId, String secret);
}
