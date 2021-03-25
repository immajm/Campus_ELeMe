package com.example.eleme.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.BuyActivity;
import com.example.eleme.MainActivity;
import com.example.eleme.R;
import com.example.eleme.data.OneshopCart;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public List list;
    View view;
    class CartViewHolder extends RecyclerView.ViewHolder {
            TextView shopname;
            Button buy;
            TextView delete;
            public CartViewHolder(@NonNull View itemView) {
                super(itemView);
                shopname = (TextView) itemView.findViewById(R.id.cart_shopname);
                buy= (Button) itemView.findViewById(R.id.cart_btn_buy);
                delete=(TextView)itemView.findViewById(R.id.cart_delete);
            }


        }

        public CartAdapter(List<Object> l) {
            list = l;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneshop_cart, parent, false);
                CartViewHolder holder = new CartViewHolder(view);

                return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                OneshopCart c= (OneshopCart) list.get(position);
                ((CartViewHolder) holder).shopname.setText(c.getShopname());
            ((CartViewHolder) holder).buy.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    //跳转到购买界面
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //跳转到结算页面
                            Looper.prepare();
                            Toast.makeText(view.getContext(), ((OneshopCart) list.get(position)).getShopname()+"结算", Toast.LENGTH_SHORT).show();
                            Looper.loop();// 进入loop中的循环，查看消息队列
                            Intent intent = new Intent(view.getContext(), BuyActivity.class);
                            intent.putExtra("shop_id",((OneshopCart)list.get(position)).getShopId());//用户id全局，传入商店id，一个商店一起结算
                            view.getContext().startActivity(intent);
                        }
                    }).start();
                }
            });
            ((CartViewHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    //删除购物车中一个商店的所有商品
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Looper.prepare();
                                Toast.makeText(view.getContext(), ((OneshopCart) list.get(position)).getShopname()+"删除成功", Toast.LENGTH_SHORT).show();
                                Looper.loop();// 进入loop中的循环，查看消息队列
                                FormBody.Builder params=new FormBody.Builder();
                                params.add("customer_id", MainActivity.customer_id+"");
                                params.add("shop_id",((OneshopCart) list.get(position)).getShopId()+"");
                                OkHttpClient client=new OkHttpClient();//创建http客户端
                                Request request=new Request.Builder()
                                        .url(MainActivity.service+"/delete")
                                        .post(params.build())
                                        .build();//创造http请求
                                Response responese=client.newCall(request).execute();//执行发送的指令
                                final String responseData=responese.body().string();//获取返回的json格式的结果
                                Toast.makeText(view.getContext(), "删除成功", Toast.LENGTH_SHORT).show();

                            }catch(Exception e){
                                e.printStackTrace();
                                Toast.makeText(view.getContext(), "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).start();
                }
            });

        }
        @Override
        public int getItemCount() {
            return list.size();
        }

}
