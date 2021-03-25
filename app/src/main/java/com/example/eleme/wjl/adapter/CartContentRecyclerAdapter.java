package com.example.eleme.wjl.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.wjl.GoodsListActivity;
import com.example.eleme.R;
import com.example.eleme.wjl.bean.BeanCart;

/**
 * @version : 1.0
 * @author: momoshenchi
 * @date: 2021/1/15 - 12:34
 */
public class CartContentRecyclerAdapter extends RecyclerView.Adapter<CartContentRecyclerAdapter.VH>
{
    private BeanCart beanCart;
    private Context mContext;

    public CartContentRecyclerAdapter(BeanCart beanCart)
    {
        this.beanCart = beanCart;
    }

    static class VH extends RecyclerView.ViewHolder
    {

        public final TextView name;
        public final TextView price;
        public final TextView num;

        public final ImageView img;
        public final ImageButton add;
        public final ImageButton sub;

        public VH(View v)
        {
            super(v);
            name = v.findViewById(R.id.goods_name);
            price = v.findViewById(R.id.total_price);
            img = v.findViewById(R.id.goods_img);
            num = v.findViewById(R.id.goods_number);
            add = v.findViewById(R.id.goods_add);
            sub = v.findViewById(R.id.goods_sub);

        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        if (mContext == null)
        {
            mContext = parent.getContext();
        }
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_cart_content, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position)
    {
        holder.name.setText(beanCart.getGoods().get(position).getBeanGoods().getName());
        holder.num.setVisibility(View.VISIBLE);
        holder.num.setText(String.valueOf(beanCart.getGoods().get(position).getNum()));
        holder.price.setText(String.valueOf(beanCart.getGoods().get(position).getPrice()));
        holder.img.setImageResource(beanCart.getGoods().get(position).getBeanGoods().getImg());
        holder.add.setImageResource(R.drawable.tianjia);
        holder.sub.setVisibility(View.VISIBLE);
        holder.sub.setImageResource(R.drawable.jianqu);
        holder.add.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v)
            {
                System.out.println(v);
                GoodsListActivity.beanCarts.addGoods(beanCart.getGoods().get(position).getBeanGoods());
                ImageButton sub = holder.sub;
                TextView goodsnumber = holder.num;

                sub.setVisibility(View.VISIBLE);
                sub.setImageResource(R.drawable.jianqu);
                goodsnumber.setVisibility(View.VISIBLE);
                goodsnumber.setText(String.valueOf(GoodsListActivity.beanCarts.getGoodsNum(beanCart.getGoods().get(position).getBeanGoods())));

            }

        });
        holder.sub.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v)
            {
                System.out.println(v);

                ImageButton sub = holder.sub;
                TextView goodsnumber = holder.num;

                int num=beanCart.getGoodsNum(beanCart.getGoods().get(position).getBeanGoods());
                GoodsListActivity.beanCarts.subGoods(beanCart.getGoods().get(position).getBeanGoods());
                if (num==1)
                {
                    goodsnumber.setVisibility(View.INVISIBLE);
                    sub.setVisibility(View.INVISIBLE);
                }
                else
                {
                    goodsnumber.setText(String.valueOf(GoodsListActivity.beanCarts.getGoodsNum(beanCart.getGoods().get(position).getBeanGoods())));
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return beanCart.getGoods().size();
    }
}
