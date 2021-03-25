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
import com.example.eleme.data.OneChange;

import java.util.List;

public class ChangeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public List list;

    class ChangeViewHolder extends RecyclerView.ViewHolder {
        ImageView goodsimg;
            TextView goodsname;
            TextView goodsmsg;
            TextView goodsprice;//单价x数量
            TextView goodsprice_d;
            public ChangeViewHolder(@NonNull View itemView) {
                super(itemView);
                goodsimg=(ImageView)  itemView.findViewById(R.id.change_img);
                goodsname = (TextView) itemView.findViewById(R.id.change_name);
               goodsmsg = (TextView) itemView.findViewById(R.id.change_msg);
               goodsprice= (TextView) itemView.findViewById(R.id.change_price);
               goodsprice_d = (TextView) itemView.findViewById(R.id.change_price_d);
            }


        }

        public ChangeAdapter(List<Object> l) {
            list = l;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onechange, parent, false);
                final ChangeViewHolder holder = new ChangeViewHolder(view);

                return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            OneChange c= (OneChange) list.get(position);
            ((ChangeViewHolder) holder).goodsname.setText(c.goodsName);
            ((ChangeViewHolder) holder).goodsmsg.setText(c.goodsmsg);
            ((ChangeViewHolder) holder).goodsimg.setImageResource(c.getGoodsImg());
            ((ChangeViewHolder) holder).goodsprice.setText("￥"+c.getPrice());
            ((ChangeViewHolder) holder).goodsprice.getPaint().setFlags(
                    Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
            ((ChangeViewHolder) holder).goodsprice_d.setText("￥"+c.getPrice_d());

        }
        @Override
        public int getItemCount() {
            return list.size();
        }

}
