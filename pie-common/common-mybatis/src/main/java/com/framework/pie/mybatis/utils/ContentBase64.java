package com.framework.pie.mybatis.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * @Author: guanqiangzhang
 * @Date:11:09 2021/9/26
 *
 *
 */
public class ContentBase64 {
    /**
     * BASE64解密
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     */
    public static String encryptionBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
}
