package com.framework.pie.admin.service;

import com.framework.pie.admin.model.SysLoginLog;
import com.framework.pie.mybatis.service.CurdService;

public interface SysLoginLogService extends CurdService<SysLoginLog> {

    /**
     * 记录登录日志
     * @param userName
     * @param ip
     * @return
     */
    int writeLoginLog(String userName, String ip);

    /**
     * 记录登出日志
     * @param userName
     * @param ip
     * @return
     */
    int writeLoginOut(String userName, String ip);

}
