package com.framework.pie.auth.common.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.Map;
import java.util.Set;

/**
 * 描述: 生成accessToken信息
 * 创建时间: 2021-08-04
 * @author houjh
 */
@Component
public class JwtGenerator {


    @Autowired
    private KeyPair keyPair;

    public String createAccessToken(Set<String> authorities, Map<String, String> additional) {
        String payload = new JwtPayloadBuilder()
                .exp(12 * 3600) // 默认12小时
                .authorities(authorities)
                .additional(additional)
                .builder();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RsaSigner signer = new RsaSigner(privateKey);
        String accessToken = JwtHelper.encode(payload, signer).getEncoded();
        return accessToken;
    }


}



