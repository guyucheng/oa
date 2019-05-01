package com.example.oa;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment implements View.OnClickListener {



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
        mView = inflater.inflate(R.layout.fragment_account,container,false);
        Button btn_sign_out = mView.findViewById(R.id.btn_sign_out);
        btn_sign_out.setOnClickListener(this);
        Button btn_setting = mView.findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(this);

        return mView;   //返回该碎片对象
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.btn_sign_out){
            Logout();
        }else if(v.getId()==R.id.btn_setting){
            Toast.makeText(mContext, "您点击了设置！", Toast.LENGTH_SHORT).show();
        }
    }

    // 退出登录功能
    public void Logout(){
        // 清空 Token
        userMsg = this.getActivity().getSharedPreferences("LoginData",Context.MODE_PRIVATE);
//        this.getActivity().getSharedPreferences("config", Context.MODE_PRIVATE).getString("LoginData", "");
        SharedPreferences.Editor editor = userMsg.edit();
        editor.putString("token", "");
        editor.commit();    // 提交修改
        Intent intent = new Intent(mContext,LoginActivity.class);
        // 关闭之前的Activity
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }

}
