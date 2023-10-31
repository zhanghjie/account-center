package com.hzjt.platform.account.user.interfaces;

import com.hzjt.platform.account.api.AccountAuthorization;
import com.hzjt.platform.account.api.model.AccountUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 * 功能描述：TODO
 *
 * @author zhanghaojie
 * @date 2023/10/31 15:05
 */
@RestController
@RequestMapping("/test")
@AccountAuthorization
@Slf4j
public class TestController {

    @GetMapping("/info/business")
    public AccountUserInfo getUerInfoByUserId(Long userId) {
        log.info("TestController-getUserInfoByToken");
        AccountUserInfo accountUserInfo = new AccountUserInfo();
        accountUserInfo.setUserId(userId);
        accountUserInfo.setUsername("李四");
        return accountUserInfo;
    }
}
