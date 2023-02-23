package com.framework.pie.utils;

import com.framework.pie.constant.GlobalConstants;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class MD5Utils {

    /**
     * 用户密码加密工具类
     * @param salt 加密盐字符串
     * @param pwd 密码明文字符串
     * @return
     */
    public static String encrypt(String salt,String pwd){
        String encryptStr = null;
        if(pwd != null && !"".equals(pwd)){
            try {
                //MD5实现类实例化
                byte[] retInfo = MessageDigest.getInstance("md5").digest((salt+pwd).getBytes("utf-8"));
                //将获取到byte数组转换成16进制的字符串
                String md5Code = new BigInteger(1, retInfo).toString(16);
                //获取到的字符串不足32位的用"0"进行补齐
                for(int i=0;i<GlobalConstants.PWD_LENGTH-md5Code.length();i++){
                    md5Code = "0" + md5Code;
                }
                encryptStr = md5Code;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return encryptStr;
    }

    /**
     * 验证密码信息是否匹配
     * @param rawPass 原始密码
     * @param encPass 加密密码信息
     * @return
     */
    public static boolean matches(String rawPass,String encPass){
        //获取前缀做为加密盐信息
        String salt = encPass.split("\\|")[0];
        //加密年盐与新的明文密码组合进行密码行到用户输入的加密后的密码
        String inputPwd = MD5Utils.encrypt(salt,rawPass);
        String newPwd = salt+GlobalConstants.PWD_SPLIT+inputPwd;
        return newPwd.equals(encPass);
    }

    /**
     * 获取加密盐信息
     * @return
     */
    public static String getSalt(){
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,20);
    }

    public static void main(String[] args) {
        String pwd = "123456";
        //String salt = getSalt();
        String salt = "90fb2441b845424db163";
        System.out.println(salt);
        String encrypt = encrypt(salt, pwd);
        System.out.println(encrypt);
        System.out.println(matches(pwd,salt+GlobalConstants.PWD_SPLIT+"7b8f88577240528e39426fe3892b387f"));

    }
}
