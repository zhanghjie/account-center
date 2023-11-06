package com.hzjt.platform.account.user.infrastructure.db.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 表名：account_client_user
 * 备注：用户中心客户端关联表
 *
 * @author zhanghaojie
 */
@TableName("account_client_user")
@Data
public class AccountClientUserPO {

    /**
     * 绑定时间
     */
    private Date bindingTime;

    /**
     * 绑定方式
     */
    private Integer bindingType;

    /**
     * 客户端代码
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
     * 状态
     */
    private Integer status;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 用户id
     */
    private Long userId;


}