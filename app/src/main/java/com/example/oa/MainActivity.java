package com.example.oa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_sign_in).setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.btn_sign_in){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
    }
}
