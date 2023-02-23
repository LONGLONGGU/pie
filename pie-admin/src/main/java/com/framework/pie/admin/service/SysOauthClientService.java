package com.framework.pie.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.framework.pie.admin.model.SysOauthClient;

/**
 * <p>
 * 客户端信息表 服务类
 * </p>
 *
 * @author houjh
 * @since 2021-08-04
 */
public interface SysOauthClientService extends IService<SysOauthClient> {

    /**
     * 通过clientId获取客户端信息
     * @param clientId
     * @return
     */
    SysOauthClient getByClientId(String clientId);
}

