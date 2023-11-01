package com.hzjt.platform.account.api;

import com.hzjt.platform.account.api.model.AccountUserInfo;

/**
 * AccountUserPermission
 * 功能描述：用户权限校验提供接口
 *
 * @author zhanghaojie
 * @date 2023/10/31 19:43
 */
public interface AccountUserPermission {

    /**
     * 检验用户是否有权限
     */
    Boolean verifyThatTheUserHasPermissions(AccountUserInfo accountUserInfo) throws Exception;

}
