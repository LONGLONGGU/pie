package com.framework.pie.auth.common.enums;
import lombok.Getter;

/**
 * 密码加密类型
 */
public enum PasswordEncoderTypeEnum {

    BCRYPT("{bcrypt}","BCRYPT加密"),
    NOOP("{noop}","无加密明文"),
    MD5("{md5}","MD5加密");

    @Getter
    private String prefix;

    PasswordEncoderTypeEnum(String prefix,String desc){
        this.prefix=prefix;
    }

}
