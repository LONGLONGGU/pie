package com.framework.pie.auth.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.framework.pie.auth.common.enums.OAuthClientEnum;
import com.framework.pie.auth.feign.SysOauthClientFeignClient;
import com.framework.pie.auth.feign.SysUserFeignClient;
import com.framework.pie.auth.model.OAuthSysUser;
import com.framework.pie.constant.AuthConstants;
import com.framework.pie.constant.RedisConstants;
import com.framework.pie.http.HttpResult;
import com.framework.pie.redis.client.RedisClient;
import com.framework.pie.web.utils.JwtUtils;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import sun.security.acl.PrincipalImpl;

import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Api(tags = "认证中心")
@RestController
@RequestMapping("/oauth")
@AllArgsConstructor
@Slf4j
public class OAuthController{
    @Autowired
    private TokenEndpoint tokenEndpoint;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private KeyPair keyPair;
    @Autowired
    private SysUserFeignClient sysUserFeignClient;
    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private SysOauthClientFeignClient sysOauthClientFeignClient;
    @Autowired
    private UserDetailsService userDetailsService;



    @ApiOperation(value = "OAuth2认证", notes = "login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", value = "Oauth2客户端ID（新版本需放置请求头）", required = true),
            @ApiImplicitParam(name = "client_secret", value = "Oauth2客户端秘钥（新版本需放置请求头）", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", defaultValue = "SuperAdmin", value = "登录用户名"),
            @ApiImplicitParam(name = "password", defaultValue = "123456", value = "登录密码"),
            @ApiImplicitParam(name = "captToken", value = "验证码Token信息"),
            @ApiImplicitParam(name = "captcha", value = "验证码信息")
    })
    @PostMapping("/token")
    public Object postAccessToken(
            @ApiIgnore Principal principal,
            @ApiIgnore @RequestBody Map<String, String> parameters
    ) throws Exception {
        if (parameters == null || parameters.size() == 0) {
            return HttpResult.error("参数信息不能为空!");
        }
        if (StrUtil.isBlankIfStr(parameters.get("grant_type"))) {
            return HttpResult.error("必填参数[grant_type]授权模式不能为空!");
        }
        String grantType = parameters.get("grant_type");
        String logInType = parameters.get("logInType");
        String concatName = parameters.get("username")+"&"+logInType;
        parameters.put("username",concatName);
        //如果是系统登录，需要验证验证码信息
        if ("password".equals(grantType) && ("pc").equals(logInType)) {
                if (StrUtil.isBlankIfStr(parameters.get("captToken"))) {
                    return HttpResult.error("登录参数[captToken]不能为空!");
                }
                if (StrUtil.isBlankIfStr(parameters.get("captcha"))) {
                    return HttpResult.error("登录参数[captcha]不能为空!");
                }
                String captToken = parameters.get("captToken");
                String captcha = parameters.get("captcha");
                String redisCaptToken = redisClient.get(RedisConstants.CAP_CODE_PREFIX + captToken);
                if (StrUtil.isBlankIfStr(redisCaptToken)) {
                    return HttpResult.error("验证码已过期，请刷新页面后重试!");
                }
                if (!redisCaptToken.equalsIgnoreCase(captcha)) {
                    return HttpResult.error("登录验证码不正确，请重试!");
                }
                //都未返回清除redis验证码信息
                redisClient.del(RedisConstants.CAP_CODE_PREFIX + captToken);
        }
        if(("decision").equals(logInType)){
           HttpResult httpResult  = sysUserFeignClient.getSysUserByName(parameters.get("username"));
           if (httpResult.getCode() == 200){
               OAuthSysUser oAuthSysUser = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(httpResult.getData()),OAuthSysUser.class);
               Set<String> roleIds = oAuthSysUser.getRoleIds();
               if (roleIds.stream().noneMatch(("fa0d2c7d3affa7d6b161fa7e51da466f")::equals)){
                   return HttpResult.error("您暂无登录权限，请联系管理员!");
               }

           }

        }
        /**
         * 获取登录认证的客户端ID
         *
         * 兼容两种方式获取Oauth2客户端信息（client_id、client_secret）
         * 方式一：client_id、client_secret放在请求路径中(注：当前版本已废弃)
         * 方式二：放在请求头（Request Headers）中的Authorization字段，且经过加密，例如 Basic Y2xpZW50OnNlY3JldA== 明文等于 client:secret
         */
        String clientId = JwtUtils.getOAuthClientId();
        OAuthClientEnum client = OAuthClientEnum.getByClientId(clientId);
        switch (client) {
            case TEST: // knife4j接口测试文档使用 client_id/client_secret : client/123456
                return tokenEndpoint.postAccessToken(principal, parameters).getBody();
            default:
                return HttpResult.ok(tokenEndpoint.postAccessToken(principal, parameters).getBody());
        }
    }


    /**
     * 注销登录时，缓存JWT至Redis，且缓存有效时间设置为JWT的有效期，请求资源时判断是否存在缓存的黑名单中，存在就直接拒绝访问
     * 之所以这么做是因为注销的时候JWT已经生成了，我们无法让用户已经获取到的JWT直接过期，所以添加到黑名单中做判断
     *
     * @return
     */
    @ApiOperation(value = "注销", notes = "logout")
    @PostMapping("/logout")
    public HttpResult logout() {
        JSONObject payload = JwtUtils.getJwtPayload();
        String jti = payload.getStr(AuthConstants.JWT_JTI); // JWT唯一标识
        Long expireTime = payload.getLong(AuthConstants.JWT_EXP); // JWT过期时间戳(单位：秒)
        if (expireTime != null) {
            long currentTime = System.currentTimeMillis() / 1000;// 当前时间（单位：秒）
            if (expireTime > currentTime) { // token未过期，添加至缓存作为黑名单限制访问，缓存时间为token过期剩余时间
                redisClient.set(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti, "expireToken", Integer.parseInt(String.valueOf((expireTime - currentTime))));
            }
        } else { // token 永不过期则永久加入黑名单
            redisClient.set(AuthConstants.TOKEN_BLACKLIST_PREFIX + jti, "expireToken");
        }

        // 记录退出登录日志
        sysUserFeignClient.loginOut(JwtUtils.getUsername()+"["+JwtUtils.getNickName()+"]",JwtUtils.getLoginType());

        return HttpResult.ok("注销成功");
    }


    @ApiOperation(value = "获取公钥", notes = "login")
    @GetMapping("/public-key")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

    @PostMapping("/apply")
    public HttpResult apply(@RequestBody Map<String, String> parms){

        String id = parms.get("id");
        HttpResult httpResult = sysUserFeignClient.getTokenApple(id);
        Map<String, String> parameters = new HashMap<>();
        Map<String, String> data = new HashMap<>();
        if (httpResult.getCode() == 200){
            data = (Map<String, String>) httpResult.getData();
        }else {
            return HttpResult.error("申请失败！");
        }
        parameters.put("username",data.get("userName")+"&pc");
        parameters.put("password",data.get("password"));
        parameters.put("grant_type","password");
        Object obj = null;
        Principal principal = new PrincipalImpl("client2");
//
        try {
            Authentication authentication = new Authentication() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return null;
                }
                @Override
                public Object getCredentials() {
                    return null;
                }
                @Override
                public Object getDetails() {
                    return null;
                }
                @Override
                public Object getPrincipal() {
                    return principal;
                }
                @Override
                public boolean isAuthenticated() {
                    return true;
                }
                @Override
                public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
                }
                @Override
                public String getName() {
                    return "client2";
                }
            };
            ResponseEntity<OAuth2AccessToken> result = tokenEndpoint.postAccessToken(authentication, parameters);
            OAuth2AccessToken oAuth2AccessToken = result.getBody();
            String accessToken = oAuth2AccessToken.getTokenType() +" "+ oAuth2AccessToken.getValue();
            Map<String,String> tokenAppleMap = new HashMap<>();
            tokenAppleMap.put("id",data.get("id"));
            tokenAppleMap.put("accessToken",accessToken);
            return  sysUserFeignClient.tokenApply(tokenAppleMap);
        }catch (Exception e){
            e.printStackTrace();
            return HttpResult.error("申请失败！");
        }
    }
}

