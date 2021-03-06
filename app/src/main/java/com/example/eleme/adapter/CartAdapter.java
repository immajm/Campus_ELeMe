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
                    //?????????????????????
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //?????????????????????
                            Looper.prepare();
                            Toast.makeText(view.getContext(), ((OneshopCart) list.get(position)).getShopname()+"??????", Toast.LENGTH_SHORT).show();
                            Looper.loop();// ??????loop?????????????????????????????????
                            Intent intent = new Intent(view.getContext(), BuyActivity.class);
                            intent.putExtra("shop_id",((OneshopCart)list.get(position)).getShopId());//??????id?????????????????????id???????????????????????????
                            view.getContext().startActivity(intent);
                        }
                    }).start();
                }
            });
            ((CartViewHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    //?????????????????????????????????????????????
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Looper.prepare();
                                Toast.makeText(view.getContext(), ((OneshopCart) list.get(position)).getShopname()+"????????????", Toast.LENGTH_SHORT).show();
                                Looper.loop();// ??????loop?????????????????????????????????
                                FormBody.Builder params=new FormBody.Builder();
                                params.add("customer_id", MainActivity.customer_id+"");
                                params.add("shop_id",((OneshopCart) list.get(position)).getShopId()+"");
                                OkHttpClient client=new OkHttpClient();//??????http?????????
                                Request request=new Request.Builder()
                                        .url(MainActivity.service+"/delete")
                                        .post(params.build())
                                        .build();//??????http??????
                                Response responese=client.newCall(request).execute();//?????????????????????
                                final String responseData=responese.body().string();//???????????????json???????????????
                                Toast.makeText(view.getContext(), "????????????", Toast.LENGTH_SHORT).show();

                            }catch(Exception e){
                                e.printStackTrace();
                                Toast.makeText(view.getContext(), "????????????", Toast.LENGTH_SHORT).show();
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
