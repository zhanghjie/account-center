package com.hzjt.platform.account.user.domain;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hzjt.platform.account.api.exception.AccountCenterException;
import com.hzjt.platform.account.api.model.AccountUserInfo;
import com.hzjt.platform.account.api.model.NewAccountUserInfo;
import com.hzjt.platform.account.user.instructure.db.entity.AccountLoginInfoPO;
import com.hzjt.platform.account.user.instructure.db.entity.AccountUserPO;
import com.hzjt.platform.account.user.instructure.db.mapper.AccountLoginInfoMapper;
import com.hzjt.platform.account.user.instructure.db.mapper.AccountUserMapper;
import com.hzjt.platform.account.user.instructure.utils.AccountThreadPoolManager;
import com.hzjt.platform.account.user.instructure.utils.EncryptUtils;
import com.hzjt.platform.account.api.utils.TokenAlgorithmUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

    @Autowired
    private AccountLoginInfoMapper accountLoginInfoMapper;


    /**
     * OAuth2登录校验
     *
     * @param clientCode
     * @param password
     * @param username
     * @return 用户信息
     */
    public String checkLoginOAuth2(String username, String password, String clientCode) {
        // 检验登录密码
        check(username, password);
        // 获取account_code
        return TokenAlgorithmUtils.encrypt(String.valueOf(username));
    }


    /**
     * 根据用户ID获取用户信息
     *
     * @param clientCode
     * @param password
     * @param username
     * @return 用户信息
     */
    public AccountUserInfo doLogin(String username, String password, String clientCode) {
        // 账号密码检验成功，单点登录
        AccountUserPO accountUserPO = check(username, password);
        String accountToken = accountUserPO.getAccountToken();
        String refreshToken = accountUserPO.getRefreshToken();
        AccountUserInfo result = new AccountUserInfo();
        BeanUtils.copyProperties(accountUserPO, result);
        result.setAccountToken(accountToken);
        saveTokenInfo(clientCode, accountUserPO, accountToken, refreshToken);
        return result;
    }

    private void saveTokenInfo(String clientCode, AccountUserPO accountUserPO, String accountToken, String refreshToken) {
        AccountThreadPoolManager.submit(() -> {
            AccountLoginInfoPO accountLoginInfoPO = new AccountLoginInfoPO();
            accountLoginInfoPO.setCreateTime(new Date());
            accountLoginInfoPO.setUpdateTime(new Date());
            accountLoginInfoPO.setLoginTime(new Date());
            accountLoginInfoPO.setRefreshToken(refreshToken);
            accountLoginInfoPO.setToken(accountToken);
            accountLoginInfoPO.setUserId(accountUserPO.getId());
            accountLoginInfoPO.setClientCode(clientCode);
            // 往date里加2小时
            accountLoginInfoPO.setTokenValidityTime(new Date(System.currentTimeMillis() + 2 * 60 * 60 * 1000));
            accountLoginInfoMapper.insert(accountLoginInfoPO);
        });
    }

    /**
     * 根据用户token获取用户信息
     *
     * @param accountToken 用户ID
     * @return 用户信息
     */
    public AccountUserInfo getAccountUserInfoByToken(String accountToken) {
        LambdaQueryWrapper<AccountLoginInfoPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AccountLoginInfoPO::getToken, accountToken);
        AccountLoginInfoPO accountLoginInfoPOS = accountLoginInfoMapper.selectOne(queryWrapper);
        if (accountLoginInfoPOS == null) {
            throw new AccountCenterException("用户未登录");
        }
        if (accountLoginInfoPOS.getLoginOutTime() != null || accountLoginInfoPOS.getCreateTime().getTime() < new Date().getTime()) {
            throw new AccountCenterException("登录已失效，请重新登录");
        }
        AccountUserPO accountUserPO = accountUserMapper.selectById(accountLoginInfoPOS.getUserId());
        return beanUtilsToInfo(accountUserPO);
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    public AccountUserInfo getAccountUerInfoByUserId(Long userId) {
        AccountUserPO accountUserPO = accountUserMapper.selectById(userId);
        if (Objects.isNull(accountUserPO)) {
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
        if (StringUtils.isNotBlank(accountUserInfo.getPassWord())) {
            //密码需要加密保存
            accountUserPO.setIdCardNo(EncryptUtils.encryptIdCareNo(accountUserInfo.getIdCardNo()));
        }
        // 手机是必填的
        accountUserPO.setPhone(EncryptUtils.encryptPhone(accountUserInfo.getPhone()));
        accountUserPO.setId(null);
        accountUserPO.setCreateTime(new Date());
        accountUserPO.setUpdateTime(new Date());
        return accountUserMapper.insert(accountUserPO) > 0;
    }


    private AccountUserInfo beanUtilsToInfo(AccountUserPO accountUserPO) {
        AccountUserInfo accountUserInfo = new AccountUserInfo();
        accountUserInfo.setUserId(accountUserPO.getId());
        BeanUtils.copyProperties(accountUserPO, accountUserInfo);
        return accountUserInfo;
    }


    /**
     * 检查用户名和密码是否匹配
     *
     * @param username 账户名
     * @param password 密码
     */
    private AccountUserPO check(String username, String password) {
        // 创建查询对象
        LambdaQueryWrapper<AccountUserPO> wrapper = new LambdaQueryWrapper<>();
        // 根据账户名查询
        wrapper.eq(AccountUserPO::getUserName, username);
        // 根据加密后的密码查询
        wrapper.eq(AccountUserPO::getPassWord, EncryptUtils.encryptUserPassWord(password));
        // 查询账户信息
        AccountUserPO accountUserPO = accountUserMapper.selectOne(wrapper);
        // 如果账户信息为空，则抛出异常
        if (accountUserPO == null) {
            throw new AccountCenterException("账号密码错误，请重新输入");
        }
        // 如果账户状态为停用，则抛出异常
        if (accountUserPO.getStatus() == 1) {
            throw new AccountCenterException("该账户已停用，联系管理员");
        }
        // 校验当前登录是否过期
        LambdaQueryWrapper<AccountLoginInfoPO> infoPOLambdaQueryWrapper = new LambdaQueryWrapper<>();
        infoPOLambdaQueryWrapper.eq(AccountLoginInfoPO::getUserId, accountUserPO.getId());
        infoPOLambdaQueryWrapper.isNull(AccountLoginInfoPO::getLoginOutTime);
        infoPOLambdaQueryWrapper.gt(AccountLoginInfoPO::getTokenValidityTime, new Date());
        List<AccountLoginInfoPO> accountLoginInfoPOS = accountLoginInfoMapper.selectList(infoPOLambdaQueryWrapper);
        if (CollectionUtils.isEmpty(accountLoginInfoPOS)) {
            throw new AccountCenterException("请勿重复登录");
        }

        return accountUserPO;
    }
}
