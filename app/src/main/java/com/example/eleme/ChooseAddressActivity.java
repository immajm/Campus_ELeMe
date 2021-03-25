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

import com.example.eleme.adapter.AddressAdapter;
import com.example.eleme.data.OneAddress;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChooseAddressActivity extends AppCompatActivity {
    public List<Object> list=new ArrayList<>();
    FloatingActionButton add;
    public int shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.activity_choose_address);
        shopId=getIntent().getIntExtra("shop_id",1);
        final Context c=this;
        Toolbar toolbar = findViewById(R.id.chooseaddress_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("选择收货地址");//不能直接返回 必须选择地址  新建或选择已存在的
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        add=findViewById(R.id.choose_address_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//添加收货地址
                Intent intent = new Intent(ChooseAddressActivity.this, EditAddressActivity.class);
                intent.putExtra("type",1);//1代表是添加地址 去下单
                startActivity(intent);
            }
        });
        initAddresss();
        buildAddress();
    }
    private void initAddresss(){
        list.clear();
        //list.add(new OneAddress(1,"zhedacsxy","lsm","198"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FormBody.Builder params=new FormBody.Builder();
                    params.add("id",MainActivity.customer_id+"");
                    OkHttpClient client=new OkHttpClient();//创建http客户端
                    Request request=new Request.Builder()
                            .url(MainActivity.service+"/queryaddress")
                            .post(params.build())
                            .build();//创造http请求
                    Response responese=client.newCall(request).execute();//执行发送的指令
                    final String responseData=responese.body().string();//获取返回的json格式的结果
                    JSONArray ja=new JSONArray(responseData);//字符串转jsonarray
                    for(int i=0;i<ja.length();i++){
                        JSONObject jb=ja.getJSONObject(i);
                        OneAddress a=new OneAddress(jb.getInt("address_id"),
                                jb.getString("province")+jb.getString("town")+jb.getString("block")+jb.getString("specific_address"),
                                jb.getString("contact_name"),
                                jb.getString("contact_tel"));
                        list.add(a);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                buildAddress();
                            }
                        });

                    }
                }catch(Exception e){
                    e.printStackTrace();
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(ChooseAddressActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();

    }
    public void buildAddress(){
        RecyclerView recycleView = findViewById(R.id.choose_address_recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager((this));
        recycleView.setLayoutManager(layoutManager);
        AddressAdapter adapter = new AddressAdapter(list,1,shopId);//1代表是选择地址 去下单
        recycleView.setAdapter(adapter);
    }
}