package com.framework.pie.mybatis.utils;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;

/**
 * @Author: guanqiangzhang
 * @Date:14:16 2021/9/2
 *  sql执行工具类
 *
 */
@Data
@Component

public class RunSqlUtils {
    private String mysqlDriver = "com.mysql.cj.jdbc.Driver";
    private String Url = "jdbc:mysql://172.16.3.28:3306/zst-data-manager?useUnicode=true&zeroDateTimeBehavior=convertToNull&autoReconnect=true&characterEncoding=utf-8&serverTimezone=GMT%2B8";
    private String username = "root";
    private String password = "Pass_123";
    private Connection conn = null;
    private ResultSet rs = null;
    private Statement newSmt = null;
    private String databaseName;
    private PreparedStatement preparedStatement = null;

    public RunSqlUtils(String mysqlDriver, String Url, String username, String password){
            this.mysqlDriver=mysqlDriver;
            this.Url=Url;
            this.username=username;
            this.password=password;
    }
    public RunSqlUtils() {
    }

    /**
     * @Author: guanqiangzhang
     * @Date:14:15 2021/9/2
     *  测试连接是否通
     *
     */
    public boolean getConnection(){
        boolean flag=false;
        try {
            Class.forName(mysqlDriver);
            conn = DriverManager.getConnection(Url, username,
                    password);
            if(conn!=null){
                flag=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       return flag;
    }
    /**
     * @Author: guanqiangzhang
     * @Date:14:19 2021/9/2
     *  关闭资源
     *
     */
    public void close() {
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
    /**
     * @Author: guanqiangzhang
     * @Date:16:26 2021/9/8
     *  返回结果集
     *
     */
    public List<Map<String,Object>> getObjectLists(String sql) {
       List<Map<String,Object>> list=new ArrayList<>();
        if (conn != null) {
            try {
                // 开始时间
                long stime = System.currentTimeMillis();
                preparedStatement = conn.prepareStatement(sql);
                newSmt =conn.createStatement(rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_UPDATABLE);
                rs=newSmt.executeQuery(sql);
                // 结束时间
                long etime = System.currentTimeMillis();
                Long second=(etime - stime);
                ResultSetMetaData rsmd = rs.getMetaData();
                int numberOfColumns = rsmd.getColumnCount();
                this.databaseName=rsmd.getCatalogName(1);
                this.setDatabaseName(this.databaseName);
                while(rs.next()) {
                    Map<String, Object> map = new LinkedHashMap<>();
                    for (int i = 1; i <= numberOfColumns; i++) {
                        String columnLabel = rsmd.getColumnLabel(i);
                        map.put(rsmd.getColumnLabel(i),rs.getString(rsmd.getColumnLabel(i)));
                    }
                    list.add(map);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                Map<String,Object> map=new HashMap<>();
                map.put("error",e.getMessage());
                list.add(map);
                close();
            }
        }
        return list;
    }
    /**
     * @Author: guanqiangzhang
     * @Date:16:07 2021/9/2
     *  执行修改或创建操作
     *
     */
    public List<Map<String,Object>> getUpdateObjectList(String sql) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String,Object>> list=new ArrayList<>();
        if (conn != null) {
            try {
                newSmt = conn.createStatement();
                // 开始时间
                long stime = System.currentTimeMillis();
               // Thread.sleep(1000);
                Integer result = newSmt.executeUpdate(sql);
                // 结束时间
                long etime = System.currentTimeMillis();
                if(result!=-1){
                    if(result==0){
                        map.put("success","创建成功！ 受影响的行: "+result+"行" );
                    }else{
                        map.put("success","执行成功！ 受影响的行"+result+"行" );
                    }
                    map.put("runTime",(etime - stime)+"毫秒");
                   // map.put("databasesName",)
                }else{
                    map.put("runTime",(etime - stime)+"毫秒");
                }
                list.add(map);
            } catch (SQLException e) {
                e.printStackTrace();
                map.put("error",e.getMessage());
                map.put("runTime",0+"毫秒");
                list.add(map);
            }
        }
        return list;
    }

    public List<Map<String,Object>> getData(String sql, JudgeSql<String> js){
        return js.getObjectList(sql);
    }
}
