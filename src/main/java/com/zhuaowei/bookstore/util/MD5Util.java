package com.zhuaowei.bookstore.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * @ClassName: MD5Util
 * @Description: TODO: add description
 * @Author: zhuaowei
 * @Date: 2021/8/22 9:42
 * @Version: 1.0
 **/
public class MD5Util {
    public static String Md5Digest(String source, int salt) {
        char[] chars = source.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char)(chars[i] + salt);
        }
        String result = new String(chars);
        String md5Hex = DigestUtils.md5Hex(result);
        return md5Hex;
    }
}
