package com.example.eleme.wjl;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.BuyActivity;
import com.example.eleme.MainActivity;
import com.example.eleme.wjl.adapter.CartContentRecyclerAdapter;
import com.example.eleme.wjl.bean.BeanCart;
import com.example.eleme.wjl.bean.BeanCartItem;
import com.example.eleme.wjl.bean.BeanGoods;
import com.example.eleme.wjl.components.MyNumImageView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import com.example.eleme.R;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodsListActivity extends AppCompatActivity {
    private AppBarLayout appBarLayout;
    public static BeanCart beanCarts;
    public static int shopId;
    private Button btn;
    private MyNumImageView eleme;
    private TextView total_price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wjl);
        beanCarts = new BeanCart();
        shopId = getIntent().getIntExtra("shop_id", 1);
        appBarLayout = findViewById(R.id.appBar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
                int color = Color.argb(200, 0, 0, 0);
                collapsingToolbar.setCollapsedTitleTextColor(color);
                ImageView imageView = findViewById(R.id.bgc_img);
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) { // ????????????
                    //?????????????????????
                    imageView.setVisibility(View.GONE);
                } else {
                    // ???????????????
                    //???????????????
                    collapsingToolbar.setTitle("");
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });

        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void run() {
                try {
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("customer_id", MainActivity.customer_id + "");
                    params.add("shop_id", shopId + "");
                    Log.v("shopid:after",shopId + "");
                    OkHttpClient client = new OkHttpClient();//??????http?????????
                    Request request = new Request.Builder()
                            .url(MainActivity.service + "/queryusershopcart")
                            .post(params.build())
                            .build();//??????http??????
                    Response responese = client.newCall(request).execute();//?????????????????????
                    final String responseData = responese.body().string();//?????????????????????json???????????????
                    JSONArray ja = new JSONArray(responseData);//????????????jsonarray
                    int data = 0;
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject jb = ja.getJSONObject(i);

                        FormBody.Builder params1 = new FormBody.Builder();
                        params1.add("goods_id", jb.getInt("goods_id") + "");
                        OkHttpClient client1 = new OkHttpClient();//??????http?????????
                        Request request1 = new Request.Builder()
                                .url(MainActivity.service + "/queryonegoods")
                                .post(params1.build())
                                .build();//??????http??????
                        Response responese1 = client1.newCall(request1).execute();//?????????????????????
                        final String responseData1 = responese1.body().string();//?????????????????????json???????????????
                        JSONArray ja1 = new JSONArray(responseData1);
                        JSONObject jb1 = ja1.getJSONObject(0);
                        BeanCartItem beanCartItem = new BeanCartItem();
                        int tem = jb.getInt("goods_count");
                        data += tem;
                        beanCartItem.setNum(tem);

                        BeanGoods a = new BeanGoods();
                        a.setGoodsid(jb.getString("goods_id"));
                        a.setClassid(jb.getInt("class_id") + "");
                        a.setImg(R.drawable.glgs);
                        a.setDes("?????????2???+?????????2???");
                        a.setName(jb1.getString("goods_name"));
                        a.setPrice(jb1.getString("d_price"));
                        a.setNum("??????102");
                        a.setVipprice("??????????????????31.9");
//                        a.setTag(String.valueOf(index));
//                        a.setTitlename(listclass[index].getMenuname());
                        beanCartItem.setBeanGoods(a);
//                        beanCarts.addGoods(a);
                    }
                    final int finalData = data;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (finalData == 0) {
                                eleme.setNum(finalData);
                                total_price.setText("???????????????");
                                btn.setText("??20??????");
                                btn.setBackgroundColor(Color.rgb(230, 230, 230));
                            } else {
                                eleme.setNum(finalData);
                                btn.setText("?????????");
                                total_price.setText(String.valueOf(beanCarts.getTotalPrice()));
                                btn.setBackgroundColor(Color.rgb(0, 0, 230));
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        ;
        eleme = findViewById(R.id.eleme);
        total_price = findViewById(R.id.total_price);
        btn = findViewById(R.id.btn_checkout);
        total_price.setText("???????????????");


//        goods=findViewById(R.id.);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoodsListActivity.this, BuyActivity.class);
                intent.putExtra("shop_id", shopId);//??????id?????????????????????id???????????????????????????
                startActivity(intent);
            }
        });

//        btn.setBackground(R.drawable.blue_shape);

        eleme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!beanCarts.isEmpty()) {
                    appear();
                }
            }
        });
        DataChangeListener dataChangeListener = new DataChangeListener() {
            @Override
            public void change(int data) {
                if (data == 0) {
                    eleme.setNum(data);
                    total_price.setText("???????????????");
                    btn.setText("??20??????");
                    btn.setBackgroundColor(Color.rgb(230, 230, 230));
                } else {
                    eleme.setNum(data);
                    btn.setText("?????????");
                    total_price.setText(String.valueOf(beanCarts.getTotalPrice()));
                    btn.setBackgroundColor(Color.rgb(0, 0, 230));
                }
            }
        };
        beanCarts.setDataChangeListener(dataChangeListener);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.business_toolbar_menu, menu);
        return true;
    }

    public void appear() {
        final Dialog dialog = new Dialog(this, R.style.bottom_anim_theme);
        View view = View.inflate(this, R.layout.fragment_goods_content_bottom, null);
        RecyclerView recyclerView = view.findViewById(R.id.cart_content_recycler);

        dialog.setContentView(view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        CartContentRecyclerAdapter cartContentRecyclerAdapter = new CartContentRecyclerAdapter(beanCarts);
        recyclerView.setAdapter(cartContentRecyclerAdapter);
        recyclerView.setLayoutManager(layoutManager);

        Window window = dialog.getWindow();
        //??????????????????
        window.setGravity(Gravity.BOTTOM);

        //??????????????????
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //?????????????????????
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.chosen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
}