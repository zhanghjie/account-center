package com.hzjt.platform.account.api.model;

import java.io.Serializable;

/**
 * AccountUserInfo
 * 功能描述：账户中心用户信息
 *
 * @author zhanghaojie
 * @date 2023/10/26 10:27
 */
public class AccountUserInfo implements Serializable {
    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户token
     */
    private String token;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户性别
     * 0:未知，1：男，2：女
     */
    private Integer sex;

    /**
     * 用户状态
     * 0:正常，1：禁用
     */
    private Integer status;

    /**
     * 用户类型
     * 0:普通用户，1：管理员
     */
    private Integer type;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户生日
     */
    private String birthday;

    /**
     * 用户地址
     */
    private String address;

    /**
     * 用户身份证姓名
     */
    private String idCardName;

    /**
     * 用户身份证号
     */
    private String idCardNo;

    /**
     * 用户注册时间
     */
    private String createTime;

    /**
     * 用户更新时间
     */
    private String updateTime;

    /**
     * 用户最后登录时间
     */
    private String lastLoginTime;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
