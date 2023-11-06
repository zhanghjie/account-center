package com.hzjt.platform.account.user.application;

import com.hzjt.platform.account.api.exception.AccountCenterException;
import com.hzjt.platform.account.api.model.AccountResponse;
import com.hzjt.platform.account.api.model.AccountUserInfo;
import com.hzjt.platform.account.api.model.NewAccountUserInfo;
import com.hzjt.platform.account.user.domain.AccountClientUserService;
import com.hzjt.platform.account.user.domain.AccountUserInfoService;
import com.hzjt.platform.account.user.infrastructure.db.entity.AccountClientUserPO;
import com.hzjt.platform.account.user.infrastructure.enums.ClientUserBindingTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * AccountUserBizServices
 * 功能描述：AccountUserBizServices
 *
 * @author zhanghaojie
 * @date 2023/11/6 10:19
 */
@Service
public class AccountUserBizServices {
    @Autowired
    private AccountUserInfoService accountUserInfoService;

    @Autowired
    private AccountClientUserService accountClientUserService;

    /**
     * 注册新用户
     *
     * @param accountUserInfo 账户用户信息
     * @return 注册结果
     * @throws AccountCenterException 注册失败异常
     */
    public AccountResponse<Boolean> registerNewUser(NewAccountUserInfo accountUserInfo) {
        // 保存新用户
        AccountResponse<Long> accountResponse = accountUserInfoService.saveNewUser(accountUserInfo);

        // 判断保存结果是否成功或者账号ID是否为空
        if (!accountResponse.getIsSuccess() || Objects.isNull(accountResponse.getData())) {
            // 注册失败，抛出异常
            throw new AccountCenterException("创建新用户失败");
        }

        // 保存新用户与客户端用户的关系
        return accountClientUserService.saveNewRelation(accountResponse.getData(), accountUserInfo.getClientCode(), ClientUserBindingTypeEnum.REGISTRY.getCode());
    }

    /**
     * 登录方法
     *
     * @param userName   用户名
     * @param passWord   密码
     * @param clientCode 客户端代码
     * @return 登录结果
     */
    public AccountResponse<AccountUserInfo> doLogin(String userName, String passWord, String clientCode) {
        AccountResponse<AccountUserInfo> infoAccountResponse = accountUserInfoService.doLogin(userName, passWord, clientCode);

        // 判断保存结果是否成功或者账号ID是否为空
        if (!infoAccountResponse.getIsSuccess() || Objects.isNull(infoAccountResponse.getData())) {
            return infoAccountResponse;
        }

        // 保存新用户与客户端用户的关系（以登录方式）
        accountClientUserService.saveNewRelation(infoAccountResponse.getData().getUserId(), clientCode, ClientUserBindingTypeEnum.LOGIN.getCode());

        return infoAccountResponse;
    }


}
