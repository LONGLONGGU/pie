package com.framework.pie.mybatis.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/**
 * @Author: guanqiangzhang
 * @Date:9:15 2021/9/26
 *  创建文件
 *
 */
public class creatMysqlScritp {
    /**
     * @Author: guanqiangzhang
     * @Date:9:16 2021/9/26
     *  创建文件
     * 参数  文件路径
     */
    public static boolean cerateFile(String filePath){
        boolean flag=false;
        File file =new File(filePath);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            flag=true;
        }
        return flag;
    }
    /**
     * @Author: guanqiangzhang
     * @Date:9:16 2021/9/26
     *  写入数据
     *  参数 内容，文件路径，是否可以追加
     *
     */
    public static boolean writeTxtFile(String content,String filePath,boolean append){
        boolean flag=false;
        if(content.equals("")){
            return flag;
        }
        File file=new File(filePath);
        try {
            creatMysqlScritp.cerateFile(filePath);
            //1
            FileWriter fw=new FileWriter(filePath,append);
           // fw.write(content);
            //2
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write(content);
            bw.close();
            fw.close();
            flag=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
