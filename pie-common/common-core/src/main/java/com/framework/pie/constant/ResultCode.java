package com.framework.pie.constant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode implements IResultCode {

    ACCESS_UNAUTHORIZED(10001, "访问未授权"),
    TOKEN_INVALID_OR_EXPIRED(10002, "token无效或已过期"),
    TOKEN_ACCESS_FORBIDDEN(10003, "token已被禁止访问"),
    REFRESH_TOKEN_INVALID_OR_EXPIRED(10004, "refreshToken无效或已过期");

    private Integer code;

    private String msg;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }


}
