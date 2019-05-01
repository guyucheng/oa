package com.example.oa;


import android.accounts.Account;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oa.http.HttpUrls;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * A simple {@link Fragment} subclass.
 */
public class MassageFragment extends Fragment {

    Toolbar toolbar;
    protected View mView;   //申明一个视图对象,用来设置fragment的layout
    protected Context mContext;

    public MassageFragment() {
        // Required empty public constructor
    }


    private RecyclerView msgRecycleView;
    private RecyclerView.LayoutManager mLayoutManager;

    private LinkedList<HashMap<String, String>> data;  // 申明测试数据

    private MyAdapter myAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_massage, container, false);
        toolbar = mView.findViewById(R.id.toolbar);
        toolbar.setTitle("消息");

        msgRecycleView = (RecyclerView) mView.findViewById(R.id.rcc_message_list);
        msgRecycleView.setHasFixedSize(true);
        msgRecycleView.setLayoutManager(new LinearLayoutManager(this.mView.getContext()));     //线性布局

        // 生成消息数据
        doData();
        // 加载数据到消息中
        myAdapter = new MyAdapter();
        msgRecycleView.setAdapter(myAdapter);
        // Inflate the layout for this fragment
        return mView;

    }


    // 生成消息数据
    private void doData() {
        data = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            HashMap<String, String> row = new HashMap<>();
            int random = (int) (Math.random() * 100);
            row.put("name", "name" + random);
            row.put("title", "title" + random);
            row.put("date", "date" + random);
            data.add(row);
        }



    }

//    // 从网络上拉取消息
//    private void FetchMsg(){
//        // 拿到OkHttpClient对象
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .get()
//                .url(HttpUrls.MESSAGE)
//                .addHeader("x-access-token",
//                .build();
//
//    }


    // 加载消息列表
    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        class MyViewHolder extends RecyclerView.ViewHolder {
            public View itemView;
            public TextView name, title, date;

            public MyViewHolder(View v) {
                super(v);
                itemView = v;


                name = itemView.findViewById(R.id.tv_msg_item_name);
                title = itemView.findViewById(R.id.tv_msg_item_title);
                date = itemView.findViewById(R.id.tv_msg_item_date);

            }
        }


        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // 从文件中找到消息显示的那个模板
            View msgView = LayoutInflater.from(mView.getContext())
                    .inflate(R.layout.message_single_layout, parent, false);
            MyViewHolder vh = new MyViewHolder(msgView);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, final int position) {
            // 变更项目中的内容
            holder.name.setText(data.get(position).get("name"));
            holder.title.setText(data.get(position).get("title"));
            holder.date.setText(data.get(position).get("date"));
            // 该方法也可以对 name title data进行点击事件处理
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
//                }
//            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("MyAdapter TAG", "position:" + position);
                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
//                    showPopMenu(v,position);
                    return false;
                }
            });


        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public void removeItem(int pos) {
            data.remove(pos);
            notifyItemRemoved(pos);
        }


    }

    public void showPopMenu(View view, final int pos) {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                myAdapter.removeItem(pos);
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
//                Toast.makeText(getApplicationContext(), "关闭PopupMenu", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }


}
