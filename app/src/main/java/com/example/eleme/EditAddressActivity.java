package com.example.eleme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EditAddressActivity extends AppCompatActivity {
    int type;//1代表是选择地址去下单   0代表仅查看修改
    int shopId;
    public List<Object> list=new ArrayList<>();
    EditText name;
    EditText tel;
    EditText province;
    EditText town;
    EditText block;
    EditText specific;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        shopId= getIntent().getIntExtra("shop_id",1);
        type=getIntent().getIntExtra("type",0);
        Context c=this;
        name=findViewById(R.id.edit_contactname);
        tel=findViewById(R.id.edit_contacttel);
        province=findViewById(R.id.edit_province);
        town=findViewById(R.id.edit_town);
        block=findViewById(R.id.edit_block);
        specific=findViewById(R.id.edit_specific);
        save=findViewById(R.id.edit_btn_save);

        Toolbar toolbar = findViewById(R.id.editaddress_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("选择收货地址");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {//不买了  返回商店activity
            @Override
            public void onClick(View v) {
                if(type==1){
                    Intent intent = new Intent(EditAddressActivity.this, ShopActivity.class);
                    intent.putExtra("shop_id", shopId);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(EditAddressActivity.this, MainPage.class);
                    startActivity(intent);
                }
            }
        });
        save=(Button)findViewById(R.id.edit_btn_save);
        save.setOnClickListener(new saveAddressListener());

    }
    private class saveAddressListener implements View.OnClickListener{//保存并使用新建的地址

        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        FormBody.Builder params=new FormBody.Builder();
                        params.add("customer_id", MainActivity.customer_id+"");
                        params.add("province",province.getText().toString());
                        params.add("town",town.getText().toString());
                        params.add("block",block.getText().toString());
                        params.add("specific_address",specific.getText().toString());
                        params.add("contact_name",name.getText().toString());
                        params.add("contact_tel",tel.getText().toString());
                        OkHttpClient client=new OkHttpClient();//创建http客户端
                        Request request=new Request.Builder()
                                .url(MainActivity.service+"/addaddress")
                                .post(params.build())
                                .build();//创造http请求

                        Response responese=client.newCall(request).execute();//执行发送的指令
                        final String responseData=responese.body().string();//返回地址id
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(EditAddressActivity.this, responseData.substring(0,7), Toast.LENGTH_SHORT).show();
                            }
                        });
                        if(type==1){
                            Intent intent = new Intent(EditAddressActivity.this, BuyActivity.class);
                            intent.putExtra("address_id", Integer.parseInt(responseData.substring(7,responseData.length())));
                            intent.putExtra("address_address", province.getText().toString()+town.getText().toString()+block.getText().toString()+specific.getText().toString());
                            Log.v("address",province.getText().toString()+town.getText().toString()+block.getText().toString()+specific.getText().toString());
                            intent.putExtra("address_nameandtel",name.getText().toString()+" "+tel.getText().toString());
                            intent.putExtra("shop_id",shopId);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(EditAddressActivity.this, MainPage.class);
                            startActivity(intent);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(EditAddressActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }
    }

}