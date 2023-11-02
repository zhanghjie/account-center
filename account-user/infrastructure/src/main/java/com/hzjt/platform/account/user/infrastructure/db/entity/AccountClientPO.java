package com.hzjt.platform.account.user.infrastructure.db.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 表名：account_client
 *
 * @author zhanghaojie
 */
@TableName("account_client")
@Data
public class AccountClientPO {

    /**
     * 客户端编码
     */
    private String clientCode;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 授权秘钥
     */
    private String clientSecret;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 修改时间
     */
    private Date updateTime;


}