package com.framework.pie.auth.security.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.framework.pie.auth.model.OAuthUserDetails;
import com.framework.pie.auth.security.service.ClientDetailsServiceImpl;
import com.framework.pie.auth.security.service.UserDetailsServiceImpl;
import com.framework.pie.auth.security.util.SelfPasswordEncoder;
import com.framework.pie.constant.AuthConstants;
import com.framework.pie.http.HttpResult;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 认证授权配置，这个配置类是整个认证服务实现的核心，
 * 总结下来主要就是配置客户端信息从数据库加载出来，完成access_token的生成配置
 */
@Configuration
@EnableAuthorizationServer
@AllArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private ClientDetailsServiceImpl clientDetailsService;

    /**
     * OAuth2客户端【数据库加载】
     */
    @Override
    @SneakyThrows
    public void configure(ClientDetailsServiceConfigurer clients) {
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> tokenEnhancers = new ArrayList<>();
        tokenEnhancers.add(tokenEnhancer());
        tokenEnhancers.add(jwtAccessTokenConverter());
        tokenEnhancerChain.setTokenEnhancers(tokenEnhancers);
        endpoints
                .authenticationManager(authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(tokenEnhancerChain)
                .userDetailsService(userDetailsService)
                // refresh token有两种使用方式：重复使用(true)、非重复使用(false)，默认为true
                //      1 重复使用：access token过期刷新时， refresh token过期时间未改变，仍以初次生成的时间为准
                //      2 非重复使用：access token过期刷新时， refresh token过期时间延续，在refresh token有效期内刷新便永不失效达到无需再次登录的目的
                .reuseRefreshTokens(true);
    }




    /**
     * JwtAccessTokenConverter 生成token的转换器，可以实现指定token的生成方式(JWT)和对JWT进行签名
     * 此处我们自定义了KeyPair,使用指定的私钥进行token生成和转换
     * 使用非对称加密算法对token签名
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 从classpath下的密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("pie.jks"), "pie123456".toCharArray());
        KeyPair keyPair = factory.getKeyPair("pie", "pie123456".toCharArray());
        return keyPair;
    }

    /**
     * JWT内容增强
     * 此处实现了TokenEnhancer接口， 实现接口中的enhance方法，JWT负载信息默认是固定的，如果想要自定义添加一些额外信息
     * 需要实现TokenEnhancer的enhance方法将附加信息添加到access_token中，如下方式进行添加
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = CollectionUtil.newHashMap();
            OAuthUserDetails oAuthUserDetails = (OAuthUserDetails) authentication.getUserAuthentication().getPrincipal();
            additionalInfo.put(AuthConstants.USER_ID_KEY, oAuthUserDetails.getId());
            additionalInfo.put(AuthConstants.USER_NAME_KEY, oAuthUserDetails.getUsername());
            additionalInfo.put(AuthConstants.NICK_NAME_KEY, oAuthUserDetails.getNickName());
            additionalInfo.put(AuthConstants.DEPT_ID_KEY,oAuthUserDetails.getDeptId());
            additionalInfo.put(AuthConstants.ORG_ID_KEY,oAuthUserDetails.getOrgId());
            additionalInfo.put(AuthConstants.ORG_DISTRICT_ID,oAuthUserDetails.getOrgDistrictId());
            additionalInfo.put(AuthConstants.SUPER_ADMIN_KEY,oAuthUserDetails.isSuperAdminFlag());
            additionalInfo.put(AuthConstants.LOGIN_TYPE,oAuthUserDetails.getLoginType());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }

    /**
     * 主要用于解析特定 Authentication
     * 项目中我们重写了 userDetails和密码验证管理
     * @return
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false); // 用户不存在异常抛出
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * 密码编码器
     *
     * 委托方式，根据密码的前缀选择对应的encoder，例如：{bcypt}前缀->标识BCYPT算法加密；{noop}->标识不使用任何加密即明文的方式
     * 密码判读 DaoAuthenticationProvider#additionalAuthenticationChecks
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        //如果是普通的加密，直接使用Security的PasswordEncoderFactories中的createDelegatingPasswordEncoder方法即可
        //使用工厂原生的方式，在查询用户出来的时候，需要让Security知道是使用哪一种方式进行加密处理的，在对应的密码前加{bcypt}、
        // {md5}或{noop}等进行区分，{noop}表示没有加密，直接明文验证
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //此处我们自定义的密码加密方式，定义返回自己的PasswordEncoder
        SelfPasswordEncoder selfPasswordEncoder = new SelfPasswordEncoder();
        return selfPasswordEncoder;
    }

    /**
     * 自定义认证异常响应数据
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, e) -> {
            response.setStatus(HttpStatus.HTTP_OK);
            response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            HttpResult result = HttpResult.error(403,"oAuth认证异常");
            response.getWriter().print(JSONUtil.toJsonStr(result));
            response.getWriter().flush();
        };
    }
}
