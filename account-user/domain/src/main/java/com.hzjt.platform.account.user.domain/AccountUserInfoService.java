package com.hzjt.platform.account.user.domain;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hzjt.platform.account.api.exception.AccountCenterException;
import com.hzjt.platform.account.api.model.AccountUserInfo;
import com.hzjt.platform.account.api.model.NewAccountUserInfo;
import com.hzjt.platform.account.user.instructure.db.entity.AccountUserPO;
import com.hzjt.platform.account.user.instructure.db.mapper.AccountUserMapper;
import com.hzjt.platform.account.user.instructure.utils.EncryptUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * AccountUserInfoService
 * 功能描述：用户信息服务
 *
 * @author zhanghaojie
 * @date 2023/11/1 10:12
 */
@Service
public class AccountUserInfoService {
    @Autowired
    private AccountUserMapper accountUserMapper;

    /**
     * OAuth2登录校验
     *
     * @param applicationId
     * @param password
     * @param username
     * @return 用户信息
     */
    public String checkLoginOAuth2(String username, String password, String applicationId) {
        LambdaQueryWrapper<AccountUserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountUserPO::getUserName, username);
        wrapper.eq(AccountUserPO::getPassWord, EncryptUtils.encryptUserPassWord(password));
        AccountUserPO accountUserPO = accountUserMapper.selectOne(wrapper);
        if (accountUserPO == null) {
            throw new AccountCenterException("账号密码错误，请重新输入");
        }
        if (accountUserPO.getStatus() == 1) {
            throw new AccountCenterException("该账户已停用，联系管理员");
        }
        return "";
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param applicationId
     * @param password
     * @param username
     * @return 用户信息
     */
    public AccountUserInfo checkLogin(String username, String password, String applicationId) {
        LambdaQueryWrapper<AccountUserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountUserPO::getUserName, username);
        wrapper.eq(AccountUserPO::getPassWord, EncryptUtils.encryptUserPassWord(password));
        AccountUserPO accountUserPO = accountUserMapper.selectOne(wrapper);
        if (accountUserPO == null) {
            throw new AccountCenterException("账号密码错误，请重新输入");
        }
        if (accountUserPO.getStatus() == 1) {
            throw new AccountCenterException("该账户已停用，联系管理员");
        }

        return new AccountUserInfo();
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    public AccountUserInfo getAccountUserInfo(Long userId) {
        AccountUserPO accountUserPO = accountUserMapper.selectById(userId);
        if (accountUserPO == null) {
            throw new AccountCenterException("用户不存在");
        }
        return beanUtilsToInfo(accountUserPO);
    }

    /**
     * 注册新用户
     *
     * @param accountUserInfo 用户信息
     * @return 用户信息
     */
    public Boolean registerNewUser(NewAccountUserInfo accountUserInfo) {
        // 检验手机验证码是否正确
        // todo

        if (Objects.isNull(accountUserInfo)) {
            throw new AccountCenterException("用户信息不能为空");
        }
        if (StringUtils.isBlank(accountUserInfo.getPhone())) {
            throw new AccountCenterException("用户手机号不可为空");
        }
        LambdaQueryWrapper<AccountUserPO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AccountUserPO::getPhone, accountUserInfo.getPhone());
        AccountUserPO accountUserPO = accountUserMapper.selectOne(wrapper);
        if (Objects.isNull(accountUserPO)) {
            throw new AccountCenterException("用户已存在");
        }
        BeanUtils.copyProperties(accountUserInfo, accountUserPO);
        if (StringUtils.isNotBlank(accountUserInfo.getPassWord())) {
            //密码需要加密保存
            accountUserPO.setPassWord(EncryptUtils.encryptUserPassWord(accountUserInfo.getPassWord()));
        }
        accountUserPO.setPhone(EncryptUtils.encryptPhone(accountUserInfo.getPhone()));
        accountUserPO.setId(null);
        accountUserPO.setCreateTime(new java.util.Date());
        accountUserPO.setUpdateTime(new java.util.Date());
        return accountUserMapper.insert(accountUserPO) > 0;
    }


    private AccountUserInfo beanUtilsToInfo(AccountUserPO accountUserPO) {
        AccountUserInfo accountUserInfo = new AccountUserInfo();
        accountUserInfo.setUserId(accountUserPO.getId());
        BeanUtils.copyProperties(accountUserPO, accountUserInfo);
        return accountUserInfo;
    }


}
