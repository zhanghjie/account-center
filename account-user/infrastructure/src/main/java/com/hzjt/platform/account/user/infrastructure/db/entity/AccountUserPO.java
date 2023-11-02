package com.hzjt.platform.account.user.infrastructure.db.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hzjt.platform.account.api.utils.TokenAlgorithmUtils;
import lombok.Data;

import java.util.Date;


/**
 * 表名：account_user
 * 备注：用户主表
 *
 * @author zhanghaojie
 */
@TableName("account_user")
@Data
public class AccountUserPO {

    /**
     * 用户地址
     */
    private String address;

    /**
     * 用户生日
     */
    private String birthday;

    /**
     * 用户注册时间
     */
    private Date createTime;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * id
     */
    private Long id;

    /**
     * 用户身份证姓名
     */
    private String idCardName;

    /**
     * 用户身份证号
     */
    private String idCardNo;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户性别 0:未知，1：男，2：女
     */
    private Integer sex;

    /**
     * 用户状态 0:正常，1：禁用
     */
    private Integer status;

    /**
     * 用户类型 0:普通用户，1：管理员
     */
    private Integer type;

    /**
     * 用户更新时间
     */
    private Date updateTime;

    /**
     * 用户名
     */
    private String userName;

    public String getAccountToken() {
        return TokenAlgorithmUtils.encrypt(this.id + "");
    }

    public String getRefreshToken() {
        return TokenAlgorithmUtils.encrypt(this.phone + "");
    }
}