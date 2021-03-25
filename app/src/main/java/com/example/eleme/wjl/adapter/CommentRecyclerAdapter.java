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

public class CommentRecyclerAdapter extends RecyclerView.Adapter<CommentRecyclerAdapter.VH>{
    private BeanComment[] list;
    private Context mContext;

    public CommentRecyclerAdapter(BeanComment[] list) {
        this.list = list;
    }

    static class VH extends RecyclerView.ViewHolder {

        public final TextView name;
        public final TextView date;
        public final TextView content;
        public final ImageView avator;
        public  final MyGridView img;
        public VH(View v) {
            super(v);
            name = v.findViewById(R.id.comment_name);
            date = v.findViewById(R.id.comment_date);
            content = v.findViewById(R.id.comment_content);
            img = v.findViewById(R.id.comment_img);
            avator = v.findViewById(R.id.comment_avatar);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_comment, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.name.setText(list[position].getName());
        holder.date.setText(list[position].getDate());
        holder.content.setText(list[position].getContent());
        holder.avator.setImageResource(list[position].getAvator());
        GridAdapter gridAdapter=new GridAdapter(list[position].getImg(),mContext);
        holder.img.setAdapter(gridAdapter);
        GridViewUtils.updateGridViewLayoutParams(holder.img, 3);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }
}
