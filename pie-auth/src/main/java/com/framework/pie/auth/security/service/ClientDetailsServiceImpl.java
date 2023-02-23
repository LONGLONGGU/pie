package com.framework.pie.auth.security.service;
import com.alibaba.fastjson.JSONObject;
import com.framework.pie.auth.common.enums.PasswordEncoderTypeEnum;
import com.framework.pie.auth.feign.SysOauthClientFeignClient;
import com.framework.pie.auth.model.SysOauthClient;
import com.framework.pie.http.HttpResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;


@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

    @Autowired
    private SysOauthClientFeignClient sysOauthClientFeignClient;

    @Override
    @SneakyThrows
    public ClientDetails loadClientByClientId(String clientId) {
        try {
            HttpResult result = sysOauthClientFeignClient.getByClientId(clientId);
            if (result.getCode() == 200) {
                SysOauthClient client = JSONObject.parseObject(JSONObject.toJSONString(result.getData()),SysOauthClient.class);
                BaseClientDetails clientDetails = new BaseClientDetails(
                        client.getClientId(),
                        client.getResourceIds(),
                        client.getScope(),
                        client.getAuthorizedGrantTypes(),
                        client.getAuthorities(),
                        client.getWebServerRedirectUri());
                clientDetails.setClientSecret(PasswordEncoderTypeEnum.NOOP.getPrefix() + client.getClientSecret());
                //设置accessToken和refreshToken过期时间
                clientDetails.setAccessTokenValiditySeconds(client.getAccessTokenValidity());
                clientDetails.setRefreshTokenValiditySeconds(client.getRefreshTokenValidity());
                return clientDetails;
            } else {
                throw new NoSuchClientException("No client with requested id: " + clientId);
            }
        } catch (Exception e) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
    }
}
