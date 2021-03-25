package com.example.eleme.wjl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.R;
import com.example.eleme.wjl.GridAdapter;
import com.example.eleme.wjl.GridViewUtils;
import com.example.eleme.wjl.bean.BeanComment;
import com.example.eleme.wjl.components.MyGridView;


/**
 * @version : 1.0
 * @author: momoshenchi
 * @date: 2021/1/15 - 23:35
 */
public class GoodsDetailAdapter extends  RecyclerView.Adapter<GoodsDetailAdapter.VH>
{
    private BeanComment[] list;
    private Context mContext;

    public GoodsDetailAdapter(BeanComment[] list) {
    this.list = list;
}
    class VH extends RecyclerView.ViewHolder {

        public final TextView name;
        public final TextView date;
        public final TextView content;
        public final ImageView avatar;
        public  final MyGridView img;
        public VH(View v) {
            super(v);
        name=v.findViewById(R.id.goods_comment_name);
        date=v.findViewById(R.id.goods_comment_date);
        content=v.findViewById(R.id.goods_comment_content);
        avatar=v.findViewById(R.id.goods_comment_avatar);
        img = v.findViewById(R.id.goods_comment_img);
        }

    }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_goods_comment, parent, false);
        final VH vh=new VH(v);

        return  vh;
    }

    @Override
    public void onBindViewHolder(@NonNull  VH holder,  int position) {
        holder.name.setText(list[position].getName());
        holder.date.setText(list[position].getDate());
        holder.content.setText(list[position].getContent());
        holder.avatar.setImageResource(list[position].getAvator());
        GridAdapter gridAdapter=new GridAdapter(list[position].getImg(),mContext);
        holder.img.setAdapter(gridAdapter);
        GridViewUtils.updateGridViewLayoutParams(holder.img, 3);
    }


    @Override
    public int getItemCount() {
        return list.length;
    }
}
