package com.hzjt.platform.account.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * com.hzjt.platform.account.user.AccountUserApplication
 * 功能描述：AccountUserApplication
 *
 * @author zhanghaojie
 * @date 2023/10/24 15:45
 */
@SpringBootApplication
@EnableScheduling
@PropertySource("classpath:application.yml")
public class AccountUserApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AccountUserApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountUserApplication.class, args);
    }
}
