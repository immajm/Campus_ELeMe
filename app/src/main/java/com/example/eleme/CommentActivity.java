package com.example.eleme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentActivity extends AppCompatActivity {
    public List<Object> list=new ArrayList<>();
    Button buy;
    EditText eComment;
    RatingBar shopRatingBar;
    RatingBar riderRatingBar;
    int shopRating;
    int riderRating;
    int orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        final Context c=this;
        orderId= getIntent().getIntExtra("orderId",0);
        Toolbar toolbar = findViewById(R.id.comment_toolbar);
        toolbar.setTitle("订单评价");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);//决定左上角的图标是否可以点击
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//决定左上角图标的右侧是否有向左的小箭头
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {//查看订单信息
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentActivity.this, MainPage.class);
                startActivity(intent);
            }
        });
        eComment=findViewById(R.id.edit_comment);
        shopRatingBar = (RatingBar) findViewById(R.id.ratingbar_comment);
        riderRatingBar = (RatingBar) findViewById(R.id.ratingbar_ridercomment);
        buy=(Button)findViewById(R.id.comment_btn_comment);
            shopRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    Toast.makeText(CommentActivity.this, "评价了" + rating + "星", Toast.LENGTH_SHORT).show();
                    shopRating=(int)rating;
                }
            });

            riderRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    Toast.makeText(CommentActivity.this, "评价了" + rating + "星", Toast.LENGTH_SHORT).show();
                    riderRating=(int)rating;
                }
            });
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                FormBody.Builder params1=new FormBody.Builder();
                                params1.add("order_id",orderId+"");
                                Log.v("orderid",orderId+"");
                                OkHttpClient client1=new OkHttpClient();//创建http客户端
                                Request request1=new Request.Builder()
                                        .url(MainActivity.service+"/queryorders")
                                        .post(params1.build())
                                        .build();//创造http请求
                                Response responese1=client1.newCall(request1).execute();
                                String responseorder=responese1.body().string();
                                JSONObject jb=new JSONObject(responseorder);


                                FormBody.Builder params=new FormBody.Builder();
                                params.add("order_id",orderId+"");
                                params.add("rider_id",jb.getInt("rider_id")+"");
                                params.add("shop_id",jb.getInt("shop_id")+"");
                                params.add("customer_id",MainActivity.customer_id+"");
                                params.add("comment_content", eComment.getText().toString());
                                params.add("riders_star", riderRating+"");
                                params.add("star", shopRating+"");
                                OkHttpClient client=new OkHttpClient();//创建http客户端
                                Request request=new Request.Builder()
                                        .url(MainActivity.service+"/assess")
                                        .post(params.build())
                                        .build();//创造http请求
                                Response responese=client.newCall(request).execute();
                                final String responseData=responese.body().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(CommentActivity.this, responseData, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }catch(Exception e){
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(CommentActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    }).start();

                    Toast.makeText(CommentActivity.this, "感谢您的评价！欢迎继续点餐", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CommentActivity.this, MainPage.class);
                    startActivity(intent);
                }
            });


    }

}