package com.example.oa.task;

import android.os.AsyncTask;

import com.example.oa.http.HttpRequestUtil;
import com.example.oa.http.tool.HttpReqData;
import com.example.oa.http.tool.HttpRespData;

/**
 * Created by gu on 19-4-21
 */
public class GetWebTask extends AsyncTask<String, Void, String> {

    public GetWebTask(String url) {
        super();
    }

    // 线程处理后台任务
    protected String doInBackground(String... params) {
        String url = params[0];
        HttpReqData mReqData = new HttpReqData(url);
        HttpRespData reqData = HttpRequestUtil.getData(mReqData);
        String response = reqData.content;
        return response;
    }

    // 线程已经完成处理
    protected void onPostExecute(String address) {
        // HTTP调用完毕，触发监听器的找到地址事件
        mListener.onFindWeb(address);
    }

    private OnWebListener mListener; // 声明一个查询详细地址的监听器对象

    // 设置查询详细地址的监听器
    public void setOnWebListener(OnWebListener listener) {
        mListener = listener;
    }

    // 定义一个查询详细地址的监听器接口
    public interface OnWebListener {
        void onFindWeb(String address);
    }


}
