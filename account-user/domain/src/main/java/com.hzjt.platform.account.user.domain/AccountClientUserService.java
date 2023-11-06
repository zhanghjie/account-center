package com.hzjt.platform.account.user.domain;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hzjt.platform.account.api.model.AccountResponse;
import com.hzjt.platform.account.api.model.AccountUserInfo;
import com.hzjt.platform.account.user.infrastructure.db.entity.AccountClientUserPO;
import com.hzjt.platform.account.user.infrastructure.db.mapper.AccountClientUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * AccountClientUserService
 * 功能描述：账户中心用户和客户端的关系
 *
 * @author zhanghaojie
 * @date 2023/11/6 09:51
 */
@Service
@Slf4j
public class AccountClientUserService {

    @Autowired
    private AccountClientUserMapper accountClientUserMapper;

    public AccountResponse<Boolean> saveNewRelation(Long userId, String clientCode, Integer bindingType) {
        // 先根据userId和clientCode判断是否存在
        AccountResponse<AccountClientUserPO> poAccountResponse = selectByUserIdAndClientCode(userId, clientCode, 0);
        //存在则返回失败
        if (Objects.nonNull(poAccountResponse.getData())) {
            return AccountResponse.returnFail("100001", "该用户已经绑定过该客户端");
        }
        //不存在就直接保存
        AccountClientUserPO accountClientUserPO = new AccountClientUserPO();
        accountClientUserPO.setUserId(userId);
        accountClientUserPO.setClientCode(clientCode);
        accountClientUserPO.setBindingType(bindingType);
        accountClientUserPO.setCreateTime(new Date());
        accountClientUserPO.setUpdateTime(new Date());
        accountClientUserPO.setStatus(0);
        accountClientUserMapper.insert(accountClientUserPO);
        return AccountResponse.returnSuccess(true);

    }

    /**
     * 根据用户ID和客户端代码选择账户响应
     *
     * @param userId     用户ID
     * @param clientCode 客户端代码
     * @return 账户响应对象
     */
    public AccountResponse<AccountClientUserPO> selectByUserIdAndClientCode(Long userId, String clientCode, Integer status) {
        if (Objects.isNull(userId) && Objects.isNull(clientCode)) {
            return AccountResponse.returnFail("", "未绑定");
        }
        LambdaQueryWrapper<AccountClientUserPO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(AccountClientUserPO::getUserId, userId);
        lambdaQueryWrapper.eq(AccountClientUserPO::getClientCode, clientCode);
        lambdaQueryWrapper.eq(AccountClientUserPO::getStatus, status);
        AccountClientUserPO accountClientUserPO = accountClientUserMapper.selectOne(lambdaQueryWrapper);
        return AccountResponse.returnSuccess(accountClientUserPO);
    }

}
