package com.hzjt.platform.account.user.infrastructure.db.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 表名：account_user_phone
 *
 * @author zhanghaojie
 */
@TableName("account_user_phone")
@Data
public class AccountUserPhonePO {


    /**
     * 客户端code
     */
    private String clientCode;

    private Date createTime;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 状态
     */
    private String status;

    private Date updateTime;

    /**
     * 用户id
     */
    private Long userId;


}