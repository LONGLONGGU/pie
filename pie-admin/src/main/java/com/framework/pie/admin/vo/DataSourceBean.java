package com.framework.pie.admin.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 数据源
 * </p>
 *
 * @author longlong
 * @since 2021-08-31
 */
@Data
public class DataSourceBean {

    /**
     * 编号
     */
    private Long id;

    /**
     * 文件夹名称
     */
    private String folderName;

    /**
     * 数据源名称
     */
    private String sourceName;

    /**
     * 数据库名称
     */
    private String databaseName;

    /**
     * 0 文件夹 1 数据源
     */
    private Long type;

    /**
     * 上级节点ID，一级节点为0
     */
    private Long parentId;

    /**
     * ip
     */
    private String ip;

    /**
     * 端口
     */
    private String port;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String lastUpdateBy;

    /**
     * 更新时间
     */
    private Date lastUpdateTime;

    /**
     * 是否删除 -1：已删除 0：正常
     */
    private Integer delFlag;


    // 非数据库字段
    private List<DataSourceBean> children;
    //是否有子节点
    private boolean hasChildren = true;
    // 非数据库字段
    private String parentName;
    //非数据库字段
    private String name;
}
