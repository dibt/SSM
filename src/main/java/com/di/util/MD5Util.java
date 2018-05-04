package com.di.util;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by bentengdi on 2017/10/14.
 */
public class MD5Util {

    public static String md5(String str) {
        return  DigestUtils.md5Hex(str);
    }
}
