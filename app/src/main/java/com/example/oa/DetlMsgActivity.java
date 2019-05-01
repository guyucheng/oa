package com.example.oa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View.OnClickListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetlMsgActivity extends AppCompatActivity implements OnClickListener {

    private String from_user, date, time, title, content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        from_user = intent.getStringExtra("from_user");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        date = intent.getStringExtra("date");
        time = intent.getStringExtra("time");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detl_msg);

        findViewById(R.id.iv_detl_go_back).setOnClickListener(this);
        TextView tv_detl_from_name = findViewById(R.id.tv_detl_from_name);
        TextView tv_detl_title = findViewById(R.id.tv_detl_title);
        TextView tv_detl_datetime = findViewById(R.id.tv_detl_datetime);
        TextView tv_detl_content = findViewById(R.id.tv_detl_content);


        tv_detl_from_name.setText(from_user);
        tv_detl_title.setText(title);
        tv_detl_content.setText(content);
        tv_detl_datetime.setText(date+"   "+time);

//        Toast.makeText(this, "" + from_user + title + content, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_detl_go_back) {
            // 返回上一级
            finish();
        }

    }


}

