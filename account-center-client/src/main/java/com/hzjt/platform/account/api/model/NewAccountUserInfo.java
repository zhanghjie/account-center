package com.hzjt.platform.account.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * NewAccountUserInfo
 * 功能描述：新用户注册信息
 *
 * @author zhanghaojie
 * @date 2023/11/1 10:47
 */
public class NewAccountUserInfo implements Serializable {
    /**
     * 短信验证码
     */
    private String smsVerificationCode;

    /**
     * 用户地址
     */
    private String address;

    /**
     * 用户生日
     */
    private String birthday;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户身份证姓名
     */
    private String idCardName;

    /**
     * 用户身份证号
     */
    private String idCardNo;

    /**
     * 昵称
     */
    private String nickName;

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
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String passWord;

    /**
     * 账户中心颁布的客户端code
     */
    private String clientCode;

    public String getSmsVerificationCode() {
        return smsVerificationCode;
    }

    public void setSmsVerificationCode(String smsVerificationCode) {
        this.smsVerificationCode = smsVerificationCode;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }
}
