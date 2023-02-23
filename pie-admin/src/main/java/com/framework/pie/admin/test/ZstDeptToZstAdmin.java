package com.framework.pie.admin.test;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author ：longlong
 * @date ：Created in 2021/11/11 15:11
 * @modified By：
 * @version: ：V1.0
 */
public class ZstDeptToZstAdmin {
    public static void main(String[] args) {
        JdbcSourceUtils jdbcSourceUtils = new JdbcSourceUtils();
        jdbcSourceUtils.getConnection();
        try {
            List<Map<String, Object>> result = jdbcSourceUtils.findResult("select d.id as id,d.dept_name as name,d.parent_id as parent_id from sys_dept d  where d.deleted = 0",null);
            for (Map<String, Object> map : result) {
                System.out.println(map);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void conversion(){

    }
}
