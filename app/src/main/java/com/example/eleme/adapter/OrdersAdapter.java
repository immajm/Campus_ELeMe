package com.example.eleme.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.CommentActivity;
import com.example.eleme.R;
import com.example.eleme.ShowMsgActivity;

import java.util.List;

import com.example.eleme.data.OneOrder;

public class OrdersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List ordersList;
    View view;
    class OrdersViewHolder extends RecyclerView.ViewHolder {
        ImageView shopImg;
        TextView shopName;
        TextView goods;
        TextView price;
        TextView condition;
        LinearLayout oneorder;
        Button comment;
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            shopImg=(ImageView)itemView.findViewById(R.id.order_shopimg);
            shopName=(TextView) itemView.findViewById(R.id.order_shopname);
            condition=(TextView) itemView.findViewById(R.id.order_condition);
            goods=(TextView) itemView.findViewById(R.id.order_goods);
            price=(TextView) itemView.findViewById(R.id.order_price);
            comment=(Button)itemView.findViewById(R.id.order_comment);
            oneorder=(LinearLayout)itemView.findViewById(R.id.one_order);
        }


    }

    public OrdersAdapter(List<Object> l) {
        ordersList = l;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneorder, parent, false);
        final OrdersViewHolder holder = new OrdersViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        OneOrder c= (OneOrder) ordersList.get(position);
        ((OrdersViewHolder) holder).shopName.setText(c.getShopName());
        ((OrdersViewHolder) holder).shopImg.setImageResource(c.getShopImg());
        ((OrdersViewHolder) holder).condition.setText(c.getCondition());
        if(c.getGoodsCount()>3){
            ((OrdersViewHolder) holder).goods.setText(c.getGoods().get(0)+"等"+c.getGoodsCount()+"件商品");
        }else{
            String s="";
            for (String o:c.getGoods()){
                s+=o+"\n";
            }
            ((OrdersViewHolder) holder).goods.setText(s);
        }
        ((OrdersViewHolder) holder).price.setText("￥"+c.getFinalPrice());
        if (!c.getCondition().equals("已送达")){
            ((OrdersViewHolder) holder).comment.setVisibility(View.GONE);
        }
        else {
            ((OrdersViewHolder) holder).comment.setVisibility(View.VISIBLE);
            if(c.getIfComment().equals("已评价")){//默认是 待评价
                ((OrdersViewHolder) holder).comment.setBackgroundResource(R.drawable.button_comment);
                ((OrdersViewHolder) holder).comment.setText("已评价");
                ((OrdersViewHolder) holder).comment.setEnabled(false);
            }
            else{
                ((OrdersViewHolder) holder).comment.setBackgroundResource(R.drawable.button_comment);
                ((OrdersViewHolder) holder).comment.setText("待评价");
                ((OrdersViewHolder) holder).comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), CommentActivity.class);
                        intent.putExtra("orderId",((OneOrder) ordersList.get(position)).getOrderId());
                        v.getContext().startActivity(intent);
                    }
                });
            }
        }
        ((OrdersViewHolder) holder).oneorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShowMsgActivity.class);
                intent.putExtra("order_id",((OneOrder) ordersList.get(position)).getOrderId());
                //intent.putExtra("shop_name",((OneOrder) ordersList.get(position)).getShopName());
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return ordersList.size();
    }
}
