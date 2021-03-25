package com.example.eleme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static int customer_id;
    public static String service="http://39.102.32.5:8082";
    public static Context c;
    Button login;
    EditText name;
    EditText password;
    TextView checkPassword;
    EditText password2;
    Button signAndLogin;
    TextView signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c=this;
        login=findViewById(R.id.btn_login);
        name=findViewById(R.id.edit1);
        password=findViewById(R.id.edit2);
        checkPassword=findViewById(R.id.text3);
        password2=findViewById(R.id.edit3);
        signAndLogin=findViewById(R.id.btn_signAndLogin);

        signup=findViewById(R.id.text4);
        signup.setOnClickListener(new signTextOnClick());
        login.setOnClickListener(new loginOnClick());
        signAndLogin.setOnClickListener(new signAndLoginOnClick());
    }

    private int flag=1;//用于标记正在登录/注册
    private class  signTextOnClick  implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(flag==1){
                password2.setVisibility(View.VISIBLE);
                checkPassword.setVisibility(View.VISIBLE);
                signAndLogin.setVisibility(View.VISIBLE);
                login.setVisibility(View.INVISIBLE);
                signup.setText("已有帐号，去登录");
            }
            else{

                password2.setVisibility(View.GONE);
                checkPassword.setVisibility(View.GONE);
                signAndLogin.setVisibility(View.INVISIBLE);
                login.setVisibility(View.VISIBLE);
                signup.setText("还没有账户？建一个吧");
            }
            flag=-flag;
        }
    }
    private class loginOnClick implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(MainActivity.this, ShopActivity.class);
//            intent.putExtra("shop_id",1);
//            intent.putExtra("shop_name","shop");
//            startActivity(intent);
            if(name.getText().toString().equals("")||password.getText().toString().equals("")){
                Toast.makeText(MainActivity.this, "登陆失败，用户名与密码不能为空！", Toast.LENGTH_SHORT).show();
            }
            else{
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FormBody.Builder params=new FormBody.Builder();
                            params.add("name",name.getText().toString());
                            params.add("pwd",password.getText().toString());
                            OkHttpClient client=new OkHttpClient();//创建http客户端

                            Request request=new Request.Builder()
                                    .url(service+"/loginuser")//http://10.64.134.210:0808/queryuser
                                    .post(params.build())
                                    .build();//创造http请求
                            Response responese=client.newCall(request).execute();//执行发送的指令
                            final String responseDate=responese.body().string();

                            if(responseDate.substring(0,5).equals("登陆成功！")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                                        customer_id=Integer.parseInt(responseDate.substring(5,responseDate.length()));
                                    }
                                });
                                Intent intent = new Intent(MainActivity.this, MainPage.class);
                                startActivity(intent);
                            }
                            else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, responseDate, Toast.LENGTH_SHORT).show();

                                    }
                                });
                            }


                        }catch(Exception e){
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();


            }

        }

    }
    private class  signAndLoginOnClick  implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if (name.getText().toString().equals("") || password.getText().toString().equals("") || password2.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "注册失败，用户名与密码不能为空！", Toast.LENGTH_SHORT).show();
            } else if (!password.getText().toString().equals(password2.getText().toString())) {
                Toast.makeText(MainActivity.this, "注册失败，两次密码不一致！", Toast.LENGTH_SHORT).show();

            } else {//用户注册
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FormBody.Builder params = new FormBody.Builder();
                            params.add("name", name.getText().toString());
                            params.add("pwd1", password.getText().toString());
                            params.add("pwd2", password2.getText().toString());
                            OkHttpClient client = new OkHttpClient();//创建http客户端
                            Request request = new Request.Builder()
                                    .url("http://192.168.43.23:8082/registeruser")//http://10.64.134.210:0808/queryuser
                                    .post(params.build())
                                    .build();//创造http请求
                            Response responese = client.newCall(request).execute();//执行发送的指令
                            final String responseDate = responese.body().string();//获取返回的json格式的结果
                            if (!responseDate.substring(0, 7).equals("用户注册成功！")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, responseDate, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "用户注册成功！", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                customer_id = Integer.parseInt(responseDate.substring(7, responseDate.length()));
                                Intent intent = new Intent(MainActivity.this, MainPage.class);
                                startActivity(intent);
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        }

    }
}