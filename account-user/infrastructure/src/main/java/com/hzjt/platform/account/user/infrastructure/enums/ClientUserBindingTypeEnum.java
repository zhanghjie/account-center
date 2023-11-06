package com.hzjt.platform.account.user.infrastructure.enums;

/**
 * ClientUserBindingTypeEnum
 * 功能描述：绑定方式
 *
 * @author zhanghaojie
 * @date 2023/11/6 10:39
 */
public enum ClientUserBindingTypeEnum {
    REGISTRY(0, "注册"),
    LOGIN(1, "登录"),
    ;

    private Integer code;
    private String desc;

    ClientUserBindingTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
