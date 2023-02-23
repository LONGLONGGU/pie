package com.framework.pie.auth.model;

import cn.hutool.core.collection.CollectionUtil;
import com.framework.pie.constant.GlobalConstants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 登录用户信息
 */
@Data
@NoArgsConstructor
public class OAuthUserDetails implements UserDetails {

    private String id;

    private String username;

    private String nickName;

    private String password;

    private Boolean enabled;

    private String clientId;

    private String salt;

    private String deptId;

    private String orgId;

    private String orgDistrictId;

    private String loginType;

    private Collection<SimpleGrantedAuthority> authorities;

    private boolean superAdminFlag;

    private boolean orgStatus;

    public OAuthUserDetails(OAuthSysUser user) {
        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setNickName(user.getNickName());
        this.setPassword(user.getSalt() + GlobalConstants.PWD_SPLIT + user.getPassword());
        this.setDeptId(user.getDeptId());
        this.setEnabled(GlobalConstants.STATUS_YES.equals(user.getStatus()));
        this.setSalt(user.getSalt());
        this.setSuperAdminFlag(user.isSuperAdminFlag());
        this.setOrgStatus(user.isOrgStatus());
        if (CollectionUtil.isNotEmpty(user.getRoleIds())) {
            authorities = new ArrayList<>();
            user.getRoleIds().forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        }
        this.setOrgId(user.getOrgId());
        this.setOrgDistrictId(user.getOrgDistrictId());
        this.setLoginType(user.getLoginType());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
