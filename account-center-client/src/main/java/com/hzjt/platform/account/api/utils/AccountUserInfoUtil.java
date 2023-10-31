package com.hzjt.platform.account.api.utils;

import com.hzjt.platform.account.api.model.AccountUserInfo;

import javax.servlet.http.HttpSession;

/**
 * AccountUserInfoUtil
 * 功能描述：TODO
 *
 * @author zhanghaojie
 * @date 2023/10/31 16:35
 */
public class AccountUserInfoUtil {

    private static final ThreadLocal<AccountUserInfo> sharedData = new ThreadLocal<>();

    /**
     *
     */
    public static AccountUserInfo getUserInfo() {
        return sharedData.get();
    }


    public static void setUserInfo(AccountUserInfo accountUserInfo) {
        sharedData.set(accountUserInfo);
    }

}
