package com.example.oa;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oa.http.HttpUrls;
import com.example.oa.tools.UTF8Cvtor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {

    private TextView tv_fr_acc_name, tv_fr_acc_id;

    public AccountFragment() {
        // Required empty public constructor
    }

    protected View mView;   //申明一个视图对象
    protected Context mContext;
    private SharedPreferences userMsg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();  // 获取活动页面上下文
        mView = inflater.inflate(R.layout.fragment_account, container, false);
        Button btn_sign_out = mView.findViewById(R.id.btn_sign_out);
        btn_sign_out.setOnClickListener(this);
        Button btn_setting = mView.findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(this);
        tv_fr_acc_name = mView.findViewById(R.id.tv_fr_acc_name);
        tv_fr_acc_id = mView.findViewById(R.id.tv_fr_acc_id);
        if (GData.getName().isEmpty()) {
            GetPersonInfo();
        } else {
            tv_fr_acc_name.setText(GData.getName());

        }
        tv_fr_acc_id.setText(GData.getId());
        return mView;   //返回该碎片对象
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_sign_out) {
            Logout();
        } else if (v.getId() == R.id.btn_setting) {
            Toast.makeText(mContext, "您点击了设置！", Toast.LENGTH_SHORT).show();
        }
    }

    // 退出登录功能
    public void Logout() {
        // 清空 Token
        userMsg = this.getActivity().getSharedPreferences("LoginData", Context.MODE_PRIVATE);
//        this.getActivity().getSharedPreferences("config", Context.MODE_PRIVATE).getString("LoginData", "");
        SharedPreferences.Editor editor = userMsg.edit();
        editor.putString("token", "");
        GData.clear();

        editor.commit();    // 提交修改
        Intent intent = new Intent(mContext, LoginActivity.class);
        // 关闭之前的Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void GetPersonInfo() {
        // 拿到OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpUrls.GET_PERSONAL_INFO)
                .header("x-access-token", GData.getToken())
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String myResponse = response.body().string();
                    // 将取回的结果转换成UTF8
                    String result = UTF8Cvtor.unicodeToString(myResponse);
                    try {
                        JSONObject obj = new JSONObject(result);

                        String name = obj.getString("name");
                        String id = obj.getString("username");
                        GData.setName(name);
                        GData.setId(id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // 用getActivity 替换 MessageFragment.this
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 数据下载完毕之后，将数据加载到界面上
                            tv_fr_acc_name.setText(GData.getName());
                            tv_fr_acc_id.setText(GData.getId());


                        }
                    });
                }
            }
        });

    }


}
