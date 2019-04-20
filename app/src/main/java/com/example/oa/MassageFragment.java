package com.example.oa;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MassageFragment extends Fragment {

    Toolbar toolbar;
    protected View mView;   //申明一个视图对象
    public MassageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_massage, container, false);
        toolbar = mView.findViewById(R.id.toolbar);
        toolbar.setTitle("消息");
        // Inflate the layout for this fragment
        return mView;
    }



}
