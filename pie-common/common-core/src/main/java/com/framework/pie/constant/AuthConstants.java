package com.framework.pie.constant;

/**
 * oAuth2.0 常量工具类
 */
public class AuthConstants {

    /**
     * 认证请求头key
     */
    public static final String AUTHORIZATION_KEY = "Authorization";



    /**
     * 认证请求头key
     */
    public static final String TOKEN_AUTHORIZATION_KEY = "TokenAuthorization";

    /**
     * JWT令牌前缀
     */
    public static final String AUTHORIZATION_PREFIX = "bearer ";


    /**
     * Basic认证前缀
     */
    public static final String BASIC_PREFIX = "Basic ";

    /**
     * JWT载体key
     */
    public static final String JWT_PAYLOAD_KEY = "payload";

    /**
     * JWT ID 唯一标识
     */
    public static final String JWT_JTI = "jti";

    /**
     * JWT ID 唯一标识
     */
    public static final String JWT_EXP = "exp";

    /**
     * 黑名单token前缀
     */
    public static final String TOKEN_BLACKLIST_PREFIX = "auth:token:blacklist:";

    /**
     * 用户ID
     */
    public static final String USER_ID_KEY = "userId";

    /**
     * 用户名称KEY
     */
    public static final String USER_NAME_KEY = "username";

    /**
     * 用户昵称
     */
    public static final String NICK_NAME_KEY = "nickName";

    /**
     * 超级管理员标识KEY
     */
    public static final String SUPER_ADMIN_KEY = "superAdminFlag";

    /**
     * 登录类型 PC 业务版、决策版
     */
    public static final String LOGIN_TYPE = "loginType";

    /**
     * 用户部门ID
     */
    public static final String DEPT_ID_KEY = "deptId";

    /**
     * 用户机构ID
     */
    public static final String ORG_ID_KEY = "orgId";

    /**
     * 用户机构行政区划ID
     */
    public static final String ORG_DISTRICT_ID = "orgDistrictId";

    /**
     * 当前登录clientId
     */
    public static final String CLIENT_ID_KEY = "client_id";

    /**
     * JWT存储权限前缀
     */
    public static final String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    public static final String JWT_AUTHORITIES_KEY = "authorities";

    /**
     * 授权类型，分两种模式 1、password为密码模式 2、refresh_token为刷新模式
     */
    public static final String GRANT_TYPE_KEY = "grant_type";

    /**
     * 获取refresh_token信息
     */
    public static final String REFRESH_TOKEN = "refresh_token";
}
