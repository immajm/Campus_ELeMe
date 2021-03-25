package com.example.eleme.adapter;

import android.graphics.Paint;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.MainActivity;
import com.example.eleme.R;
import com.example.eleme.data.OneGoods;
import com.example.eleme.fragments.GoodsFragment;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List goodsList;

    class GoodsViewHolder extends RecyclerView.ViewHolder {
        ImageView goodsImg;
        TextView goodsName;
        TextView goodsmsg;
        TextView price;
        TextView price_d;
        Button add;
        public GoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            goodsImg=(ImageView) itemView.findViewById(R.id.goodsimg);
            goodsName=(TextView) itemView.findViewById(R.id.goodsname);
            price=(TextView) itemView.findViewById(R.id.price);
            price_d=(TextView) itemView.findViewById(R.id.price_d);
            goodsmsg=(TextView) itemView.findViewById(R.id.goodsmsg);
            add=(Button)itemView.findViewById(R.id.btn_add);

        }


    }

    public GoodsAdapter(List<Object> l) {
        goodsList = l;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onegood, parent, false);
        final GoodsViewHolder holder = new GoodsViewHolder(view);


        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        OneGoods c= (OneGoods) goodsList.get(position);
        ((GoodsViewHolder) holder).goodsName.setText(c.getGoodsName());
        ((GoodsViewHolder) holder).goodsImg.setImageResource(c.getGoodsImg());
        ((GoodsViewHolder) holder).goodsmsg.setText("销量"+c.getGoodsmsg());
        ((GoodsViewHolder) holder).price.setText("￥"+c.getPrice() );
        ((GoodsViewHolder) holder).price_d.setText("￥"+c.getPrice_d() );

        ((GoodsViewHolder) holder).price.getPaint().setFlags(
                Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
        ((GoodsViewHolder) holder).add.setText("+");

        ((GoodsViewHolder) holder).add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//加入购物车 1个1个加
                final OneGoods g = (OneGoods) goodsList.get(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FormBody.Builder params=new FormBody.Builder();
                            params.add("shop_id", GoodsFragment.shopId +"");
                            params.add("goods_count","2");
                            params.add("class_id",g.getClass_id()+"");
                            params.add("goods_id",g.getGoodsId()+"");
                            params.add("customer_id", MainActivity.customer_id +"");
                            // params.add("goods_count",g.coun+"");
                            OkHttpClient client=new OkHttpClient();//创建http客户端
                            Request request=new Request.Builder()
                                    .url(MainActivity.service+"/addcart")
                                    .post(params.build())
                                    .build();//创造http请求

                            Response responese=client.newCall(request).execute();//执行发送的指令
                            final String responseData=responese.body().string();//获取返回回来的json格式的结果
                            Looper.prepare();
                            Toast.makeText(MainActivity.c, responseData, Toast.LENGTH_SHORT).show();
                            Looper.loop();

                        }catch(Exception e){
                            e.printStackTrace();
                            Looper.prepare();
                            Toast.makeText(MainActivity.c, "连接失败", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }).start();
            }
        });
    }
    @Override
    public int getItemCount() {
        return goodsList.size();
    }

}
