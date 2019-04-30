package com.example.oa;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MassageFragment extends Fragment {

    Toolbar toolbar;
    private RecyclerView messageList;
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

        messageList = (RecyclerView) mView.findViewById(R.id.rcc_message_list);
        messageList.setHasFixedSize(true);
        messageList.setLayoutManager(new LinearLayoutManager(this.mView.getContext()));

        // Inflate the layout for this fragment
        return mView;
    }

    @Override
    public void onStart(){
        super.onStart();

    }

    public static class MessagesViewHolder extends RecyclerView.ViewHolder{

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
