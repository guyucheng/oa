package com.example.oa;


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
import com.example.oa.tools.UTF8Cvtor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MassageFragment extends Fragment implements View.OnClickListener {

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
        // 刷新按钮监听器
        mView.findViewById(R.id.iv_msg_fresh).setOnClickListener(this);

        // 获取消息


        FetchMsg();

            // Inflate the layout for this fragment
            return mView;

    }


    // 从网络上拉取消息
    public void FetchMsg() {
        // 拿到OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpUrls.MESSAGE)
                .header("x-access-token", GData.getToken())
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }


            // 得到消息列表之后的处理
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    final String myResponse = response.body().string();
                    // 将取回的结果转换成UTF8
                    String result = UTF8Cvtor.unicodeToString(myResponse);
                    try {
                        JSONObject obj = new JSONObject(result);
                        JSONArray messageList = obj.getJSONArray("messages");
                        // 取出每一个消息
                        data = new LinkedList<>();

                        for (int i = 0; i < messageList.length(); i++) {
                            // 取出单个Message
                            JSONObject jsonObject = messageList.getJSONObject(i);
                            // 存入链表中
                            HashMap<String, String> row = new HashMap<>();
                            row.put("title", jsonObject.getString("title"));
                            row.put("date", jsonObject.getString("date"));
                            row.put("time", jsonObject.getString("time"));
                            row.put("from_user", jsonObject.getString("from_user"));
                            row.put("content", jsonObject.getString("content"));
                            data.add(row);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // 用getActivity 替换 MessageFragment.this
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 数据下载完毕之后，将加载数据到消息列表中
                            myAdapter = new MyAdapter();
                            msgRecycleView.setAdapter(myAdapter);
                            // 记录下刷新时间
                            GData.setLastFreshMsg(System.currentTimeMillis());
                            Log.d("TAG", "GData.getLastFreshMsg():" + GData.getLastFreshMsg());

                        }
                    });
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_msg_fresh) {
            Toast.makeText(getContext().getApplicationContext(), "正在刷新", Toast.LENGTH_SHORT).show();
            FetchMsg();
        }
    }


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
            holder.name.setText(data.get(position).get("from_user"));
            holder.title.setText(data.get(position).get("title"));
            holder.date.setText(data.get(position).get("date"));
//            holder.time.
            // 该方法也可以对 name title data进行点击事件处理

            // 单个项目的短按事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getContext().getApplicationContext(), "position:" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext().getApplicationContext(), DetlMsgActivity.class);
                    // 传入参数
                    intent.putExtra("from_user", data.get(position).get("from_user"));
                    intent.putExtra("title", data.get(position).get("title"));
                    intent.putExtra("content", data.get(position).get("content"));
                    intent.putExtra("date", data.get(position).get("date"));
                    intent.putExtra("time", data.get(position).get("time"));
                    startActivity(intent);
                }
            });


            // 单个消息的长按事件
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("MyAdapter TAG", "position:" + position);

                    // 使用getContext().getApplicationContext() 获取Context
//                    Toast.makeText(getContext().getApplicationContext(), "position:" + position, Toast.LENGTH_SHORT).show();
                    showPopMenu(v, position);
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


    // 显示删除按钮
    public void showPopMenu(View view, final int pos) {
        PopupMenu popupMenu = new PopupMenu(getContext().getApplicationContext(), view);
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
