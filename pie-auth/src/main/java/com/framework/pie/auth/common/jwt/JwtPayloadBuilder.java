package com.framework.pie.auth.common.jwt;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 描述: 生成JWT令牌
 * 创建时间: 2021/08/04
 * @author houjh
 */
public class JwtPayloadBuilder {


    private Map<String, Object> payload = new HashMap<>();


    /**
     * jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
     **/
    private String jti = IdUtil.simpleUUID();

    /**
     * jwt的签发时间
     **/
    private LocalDateTime iat = LocalDateTime.now();

    /**
     * jwt的过期时间，这个过期时间必须要大于签发时间
     **/
    private LocalDateTime exp;

    /**
     * 权限集
     */
    private Set<String> authorities = new HashSet<>();

    /**
     * 附加的属性
     */
    private Map<String, String> additional;


    public JwtPayloadBuilder exp(int seconds) {
        this.exp = this.iat.plusSeconds(seconds);
        return this;
    }

    public JwtPayloadBuilder authorities(Set<String> authorities) {
        this.authorities = authorities;
        return this;
    }

    public JwtPayloadBuilder additional(Map<String, String> additional) {
        this.additional = additional;
        return this;
    }

    public String builder() {
        payload.put("jti", jti);
        payload.put("iat", this.iat.toEpochSecond(ZoneOffset.of("+8")));
        payload.put("exp", this.exp.toEpochSecond(ZoneOffset.of("+8")));
        if (CollectionUtil.isNotEmpty(additional)) {
            payload.putAll(additional);
        }
        payload.put("authorities", this.authorities.toArray());
        return JSONUtil.toJsonStr(payload);
    }


}
