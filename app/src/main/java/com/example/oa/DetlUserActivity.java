package com.example.oa;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetlUserActivity extends AppCompatActivity implements View.OnClickListener {
    private String name, address, email, gender, phone, username, department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        username = intent.getStringExtra("username");
        department = intent.getStringExtra("department");
        gender = intent.getStringExtra("gender");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detl_user);

        TextView tv_detl_user_name = findViewById(R.id.tv_detl_user_name);
        TextView tv_detl_user_id = findViewById(R.id.tv_detl_user_id);
        TextView tv_detl_user_department = findViewById(R.id.tv_detl_user_department);
        TextView tv_detl_user_email = findViewById(R.id.tv_detl_user_email);
        TextView tv_detl_user_phone = findViewById(R.id.tv_detl_user_phone);
        TextView tv_detl_user_address = findViewById(R.id.tv_detl_user_address);
        TextView tv_detl_user_note = findViewById(R.id.tv_detl_user_note);
        TextView tv_detl_user_gender = findViewById(R.id.tv_detl_user_gender);

        tv_detl_user_name.setText(name);
        tv_detl_user_id.setText(username);
        tv_detl_user_department.setText(department);
        tv_detl_user_email.setText(email);
        tv_detl_user_phone.setText(phone);
        tv_detl_user_address.setText(address);
        tv_detl_user_gender.setText(gender);

        findViewById(R.id.btn_send_msg).setOnClickListener(this);
        findViewById(R.id.btn_call).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send_msg) {
            Intent intent = new Intent(DetlUserActivity.this, SendMsgActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("username", username);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_call) {
            if (phone.equals("null")) {
                Toast.makeText(this, "电话号码是空的", Toast.LENGTH_SHORT).show();
            } else {
                callPhone(phone);
            }

        }
    }


    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

}

