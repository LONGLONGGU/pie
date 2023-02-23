package com.framework.pie.constant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum ServerTypeCode implements IServerTypeCode {

    ENTERPRISE_FILE("enterprise","企业文件"),
    PROJECT_FILE("project","项目文件"),
    PROJECT_ECHARTS_FILE("projectEcharts","项目报告文件"),
    ADMIN_FILE("admin","系统管理文件"),
    UPLOAD_FILE("upload","文件上传服务"),
    LAND_MARK_FILE("landMark","地标库"),
    APP_DOWNLOAD_FILE("appDownload","App版本控制管理文件"),
    ZST_SERVER("cloudDisk","招商通云盘服务"),
    ZST_NEWS("news","招商通新闻附件"),
    ZST_POLICY("policy","招商通政策附件"),
    ZST_FORUM("forum","招商通学习交流附件"),
    ZST_INDUSTRY_INFO("industryInfo","招商通产业资讯附件");


    private String type;

    private String name;

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    /**
     * 通过类型获取枚举值
     * @param type
     * @return
     */
    public static String getNameByType(String type) {
        ServerTypeCode[] enums = values();
        for (ServerTypeCode enumInfo : enums) {
            if (enumInfo.getType().equals(type)) {
                return enumInfo.getName();
            }
        }
        return null;
    }
}
