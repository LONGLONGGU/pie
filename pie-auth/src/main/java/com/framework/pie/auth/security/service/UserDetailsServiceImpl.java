package com.framework.pie.auth.security.service;

import com.alibaba.fastjson.JSONObject;
import com.framework.pie.auth.common.enums.OAuthClientEnum;
import com.framework.pie.auth.feign.SysUserFeignClient;
import com.framework.pie.auth.model.OAuthSysUser;
import com.framework.pie.auth.model.OAuthUserDetails;
import com.framework.pie.http.HttpResult;
import com.framework.pie.web.utils.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 从数据库获取用户信息，用于和前端传过来的用户信息进行密码判读
 * @author haoxr
 * @date 2020-05-27
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserFeignClient sysUserFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String clientId = JwtUtils.getOAuthClientId();
        OAuthClientEnum client = OAuthClientEnum.getByClientId(clientId);

        HttpResult result;
        OAuthUserDetails oauthUserDetails = null;
        switch (client) {
            default:
                result = sysUserFeignClient.getSysUserByName(username);
                if (result.getCode() == 200) {
                    OAuthSysUser sysUser = JSONObject.parseObject(JSONObject.toJSONString(result.getData()),OAuthSysUser.class);
                    oauthUserDetails = new OAuthUserDetails(sysUser);
                }
                break;
        }
        if (oauthUserDetails == null || oauthUserDetails.getId() == null) {
            throw new UsernameNotFoundException("用户帐号不存在!");
        } else if (!oauthUserDetails.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!oauthUserDetails.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!oauthUserDetails.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        } else if(!oauthUserDetails.isOrgStatus()){
            throw new RuntimeException("机构已停用,请联系服务提供商！");
        }
        return oauthUserDetails;
    }

}
