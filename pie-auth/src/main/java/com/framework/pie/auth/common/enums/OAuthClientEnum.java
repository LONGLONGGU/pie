package com.framework.pie.auth.common.enums;
import lombok.Getter;


/**
 * 客户端枚举类型
 */
public enum OAuthClientEnum {

    CLIENT_INFO("client", "客户端"),
    TOKEN_CLIENT_INFO("client2", "token申请客户端"),
    TEST("clientTest","测试客户端");

    @Getter
    private String clientId;

    @Getter
    private String  desc;

    OAuthClientEnum(String clientId,String desc){
        this.clientId=clientId;
        this.desc=desc;
    }

    public static OAuthClientEnum getByClientId(String clientId) {
        for (OAuthClientEnum client : OAuthClientEnum.values()) {
            if(client.getClientId().equals(clientId)){
                return client;
            }
        }
        return null;
    }

}
