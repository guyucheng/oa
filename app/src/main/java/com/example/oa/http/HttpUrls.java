package com.example.oa.http;

/**
 * Created by ${user} on 19-4-30
 */
public class HttpUrls {

    // 服务器地址
    public static final String HTTPHOST = "http://192.168.1.2:8080";


    //登录地址
    public static final String LOGIN = HTTPHOST + "/api/login";


    // 消息地址
    public static final String MESSAGE = HTTPHOST + "/api/message";


    // 获取联系人地址
    public static final String GET_ALL_USERS = HTTPHOST + "/api/get_all_users";


    // 获取个人信息
    public static final String GET_PERSONAL_INFO = HTTPHOST + "/api/user";
}
