package com.xu.tulingchat.util;

import java.security.MessageDigest;

/**
 * Md5加密
 */
public class Md5Util {

  public static String encrypt(String plainText) throws Exception {
    StringBuffer buf = new StringBuffer("");
    MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(plainText.getBytes("utf-8"));
		byte b[] = md.digest();
    int i;
    for (int offset = 0; offset < b.length; offset++) {
      i = b[offset];
      if (i < 0) {
        i += 256;
      }
      if (i < 16) {
        buf.append("0");
      }
      buf.append(Integer.toHexString(i));
    }
    return buf.toString().toLowerCase();
  }
}
