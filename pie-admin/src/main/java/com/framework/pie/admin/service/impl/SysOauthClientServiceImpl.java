package com.framework.pie.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.framework.pie.admin.dao.SysOauthClientMapper;
import com.framework.pie.admin.model.SysOauthClient;
import com.framework.pie.admin.service.SysOauthClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * <p>
 * 客户端信息表 服务实现类
 * </p>
 *
 * @author houjh
 * @since 2021-08-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SysOauthClientServiceImpl extends ServiceImpl<SysOauthClientMapper, SysOauthClient> implements SysOauthClientService {

    @Resource
    private SysOauthClientMapper sysOauthClientMapper;

    //查询方法都通过lambda表达式的方式进行处理
    @Override
    public SysOauthClient getByClientId(String clientId) {
        LambdaQueryWrapper<SysOauthClient> queryWrapper = new LambdaQueryWrapper<SysOauthClient>().eq(SysOauthClient::getClientId,clientId);
        return sysOauthClientMapper.selectOne(queryWrapper);
    }
}
