package com.lh.blog.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.HashMap;
import java.util.Map;

public class EncodeUtil {
    public static Map<String,Object> encode(String pass)
    {
        // 加密的公盐
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        // 加密次数，两次更安全
        int times = 2;
        // 加密算法
        String algorithmName = "md5";
        // 获取加密后的结果
        String encodedPassword = new SimpleHash(algorithmName,pass,salt,times).toString();
        Map<String,Object> map = new HashMap<>();
        map.put("salt",salt);
        map.put("pass",encodedPassword);
        return map;
    }

    public static String recode(String pass,String salt){
        int times = 2;
        String algorithmName = "md5";
        String encodedPassword = new SimpleHash(algorithmName,pass,salt,times).toString();
        return encodedPassword;
    }
}
