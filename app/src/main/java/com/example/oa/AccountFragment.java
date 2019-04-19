package com.example.oa;


import android.content.Context;
import android.content.Intent;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();  // 获取活动页面上下文
        mView = inflater.inflate(R.layout.fragment_account,container,false);
        Button btn_sign_in = mView.findViewById(R.id.btn_sign_in);
        btn_sign_in.setOnClickListener(this);

        return mView;   //返回该碎片对象
    }

    @Override
    public void onClick(View v){
        if(v.getId()==R.id.btn_sign_in){
            Intent intent = new Intent(mContext,LoginActivity.class);
            startActivity(intent);

        }
    }

}
