package com.example.eleme.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.MainActivity;
import com.example.eleme.adapter.OrdersAdapter;
import com.example.eleme.R;
import com.example.eleme.data.OneOrder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrdersFragment extends Fragment {

    public static List<Object> list=new ArrayList<>();//订单列表
    static Context c;
    static View view;
    static OrdersAdapter adapter;
    static RecyclerView recycleView;
    public OrdersFragment(Context c) {
        this.c = c;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initOrders();
        view = inflater.inflate(R.layout.fragment_orders, container, false);

        recycleView = view.findViewById(R.id.orders_recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recycleView.setLayoutManager(layoutManager);
        adapter = new OrdersAdapter(list);
        recycleView.setAdapter(adapter);
        return view;
    }
    private void initOrders(){
        list.clear();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    FormBody.Builder params=new FormBody.Builder();
                    params.add("customer_id", MainActivity.customer_id +"");
                    OkHttpClient client=new OkHttpClient();//创建http客户端
                    Request request=new Request.Builder()
                            .url(MainActivity.service+"/queryuserorders")
                            .post(params.build())
                            .build();//创造http请求

                    Response responese=client.newCall(request).execute();//执行发送的指令
                    final String responseData=responese.body().string();//获取返回回来的json格式的结果
                    JSONArray ja=new JSONArray(responseData);//字符串转jsonarray

                    for(int i=0;i<ja.length();i++){
                        JSONObject jb=ja.getJSONObject(i);
                        List<String> namelist=new ArrayList<String>();


                        FormBody.Builder params1=new FormBody.Builder();//查询所有商品  取商品名称
                        params1.add("order_id",  jb.getInt("order_id")+"");
                        Log.d("orf", jb.getInt("order_id")+"");
                        OkHttpClient client1=new OkHttpClient();//创建http客户端
                        Request request1=new Request.Builder()
                                .url(MainActivity.service+"/queryordergoods")
                                .post(params1.build())
                                .build();//创造http请求

                        Response responese1=client1.newCall(request1).execute();//执行发送的指令
                        final String responseData1=responese1.body().string();//获取返回回来的json格式的结果
                        JSONArray goodsJA=new JSONArray(responseData1);//字符串转jsonarray
                        for(int j=0;j<goodsJA.length();j++){
                            namelist.add(goodsJA.get(j).toString());
                            Log.d("goodsname", goodsJA.get(j).toString());
                        }



                        FormBody.Builder params2=new FormBody.Builder();//查商店名称
                        params2.add("order_id",  jb.getInt("order_id")+"");
                        OkHttpClient client2=new OkHttpClient();//创建http客户端
                        Request request2=new Request.Builder()
                                .url(MainActivity.service+"/queryshop_byorder")
                                .post(params1.build())
                                .build();//创造http请求

                        Response responese2=client2.newCall(request2).execute();//执行发送的指令
                        final String responseData2=responese2.body().string();//获取返回回来的json格式的结果

                       OneOrder a=new OneOrder(
                               jb.getInt("order_id"),
                               responseData2,
                                R.drawable.noodles,
                                namelist,
                               goodsJA.length(),
                                (float) jb.getDouble("final_price"),
                               jb.getString("order_condition"),
                                jb.getString("ifcomment"),
                               jb.getInt("address_id"),
                               jb.getInt("ticket_id"),
                               jb.getInt("full_redution_id"),
                               jb.getInt("rider_id")
                        );
                        list.add(a);
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                buildOrders();
                            }
                        });

                    }

                }catch(Exception e){
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(c, "获取失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();

                }
            }
        }).start();

    }
    public static void buildOrders(){
        recycleView = view.findViewById(R.id.orders_recycleView);
        LinearLayoutManager layoutManagerright= new LinearLayoutManager(c,LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(layoutManagerright);
        adapter= new OrdersAdapter(list);
        recycleView.setAdapter(adapter);
    }
}
