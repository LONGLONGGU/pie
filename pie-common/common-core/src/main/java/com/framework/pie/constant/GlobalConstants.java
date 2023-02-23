package com.framework.pie.constant;

public class GlobalConstants {

    public static final Integer STATUS_YES = 1;

    public static final String DEFAULT_USER_PASSWORD = "123456";

    public static final String URL_PERM_ROLES_KEY = "system:perm_roles_rule:url:";

    //超级管理员角色名称
    public static final String SUPER_ADMIN_ROLE = "SuperAdmin";

    //用户密码加密后密码默认长度信息
    public static final Integer PWD_LENGTH = 32;

    //加密增强分隔符
    public static final String PWD_SPLIT = "|";

    //删除标识-数据正常状态
    public static final byte DEL_FLAG_NORMAL = 1;

    //删除标识-数据删除状态
    public static final byte DEL_FLAG_DELETE = 0;

    //操作标识-添加
    public static final String OPT_FLAG_ADD = "ADD";

    //操作标识-修改
    public static final String OPT_FLAG_UPDATE = "UPDATE";

    //创建机构时默认创建的部门名称
    public static final String DEFAULT_ADMIN_DEPT_NAME = "系统管理";
}
