package com.hzjt.platform.account.user.infrastructure.db.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
     * OAuth登录中间code
     */
    private String accountCode;

    /**
     * 执行登录的客户端
     */
    private String clientCode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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


    /**
     * token有效时间
     */
    private Date tokenValidityTime;

    /**
     * 登录渠道
     */
    private String loginChannel;
}