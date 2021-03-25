package com.example.eleme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.eleme.data.FullRedution;
import com.example.eleme.fragments.CommentsFragment;
import com.example.eleme.fragments.GetTicketFragment;
import com.example.eleme.fragments.GoodsFragment;
import com.example.eleme.fragments.ShopCartFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShopActivity  extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    TextView name;
    int shopId;
    List<FullRedution> frlist;
    TextView f1;
    TextView f2;
    TextView f3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        final Context c = this;
        frlist = new ArrayList<FullRedution>();
        initFR();
        f1 = findViewById(R.id.full1);
        f2 = findViewById(R.id.full2);
        f3 = findViewById(R.id.full3);
        shopId = getIntent().getIntExtra("shop_id", 0);//源activity传入的参数
        final String shopName = getIntent().getStringExtra("shop_name");
        GoodsFragment fragment = new GoodsFragment(shopId, c);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_shop, fragment).commit();
        name = findViewById(R.id.shopname);
        name.setText(shopName);
        bottomNavigationView = findViewById(R.id.shop_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.allgoods:
                        GoodsFragment gfragment = new GoodsFragment(shopId, c);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_shop, gfragment).commit();
                        break;
                    case R.id.comment:
                        CommentsFragment cfragment = new CommentsFragment(shopId, c);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_shop, cfragment).commit();

                        break;
                    case R.id.cart:
                        ShopCartFragment sfragment = new ShopCartFragment(shopId, c);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_shop, sfragment).commit();

                        break;
                    case R.id.shoptickets:
                        GetTicketFragment tfragment = new GetTicketFragment(shopId, c);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_shop, tfragment).commit();

                        break;
                }
                return true;
            }
        });
        Toolbar toolbar = findViewById(R.id.shop_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {//查看订单信息
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, MainPage.class);
                startActivity(intent);
            }
        });

    }

    private void initFR() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    FormBody.Builder params = new FormBody.Builder();
                    OkHttpClient client = new OkHttpClient();//创建http客户端
                    Request request = new Request.Builder()
                            .url(MainActivity.service+"/queryallshop")
                            .post(params.build())
                            .build();//创造http请求

                    Response responese = client.newCall(request).execute();//执行发送的指令
                    final String responseData = responese.body().string();//获取返回回来的json格式的结果
                    JSONArray ja = new JSONArray(responseData);//字符串转jsonarray


                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jb = ja.getJSONObject(i);

                        FormBody.Builder params1 = new FormBody.Builder();//查询满减
                        params1.add("shop_id", jb.getInt("shop_id") + "");
                        OkHttpClient client1 = new OkHttpClient();//创建http客户端
                        Request request1 = new Request.Builder()
                                .url(MainActivity.service+"/queryfullredution")
                                .post(params1.build())
                                .build();//创造http请求

                        Response responese1 = client1.newCall(request1).execute();//执行发送的指令
                        final String responseData1 = responese1.body().string();//获取返回回来的json格式的结果
                        JSONArray frJA = new JSONArray(responseData1);//字符串转jsonarray
                        for (int j = 0; j < frJA.length(); j++) {
                            JSONObject frJB = frJA.getJSONObject(j);
                            FullRedution fr = new FullRedution(frJB.getDouble("full_money"), frJB.getDouble("redution_money"));
                            frlist.add(fr);
                        }
                        fr();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(ShopActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();
    }
    private void fr(){
        if (frlist.size() == 1) f1.setText(frlist.get(0).full + "减" + frlist.get(0).redution);
        else if (frlist.size() == 2) {
            f1.setText(frlist.get(0).full + "减" + frlist.get(0).redution);
            f2.setText(frlist.get(1).full + "减" + frlist.get(1).redution);
        } else if (frlist.size() == 3) {
            f1.setText(frlist.get(0).full + "减" + frlist.get(0).redution);
            f2.setText(frlist.get(1).full + "减" + frlist.get(1).redution);
            f3.setText(frlist.get(2).full + "减" + frlist.get(2).redution);
        }
    }
}