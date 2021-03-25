package com.example.eleme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class ShowMsgActivity extends AppCompatActivity {
    public static List<Object> list=new ArrayList<>();
    //static int shopId;
    static int orderId;
    static Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_orderdetail);
        orderId= getIntent().getIntExtra("order_id",0);
        //shopId= getIntent().getIntExtra("shop_id",0);
        Toolbar toolbar = findViewById(R.id.shop_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitle("订单详情");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {//查看订单信息
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowMsgActivity.this, MainPage.class);
                startActivity(intent);
            }
        });

        RecyclerView recycleView = (RecyclerView) findViewById(R.id.showmsg_recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager((this));
        recycleView.setLayoutManager(layoutManager);
        initGoods();
        OneGoodsInCartAdapter adapter = new OneGoodsInCartAdapter(list);
        recycleView.setAdapter(adapter);
    }
    private void initGoods(){
        list.clear();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    FormBody.Builder params=new FormBody.Builder();
                    params.add("order_id", orderId+"");
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url(MainActivity.service+"/queryorderdetails")
                            .post(params.build())
                            .build();//创造http请求
                    Response responese=client.newCall(request).execute();//查询得detail数组
                    final String responseData=responese.body().string();
                    JSONArray orderdetailJA=new JSONArray(responseData);

                    FormBody.Builder params1=new FormBody.Builder();
                    params1.add("order_id", orderId+"");
                    OkHttpClient client1=new OkHttpClient();//创建http客户端
                    Request request1=new Request.Builder()
                            .url(MainActivity.service+"/queryordergoods")
                            .post(params1.build())
                            .build();//创造http请求
                    Response responese1=client1.newCall(request1).execute();//执行发送的指令
                    String responseData1=responese1.body().string();//获取返回回来的json格式的结果
                    JSONArray responseGoodsname=new JSONArray(responseData1);

                    for(int j=0;j<orderdetailJA.length();j++){
                        JSONObject jb=orderdetailJA.getJSONObject(j);

                            OneCart a=new OneCart(
                                    R.drawable.noodles,
                                    responseGoodsname.get(j).toString(),
                                    jb.getInt("order_onegoods_count"),
                                    jb.getDouble("d_price"),
                                    jb.getDouble("d_price")+2
                            );
                            list.add(a);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                buildGoods();
                            }
                        });
                    }





                }catch(Exception e){
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(ShowMsgActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();

                }
            }
        }).start();

    }
    public void buildGoods(){
        RecyclerView recycleView = (RecyclerView) findViewById(R.id.showmsg_recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager((this));
        recycleView.setLayoutManager(layoutManager);
        OneGoodsInCartAdapter adapter = new OneGoodsInCartAdapter(list);
        recycleView.setAdapter(adapter);
    }
}