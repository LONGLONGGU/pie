package com.framework.pie.constant;

public class RedisConstants {

    //登录用户加密增强信息
    public static final String REDIS_USER_SALT = "USER_SALT_";

    //登录验证码前缀信息
    public static final String CAP_CODE_PREFIX = "CAP_CODE_";

    //登录验证码有效期，单位是秒
    public static final Integer CAP_CODE_EXPIRE = 300;
}
