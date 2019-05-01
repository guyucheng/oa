package com.example.oa;

import java.util.Date;

/**
 * Created by guyucheng on 19-5-1
 * 保存全局变量
 */
public class GData {
    private static String token;
    private static long lastFreshMsg;

    public static long getLastFreshMsg() {
        return lastFreshMsg;
    }

    public static void setLastFreshMsg(long lastFreshMsg) {
        GData.lastFreshMsg = lastFreshMsg;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        GData.token = token;
    }
}
