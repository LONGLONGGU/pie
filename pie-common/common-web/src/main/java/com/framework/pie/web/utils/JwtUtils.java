package com.framework.pie.web.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.framework.pie.constant.AuthConstants;
import com.framework.pie.utils.StringUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * JWT工具类
 * @author houjh
 */
@Slf4j
public class JwtUtils {

    @SneakyThrows
    public static JSONObject getJwtPayload() {
        String payload = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader(AuthConstants.JWT_PAYLOAD_KEY);
        JSONObject jsonObject = JSONUtil.parseObj(URLDecoder.decode(payload,"UTF-8"));
        return jsonObject;
    }

    /**
     * 解析JWT获取用户ID
     *
     * @return
     */
    public static String getUserId() {
        String id = getJwtPayload().getStr(AuthConstants.USER_ID_KEY);
        return id;
    }

    /**
     * 解析JWT获取获取用户名
     *
     * @return
     */
    public static String getUsername() {
        String username = "访客";
        try {
             username = getJwtPayload().getStr(AuthConstants.USER_NAME_KEY);
        }catch (Exception e){
            return username;
        }
        return username;
    }

    /**
     * 解析JWT获取获取用户昵称
     *
     * @return
     */
    public static String getNickName() {
        String nickName = "访客";
        try {
            nickName =  getJwtPayload().getStr(AuthConstants.NICK_NAME_KEY);
        }catch (Exception e){
            return nickName;
        }

        return nickName;
    }

    /**
     * 解析JWT获取获取用户部门ID信息
     *
     * @return
     */
    public static String getDeptId() {
        String deptId = getJwtPayload().getStr(AuthConstants.DEPT_ID_KEY);
        return deptId;
    }

    /**
     * 解析JWT获取获取用户机构ID信息
     *
     * @return
     */
    public static String getOrgId() {
        String orgId = getJwtPayload().getStr(AuthConstants.ORG_ID_KEY);
        return orgId;
    }

    /**
     * 解析JWT获取获取用户机构行政区划ID信息
     *
     * @return
     */
    public static String getOrgDistrictId() {
        String orgDistrictId = getJwtPayload().getStr(AuthConstants.ORG_DISTRICT_ID);
        return orgDistrictId;
    }

    /**
     * 解析JWT获取获取用户登录类型
     *
     * @return
     */
    public static String getLoginType() {
        String loginType = getJwtPayload().getStr(AuthConstants.LOGIN_TYPE);
        return loginType;
    }

    /**
     * 获取登录认证的客户端ID
     *
     * 兼容两种方式获取Oauth2客户端信息（client_id、client_secret）
     * 方式一：client_id、client_secret放在请求路径中
     * 方式二：放在请求头（Request Headers）中的Authorization字段，且经过加密，例如 Basic Y2xpZW50OnNlY3JldA== 明文等于 client:secret
     *
     * @return
     */
    @SneakyThrows
    public static String getOAuthClientId() {
        String clientId;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        // 从请求路径中获取
        Map<String, String[]> parmet = request.getParameterMap();
        System.out.println(parmet);
        clientId = request.getParameter(AuthConstants.CLIENT_ID_KEY);
        if (StrUtil.isNotBlank(clientId)) {
            return clientId;
        }

        // 从请求头获取
        String basic = request.getHeader(AuthConstants.AUTHORIZATION_KEY);
        if (StrUtil.isNotBlank(basic) && basic.startsWith(AuthConstants.BASIC_PREFIX)) {
            basic = basic.replace(AuthConstants.BASIC_PREFIX, Strings.EMPTY);
            String basicPlainText = new String(new BASE64Decoder().decodeBuffer(basic), "UTF-8");
            clientId = basicPlainText.split(":")[0]; //client:secret
        }
        if (StringUtils.isBlank(clientId)){
            basic = request.getHeader(AuthConstants.TOKEN_AUTHORIZATION_KEY);
            basic = basic.replace(AuthConstants.BASIC_PREFIX, Strings.EMPTY);
            String basicPlainText = new String(new BASE64Decoder().decodeBuffer(basic), "UTF-8");
            clientId = basicPlainText.split(":")[0]; //client:secret
        }
        return clientId;
    }

    /**
     * JWT获取用户角色列表
     *
     * @return 角色列表
     */
    public static List<String> getRoles() {
        List<String> roles = null;
        JSONObject payload = getJwtPayload();
        if (payload != null && payload.containsKey(AuthConstants.JWT_AUTHORITIES_KEY)) {
            roles = payload.get(AuthConstants.JWT_AUTHORITIES_KEY, List.class);
        }
        return roles;
    }

    /**
     * 判断当前登录用户是否是超级管理员
     * @return
     */
    public static boolean isSuperAdmin(){
        Boolean superAdmin = getJwtPayload().getBool(AuthConstants.SUPER_ADMIN_KEY);
        return superAdmin;
    }
}
