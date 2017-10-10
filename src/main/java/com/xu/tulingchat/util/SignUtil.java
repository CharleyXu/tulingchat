package com.xu.tulingchat.util;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 请求校验工具类
 * 作用:接入微信公众平台开发
 */
public class SignUtil {
    //TOKEN     my name 的 md5
    private static final String TOKEN = "106296486d2d6bf0a1fda9e5a292c6cc";
    //验证签名
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{TOKEN, timestamp, nonce};
        Arrays.sort(arr);//字典排序
        StringBuilder sb = new StringBuilder();
        for (String element : arr
                ) {
            sb.append(element);
        }
        final String tmpStr = getSha1(sb.toString());
        // 将 sha1 加密后的字符串可与 signature 对比，标识该请求来源于微信
        return tmpStr != null ? tmpStr.equals(signature.toLowerCase()) : false;
    }
    //sha1加密
    public static String getSha1(String str) {
        if (null == str || 0 == str.length()) {
            return null;
        }
        char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

}
