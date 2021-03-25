package com.example.eleme.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.R;
import com.example.eleme.data.OneCart;

import java.util.List;

public class OneGoodsInCartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public List list;

    class OneGoodsInCartViewHolder extends RecyclerView.ViewHolder {
        ImageView goodsimg;
            TextView goodsname;
            TextView goodscount;
            TextView goodsprice;//单价x数量
            TextView goodsprice_d;
            public OneGoodsInCartViewHolder(@NonNull View itemView) {
                super(itemView);
                goodsimg=(ImageView)  itemView.findViewById(R.id.cart_goodsimg);
                goodsname = (TextView) itemView.findViewById(R.id.cart_goodsname);
               goodscount = (TextView) itemView.findViewById(R.id.cart_goodscount);
               goodsprice= (TextView) itemView.findViewById(R.id.cart_price);
               goodsprice_d = (TextView) itemView.findViewById(R.id.cart_price_d);
            }


        }

        public OneGoodsInCartAdapter(List<Object> l) {
            list = l;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onegoods_in_cart, parent, false);
                final OneGoodsInCartViewHolder holder = new OneGoodsInCartViewHolder(view);

                return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            OneCart c= (OneCart) list.get(position);
            ((OneGoodsInCartViewHolder) holder).goodsname.setText(c.getGoodName());
            ((OneGoodsInCartViewHolder) holder).goodscount.setText("x"+c.getCount());
            ((OneGoodsInCartViewHolder) holder).goodsimg.setImageResource(R.drawable.noodles);
            ((OneGoodsInCartViewHolder) holder).goodsprice.setText("￥"+c.getPrice());
            ((OneGoodsInCartViewHolder) holder).goodsprice.getPaint().setFlags(
                    Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
            ((OneGoodsInCartViewHolder) holder).goodsprice_d.setText("￥"+c.getPrice_d());

        }
        @Override
        public int getItemCount() {
            return list.size();
        }

}
