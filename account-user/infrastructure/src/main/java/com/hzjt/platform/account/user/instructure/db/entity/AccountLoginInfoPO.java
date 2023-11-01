package com.hzjt.platform.account.user.instructure.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

import lombok.Data;


/**
 * 表名：account_login_info
 * 备注：用户登录信息表
 *
 * @author zhanghaojie
 */
@TableName("account_login_info")
@Data
public class AccountLoginInfoPO {

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 主键
     */
    private Long id;

    /**
     * 登出时间
     */
    private Date loginOutTime;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 刷新token
     */
    private String refreshToken;

    /**
     * token
     */
    private String token;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 用户id
     */
    private Long userId;


}