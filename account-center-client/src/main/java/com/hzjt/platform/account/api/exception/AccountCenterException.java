package com.hzjt.platform.account.api.exception;

/**
 * AccountCenterException
 * 功能描述：TODO
 *
 * @author zhanghaojie
 * @date 2023/10/31 19:52
 */
public class AccountCenterException extends RuntimeException {

    String errorCode;

    public AccountCenterException(String message) {
        super(message);
        this.errorCode = "10001";
    }

    public AccountCenterException(String code, String message) {
        super(message);
        this.errorCode = code;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
