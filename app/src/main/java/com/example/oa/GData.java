package com.example.oa;

/**
 * Created by guyucheng on 19-5-1
 * 保存全局变量
 */
public class GData {
    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        GData.token = token;
    }
}
