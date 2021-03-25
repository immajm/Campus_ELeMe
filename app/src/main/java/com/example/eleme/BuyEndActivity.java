package com.example.eleme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class BuyEndActivity extends AppCompatActivity {
    int orderId;
    int shopId;
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_end);
        orderId= getIntent().getIntExtra("order_id",0);
        shopId= getIntent().getIntExtra("shop_id",0);
        show=findViewById(R.id.show);
        final Context c=this;
        Toolbar toolbar = findViewById(R.id.buy_end_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyEndActivity.this, MainPage.class);
                startActivity(intent);
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//查看订单信息
                Intent intent = new Intent(BuyEndActivity.this, ShowMsgActivity.class);
                intent.putExtra("order_id",orderId);
                intent.putExtra("shop_id",shopId);
                startActivity(intent);
            }
        });

    }


}