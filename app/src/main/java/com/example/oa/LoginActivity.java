package com.example.oa;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import com.example.oa.http.HttpUrls;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.example.oa.MainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    public SharedPreferences userMsg;

    private EditText et_user_name;
    private EditText et_password;
    private boolean bRemember = false; // 是否记住密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.tv_login).setOnClickListener(this);
        et_user_name = findViewById(R.id.et_user_name);
        et_password = findViewById(R.id.et_password);
        // 从布局文件中获取叫cbx_remember的复选框
        CheckBox cbx_remember = findViewById(R.id.cbx_remember);
        // 设置勾选监听器
        cbx_remember.setOnCheckedChangeListener(new CheckListener());
        userMsg = getSharedPreferences("LoginData", MODE_PRIVATE);

        // 读取上次的记住密码状态
        bRemember = userMsg.getBoolean("bRemember", false);

        // 设置记住密码CheckBox的状态
        cbx_remember.setChecked(bRemember);

        // 填写用户名
        if (bRemember == true) {
            et_user_name.setText(userMsg.getString("name", ""));
        } else {
            SharedPreferences.Editor editor = userMsg.edit();
            editor.putString("name", "");
            editor.commit();
        }


    }

    public void onClick(View v) {
        // 登录
        String username = et_user_name.getText().toString();
        String password = et_password.getText().toString();
        Log.d("TAG\t" + "name", username);
        Log.d("TAG\t" + "password", password);

        if (v.getId() == R.id.tv_login) {
            // 验证用户名密码是否为空
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(this, "请填写用户名", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }

            /**
             * 保存用户名
             */
            SharedPreferences.Editor editor = userMsg.edit();
            if (bRemember == true) {
                editor.putString("name", username);
//                Toast.makeText(this, "isChecked", Toast.LENGTH_SHORT).show();
            } else {
                editor.putString("name", "");
//                Toast.makeText(this, "unChecked", Toast.LENGTH_SHORT).show();
            }
            editor.commit();

        }

        // TODO 编写登录函数
        Login(username, password);


    }


    // 定义是否记住密码的勾选监听器
    private class CheckListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.cbx_remember) {
                bRemember = isChecked;
                SharedPreferences.Editor editor = userMsg.edit();
                editor.putBoolean("bRemember", bRemember);
                editor.commit();    // 提交修改
            }
        }
    }


    // 登录

    private void Login(String username, String password) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        JSONObject obj = new JSONObject();
        String content = "";
        try {
            obj.put("username", username);
            obj.put("password", password);
            content = obj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("TAG\t" + "LoginActivity", content);
        RequestBody body = RequestBody.create(JSON, content);

        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpUrls.LOGIN)
                .header("Content-Type", "application/json")
                .post(body)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();

                try {
                    JSONObject respJson = new JSONObject(myResponse);
                    int status = respJson.getInt("status");

                    if (status == 0) {
                        // 用户名密码正确，登录正常
                        String token = respJson.getString("token");
                        // 存入Token
                        SharedPreferences.Editor editor = userMsg.edit();
                        editor.putString("token", token);
                        editor.commit();    // 提交修改
                        Log.d("TAG\t" + "token", token);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else if (status == 1) {
                        // 用户名不存在
                        Log.d("TAG\t" + "LoginActivity", "用户名不存在");

                    } else if (status == 2) {
                        // 密码不正确
                        Log.d("TAG\t" + "LoginActivity", "密码不正确");
                    }
                } catch (JSONException e) {
                    Log.d("TAG\t" + "LoginActivity", "检查网络");
                    e.printStackTrace();
                }
            }
        });
    }


    //unicode 转换 utf8
    public static String unicodeToString(String str) {

        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

        Matcher matcher = pattern.matcher(str);

        char ch;

        while (matcher.find()) {

            ch = (char) Integer.parseInt(matcher.group(2), 16);

            str = str.replace(matcher.group(1), ch + "");

        }

        return str;

    }


}
