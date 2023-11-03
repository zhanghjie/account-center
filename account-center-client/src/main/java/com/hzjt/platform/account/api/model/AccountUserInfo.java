package com.hzjt.platform.account.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * AccountUserInfo
 * 功能描述：账户中心用户信息
 *
 * @author zhanghaojie
 * @date 2023/10/26 10:27
 */
public class AccountUserInfo implements Serializable {

    private String accountToken;

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
    private String createTime;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户身份证姓名
     */
    private String idCardName;

    /**
     * 用户身份证号(加密后)
     */
    private Integer idCardNo;

    /**
     * 最后登录时间
     */
    private String lastLoginTime;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 手机号(加密后)
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
    private String updateTime;

    /**
     * 用户名
     */
    private String userName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName;
    }

    public Integer getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(Integer idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountToken() {
        return accountToken;
    }

    public void setAccountToken(String accountToken) {
        this.accountToken = accountToken;
    }
}
