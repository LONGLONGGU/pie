package com.framework.pie.admin.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.framework.pie.admin.constant.SysConstants;
import com.framework.pie.admin.util.encry.*;
import org.apache.commons.collections.map.HashedMap;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author ：longlong
 * @date ：Created in 2021/8/5 11:09
 * @modified By：
 * @version: ：V1.0
 */
public class TestRASAES  {
    public static void main(String[] args) throws Exception {
        TreeMap<String, Object> params = new TreeMap<>();
        params.put("info","测试");
        Map<String,String> map =  client(params);  //客户端发起请求
        String info = server(map);//服务端解密
        System.out.println(info);


    }

    public static Map<String,String> client(TreeMap<String, Object> params) throws Exception{
//        // 生成RSA签名
//        String sign = EncryUtil.handleRSA(params, SysConstants.clientPrivateKey);
//        params.put("sign", sign);
        String info = JSON.toJSONString(params);
        //随机生成AES密钥
        String aesKey = SecureRandomUtil.getRandom(16);
        //AES加密数据
        String data = AES.encryptToBase64(ConvertUtils.stringToHexString(info), aesKey);
        // 使用RSA算法将商户自己随机生成的AESkey加密
        String encryptkey = RSA.encrypt(aesKey, SysConstants.serverPublicKey);

        Map<String,String> map = new HashedMap();
        map.put("encryptkey",encryptkey);
        map.put("data",data);
        return map;
    }

    public static String server(Map<String,String> map) throws Exception{
        String data = map.get("data");
        String encryptkey = map.get("encryptkey");
        String  info = "";
        // 验签
//        boolean passSign = EncryUtil.checkDecryptAndSign(data,
//                encryptkey, SysConstants.clientPublicKey, SysConstants.serverPrivateKey);
//        if (passSign){
            String aeskey = RSA.decrypt(encryptkey, SysConstants.serverPrivateKey);
            data = ConvertUtils.hexStringToString(AES.decryptFromBase64(map.get("data"),
                    aeskey));
            JSONObject jsonObj = JSONObject.parseObject(data);
              info = jsonObj.getString("info");
//        }else {
//            info ="验签失败";
//        }
        return info;
    }
}
