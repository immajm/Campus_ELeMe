package com.example.eleme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyMsgActivity extends AppCompatActivity {
    public List<Object> list=new ArrayList<>();
    EditText name;
    EditText tel;
    EditText sex;
    EditText pwd;
    EditText mail;
    EditText city;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_msg);
        name=findViewById(R.id.msg_customername);
        name.setEnabled(false);
        tel=findViewById(R.id.msg_tel);
        sex=findViewById(R.id.msg_sex);
        pwd=findViewById(R.id.msg_pwd);
        mail=findViewById(R.id.msg_email);
        city=findViewById(R.id.msg_city);
        save=findViewById(R.id.msg_btn_save);
        initMsg();
        Toolbar toolbar = findViewById(R.id.msg_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyMsgActivity.this, MainPage.class);
                startActivity(intent);
            }
        });
        save=(Button)findViewById(R.id.msg_btn_save);
        save.setOnClickListener(new saveMsgListener());
    }
    private class saveMsgListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        FormBody.Builder params=new FormBody.Builder();
                        params.add("customer_id", MainActivity.customer_id+"");
                        params.add("customer_name",name.getText().toString());
                        params.add("customer_sex",sex.getText().toString());
                        params.add("customer_pwd",pwd.getText().toString());
                        params.add("customer_tel",tel.getText().toString());
                        params.add("customer_mail",mail.getText().toString());
                        params.add("customer_city",city.getText().toString());
                        OkHttpClient client=new OkHttpClient();//创建http客户端
                        Request request=new Request.Builder()
                                .url(MainActivity.service+"/updateusermessage")
                                .post(params.build())
                                .build();//创造http请求

                        Response responese=client.newCall(request).execute();//执行发送的指令
                        final String responseData=responese.body().string();
                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              Toast.makeText(MyMsgActivity.this, responseData, Toast.LENGTH_SHORT).show();
                                          }
                                      });
                        Intent intent = new Intent(MyMsgActivity.this, MainPage.class);
                        startActivity(intent);

                    }catch(Exception e){
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MyMsgActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            }).start();
        }
    }

    private void initMsg(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FormBody.Builder params=new FormBody.Builder();
                    params.add("id", MainActivity.customer_id+"");
                    OkHttpClient client=new OkHttpClient();//创建http客户端
                    Request request=new Request.Builder()
                            .url(MainActivity.service+"/queryuser")
                            .post(params.build())
                            .build();//创造http请求

                    Response responese=client.newCall(request).execute();
                    final String responseData=responese.body().string();
                    final JSONObject json=new JSONObject(responseData);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                name.setText(json.getString("customer_name"));
                                sex.setText(json.getString("customer_sex"));
                                pwd.setText(json.getString("customer_pwd"));
                                tel.setText(json.getString("customer_tel"));
                                mail.setText(json.getString("customer_mail"));
                                city.setText(json.getString("customer_city"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }catch(Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyMsgActivity.this, "个人信息获取失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}