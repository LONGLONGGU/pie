package com.framework.pie.service;

/**
 * mysql命令备份恢复服务
 */
public interface MysqlBackupService {

    /**
     * 备份数据库
     * @param host host地址，可以是本机也可以是远程
     * @param userName 数据库的用户名
     * @param password 数据库的密码
     * @param backupFolderPath 备份的路径
     * @param fileName 备份的文件名
     * @param database 需要备份的数据库的名称
     * @return
     * @throws Exception
     */
    boolean backup(String host, String userName, String password, String backupFolderPath, String fileName, String database) throws Exception;

    /**
     * 恢复数据库
     * @param restoreFilePath 数据库备份的脚本路径
     * @param host IP地址
     * @param database 数据库名称
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    boolean restore(String restoreFilePath, String host, String userName, String password, String database) throws Exception;

}

