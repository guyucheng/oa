package com.example.oa;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 隐藏ActionBar
        getSupportActionBar().hide();
        findViewById(R.id.tv_login).setOnClickListener(this);
    }

    public void onClick(View v){
        if(v.getId()==R.id.tv_login){
            Toast.makeText(this, "login", Toast.LENGTH_SHORT).show();
        }

    }


}
