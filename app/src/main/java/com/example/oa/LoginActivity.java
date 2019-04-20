package com.example.oa;


import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences mShared; // 声明一个共享参数对象
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
        mShared = getSharedPreferences("LoginData",MODE_PRIVATE);

        // 读取上次的记住密码状态
        bRemember = mShared.getBoolean("bRemember",false);

        // 设置记住密码CheckBox的状态
        cbx_remember.setChecked(bRemember);

        // 填写用户名
        if(bRemember == true){
            et_user_name.setText(mShared.getString("name",""));
        }else {
            SharedPreferences.Editor editor = mShared.edit();
            editor.putString("name","");
            editor.commit();
        }



    }

    public void onClick(View v){
        // 登录
        if(v.getId()==R.id.tv_login){
            String name = et_user_name.getText().toString();
            String password = et_password.getText().toString();
        // 验证用户名密码是否为空
            if(TextUtils.isEmpty(name)){
                Toast.makeText(this, "请填写用户名", Toast.LENGTH_SHORT).show();
                return;
            }else if(TextUtils.isEmpty(password)){
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }

            /**
             * 保存用户名
             */
            SharedPreferences.Editor editor = mShared.edit();
            if(bRemember == true){
                editor.putString("name",name);
                Toast.makeText(this, "isChecked", Toast.LENGTH_SHORT).show();
            }else{
                editor.putString("name","");
                Toast.makeText(this, "unChecked", Toast.LENGTH_SHORT).show();
            }
            editor.commit();

        }

    }


    // 定义是否记住密码的勾选监听器
    private class CheckListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.cbx_remember) {
                bRemember = isChecked;
                SharedPreferences.Editor editor = mShared.edit();
                editor.putBoolean("bRemember",bRemember);
                editor.commit();    // 提交修改
            }
        }
    }

}
