package com.hzjt.platform.account.api.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

/**
 * AccountResponse
 * 功能描述：TODO
 *
 * @author zhanghaojie
 * @date 2023/11/2 13:45
 */
public class AccountResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String SUCCESS_CODE = "success";
    //    @ApiModelProperty("状态码")
    @Getter
    private String code;
    //    @ApiModelProperty("信息说明")
    @Getter
    private String msg;
    //    @ApiModelProperty("状态")
    @Getter
    private Boolean isSuccess;
    //    @ApiModelProperty("数据内容")
    private T data;

    // 无参构造器
    public AccountResponse() {
    }

    private AccountResponse(String code) {
        this.code = code;
        setIsSuccess(code);
    }

    private AccountResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
        setIsSuccess(code);
    }

    private AccountResponse(String code, T t) {
        this.code = code;
        setIsSuccess(code);
        this.data = t;
    }

    private AccountResponse(String code, String msg, T t) {
        this.code = code;
        this.msg = msg;
        setIsSuccess(code);
        this.data = t;
    }

    public static <T> AccountResponse<T> returnSuccess() {
        return new AccountResponse(SUCCESS_CODE);
    }

    public static <T> AccountResponse<T> returnSuccess(T t) {
        return new AccountResponse(SUCCESS_CODE, t);
    }

    public static <T> AccountResponse<T> returnFail(String code, String msg) {
        return new AccountResponse(code, msg);
    }

    public static <T> AccountResponse<T> returnFail(String code, String msg, T t) {
        return new AccountResponse(code, msg, t);
    }


    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private void setIsSuccess(String code) {
        if (Objects.equals(code, SUCCESS_CODE)) {
            this.isSuccess = true;
        } else {
            this.isSuccess = false;
        }
    }
}
