package com.example.oa;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by guyucheng on 19-5-1
 * 保存全局变量
 */
public class GData {
    private static String token;
    private static long lastFreshMsg;
    private static String id = "";
    private static String name = "";
    private static LinkedList<HashMap<String, String>> ContactData;


    public static LinkedList<HashMap<String, String>> getContactData() {
        return ContactData;
    }

    public static void setContactData(LinkedList<HashMap<String, String>> contactData) {
        GData.ContactData = contactData;
    }

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        GData.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        GData.name = name;
    }

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

    public static void clear() {
        token = "";
        lastFreshMsg = 0;
        id = "";
        name = "";

    }


}
