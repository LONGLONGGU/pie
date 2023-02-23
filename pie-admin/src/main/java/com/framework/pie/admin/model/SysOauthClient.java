package com.framework.pie.admin.model;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
* 客户端信息表
* </p>
*
* @author houjh
* @since 2021-08-04
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_oauth_client")
@ApiModel(value = "SysOauthClient对象", description = "客户端信息表")
public class SysOauthClient extends Model<SysOauthClient> {
    private static final long serialVersionUID = 1L;

    //ID信息
    @ApiModelProperty(value = "ID信息")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    //客户端ID
    @ApiModelProperty(value = "客户端ID")
    @TableField("client_id")
    private String clientId;

    //资源id列表
    @ApiModelProperty(value = "资源id列表")
    @TableField("resource_ids")
    private String resourceIds;

    //客户端密钥
    @ApiModelProperty(value = "客户端密钥")
    @TableField("client_secret")
    private String clientSecret;

    //域
    @ApiModelProperty(value = "域")
    @TableField("scope")
    private String scope;

    //授权方式
    @ApiModelProperty(value = "授权方式")
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;

    //回调地址
    @ApiModelProperty(value = "回调地址")
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;

    //权限列表
    @ApiModelProperty(value = "权限列表")
    @TableField("authorities")
    private String authorities;

    //认证令牌时效
    @ApiModelProperty(value = "认证令牌时效")
    @TableField("access_token_validity")
    private Integer accessTokenValidity;

    //刷新令牌时效
    @ApiModelProperty(value = "刷新令牌时效")
    @TableField("refresh_token_validity")
    private Integer refreshTokenValidity;

    //扩展信息
    @ApiModelProperty(value = "扩展信息")
    @TableField("additional_information")
    private String additionalInformation;

    //是否自动放行
    @ApiModelProperty(value = "是否自动放行")
    @TableField("autoapprove")
    private String autoapprove;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}