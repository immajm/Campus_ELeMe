package com.example.eleme.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.BuyActivity;
import com.example.eleme.MainActivity;
import com.example.eleme.R;
import com.example.eleme.adapter.OneGoodsInCartAdapter;
import com.example.eleme.data.OneCart;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShopCartFragment extends Fragment {
    static List<Object> list=new ArrayList<>();//列表
    int shopId;
    Context c;
    Button buy;
    View view;
    public ShopCartFragment(int s, Context context) {
        shopId=s;
        Log.v("shopid",shopId+"");
        c=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initCart();
        view = inflater.inflate(R.layout.fragment_shop_cart, container, false);
        buildcart();
        buy=(Button)view.findViewById(R.id.shop_cart_btn_buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), BuyActivity.class);
                intent.putExtra("shop_id",shopId);//用户id全局，传入商店id，一个商店一起结算
                Log.v("shopid!!!before",shopId+"");
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
    private void initCart(){
        list.clear();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    FormBody.Builder params=new FormBody.Builder();
                    params.add("customer_id", MainActivity.customer_id+"");
                    params.add("shop_id",shopId+"");
                    OkHttpClient client=new OkHttpClient();//创建http客户端
                    Request request=new Request.Builder()
                            .url(MainActivity.service+"/queryusershopcart")
                            .post(params.build())
                            .build();//创造http请求
                    Response responese=client.newCall(request).execute();//执行发送的指令
                    final String responseData=responese.body().string();//获取返回回来的json格式的结果
                    JSONArray ja=new JSONArray(responseData);//字符串转jsonarray

                    for(int i=0;i<ja.length();i++){
                        JSONObject jb=ja.getJSONObject(i);

                        FormBody.Builder params1=new FormBody.Builder();
                        params1.add("goods_id", jb.getInt("goods_id")+"");
                        Log.v("goodsid",jb.getInt("goods_id")+"");
                        OkHttpClient client1=new OkHttpClient();//创建http客户端
                        Request request1=new Request.Builder()
                                .url(MainActivity.service+"/queryonegoods")
                                .post(params1.build())
                                .build();//创造http请求
                        Response responese1=client1.newCall(request1).execute();//执行发送的指令
                        final String responseData1=responese1.body().string();//获取返回回来的json格式的结果
                        JSONArray ja1=new JSONArray(responseData1);
                        JSONObject jb1=ja1.getJSONObject(0);
                        OneCart a=new OneCart(
                                R.drawable.noodles,
                                jb1.getString("goods_name"),
                                jb.getInt("goods_count"),
                                Float.parseFloat(jb1.getString("d_price")),
                                Float.parseFloat(jb1.getString("d_price"))+2
                        );
                        list.add(a);
                    }
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            buildcart();
                            if(list.size()==0) buy.setVisibility(View.GONE);//购物车为空
                            else buy.setVisibility(View.VISIBLE);
                        }
                    });

                }catch(Exception e){
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(c, "获取失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();

                }
            }
        }).start();
    }
    public void buildcart(){
        RecyclerView recycleView = view.findViewById(R.id.cart_recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recycleView.setLayoutManager(layoutManager);
        OneGoodsInCartAdapter adapter = new OneGoodsInCartAdapter(list);
        recycleView.setAdapter(adapter);
    }
}
