package com.example.oa.task;

import android.os.AsyncTask;

import com.example.oa.http.tool.HttpReqData;
import com.example.oa.http.tool.HttpRespData;

/**
 * Created by ${user} on 19-4-25
 */
public class LoginTask extends AsyncTask<String,Void,String>{
    // 请求数据
    private HttpReqData reqData = new HttpReqData();
    // 返回数据
    private HttpRespData respData;

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }


}
