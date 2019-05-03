package com.example.oa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oa.http.HttpUrls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendMsgActivity extends AppCompatActivity implements View.OnClickListener {
    private String name, username, title, content;
    private  EditText et_msg_title, et_msg_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        username = intent.getStringExtra("username");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_msg);

        TextView tv_to_name = findViewById(R.id.tv_to_name);
        tv_to_name.setText(name);

        findViewById(R.id.btn_send_msg).setOnClickListener(this);

        et_msg_title = findViewById(R.id.et_msg_title);
        et_msg_content = findViewById(R.id.et_msg_content);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_msg) {
            title = et_msg_title.getText().toString();
            content = et_msg_content.getText().toString();
            sendMsg(title,content,username);
//            Toast.makeText(this, "send", Toast.LENGTH_SHORT).show();
        }

    }
    // 发送消息
    private void sendMsg(String title, String content, String username) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject obj = new JSONObject();
        String json_content = "";
        try {
            obj.put("title", title);
            obj.put("content", content);
            obj.put("to_user", username);
            json_content = obj.toString();
            Log.d("SendMsgActivity", json_content);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON, json_content);
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpUrls.MESSAGE)
                .header("x-access-token", GData.getToken())
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .post(body)
                .build();

        // 将Request封装为Call
        Call call = client.newCall(request);

        // 调用请求重写回调方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(SendMsgActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject respJson = new JSONObject(myResponse);
                            int status = respJson.getInt("status");
                            if (status == 1) {
                                Toast.makeText(SendMsgActivity.this, "发送成功！", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(SendMsgActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


            }
        });
    }
}
