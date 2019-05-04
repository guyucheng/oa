package com.example.oa;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oa.http.HttpUrls;
import com.example.oa.tools.UTF8Cvtor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
public class ContactsFragment extends Fragment {
    public ContactsFragment() {
        // Required empty public constructor
    }

    //申明一个视图对象,用来设置fragment的layout
    protected View mView;

    // 申明RecycleView的各项
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // 申明联系人数据
    private LinkedList<HashMap<String, String>> data;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_contacts, container, false);

        // 找到RecycleView的id
        recyclerView = (RecyclerView) mView.findViewById(R.id.rcc_contacts_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.mView.getContext()));     //线性布局

        FetchContacts();

        return mView;
    }





    // 从网络上拉取联系人
    public void FetchContacts() {
        // 拿到OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(HttpUrls.GET_ALL_USERS)
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
                    Log.d("ContactsFragment TAG", "request:" + result);
                    try {
                        JSONObject obj = new JSONObject(result);
                        JSONArray contactsList = obj.getJSONArray("user_list");

                        // 取出每一个消息
                        data = new LinkedList<>();

                        for (int i = 0; i < contactsList.length(); i++) {
                            // 取出单个联系人
                            JSONObject jsonObject = contactsList.getJSONObject(i);
                            // 存入链表中
                            HashMap<String, String> row = new HashMap<>();
                            row.put("address", jsonObject.getString("address"));
                            row.put("department", jsonObject.getString("department"));
                            row.put("email", jsonObject.getString("email"));
                            row.put("gender", jsonObject.getString("gender"));
                            row.put("name", jsonObject.getString("name"));
                            row.put("phone", jsonObject.getString("phone"));
                            row.put("username", jsonObject.getString("username"));
                            data.add(row);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // 用getActivity 替换 MessageFragment.this
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // 数据下载完毕之后，将加载数据到联系人列表中
                            myAdapter = new MyAdapter();
                            recyclerView.setAdapter(myAdapter);
                            GData.setContactData(data);
//                            // 记录下刷新时间
//                            GData.setLastFreshMsg(System.currentTimeMillis());
//                            Log.d("TAG", "GData.getLastFreshMsg():" + GData.getLastFreshMsg());

                        }
                    });
                }
            }
        });

    }



    // 加载联系人列表
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        class MyViewHolder extends RecyclerView.ViewHolder {
            public View itemView;
            public TextView name, department;

            public MyViewHolder(View v) {
                super(v);
                itemView = v;

                name = itemView.findViewById(R.id.tv_contact_item_name);
                department = itemView.findViewById(R.id.tv_contact_item_department);

            }
        }


        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // 从文件中找到消息显示的那个模板
            View msgView = LayoutInflater.from(mView.getContext())
                    .inflate(R.layout.contact_single_layout, parent, false);
            MyViewHolder vh = new MyViewHolder(msgView);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, final int position) {
            // 变更项目中的内容
            holder.name.setText(data.get(position).get("name"));
            holder.department.setText(data.get(position).get("department"));
//            holder.time.
            // 该方法也可以对 name title data进行点击事件处理

            // 单个项目的短按事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(getContext().getApplicationContext(), "position:" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext().getApplicationContext(), DetlUserActivity.class);
                    // 传入参数
                    intent.putExtra("name", data.get(position).get("name"));
                    intent.putExtra("address", data.get(position).get("address"));
                    intent.putExtra("email", data.get(position).get("email"));
                    intent.putExtra("gender", data.get(position).get("gender"));
                    intent.putExtra("phone", data.get(position).get("phone"));
                    intent.putExtra("username", data.get(position).get("username"));
                    intent.putExtra("department", data.get(position).get("phone"));

                    startActivity(intent);
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

}
