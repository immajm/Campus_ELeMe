package com.example.eleme.HomeFragment1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.eleme.R;

import java.util.List;


//如果每个按钮只要会响应就好了那很简单，item位置也很好取，点餐的难点在于要绑定每个按钮所以所在item的位置
public class RVAdapter_NestedList extends RecyclerView.Adapter<RVAdapter_NestedList.ViewHolder> {
    private List<BeanStore> datas;
    private OnItemClickListener mOnItemClickListener;

    RVAdapter_NestedList(List<BeanStore> proList) {
        this.datas=proList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView Home_ShopName;
        private ImageView Home_ShopIcon;
        private TextView Home_Level;
        private TextView Home_agvCost;
        private TextView Home_Introduce;

        public ViewHolder(View view) {
            super(view);
            this.Home_ShopIcon =view.findViewById(R.id.Home_ShopIcon);
            this.Home_ShopName =view.findViewById(R.id.Home_ShopName);
            this.Home_Level =view.findViewById(R.id.Home_Level);
            this.Home_agvCost =view.findViewById(R.id.Home_agvCost);
            this.Home_Introduce =view.findViewById(R.id.Home_Introduce);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final BeanStore data= datas.get(position);
        holder.Home_ShopIcon.setImageResource(R.drawable.humburger);
        holder.Home_ShopName.setText(data.getHome_ShopName());
        holder.Home_Level.setText(data.getHome_Level());
        holder.Home_agvCost.setText(data.getHome_agvCost());
        holder.Home_Introduce.setText(data.getHome_Introduce());
    }

    //传入的是item的xml
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fg_home_store_item, parent, false));
    }

    //item里面有多个控件可以点击
    public enum ViewName {
        ITEM,
        PRACTISE
    }

    @Override
    public int getItemCount() {
        return datas==null? 0 : datas.size();
    }

    //自己设置了接口
    public interface OnItemClickListener {
        void onItemClick(View v, ViewName viewName, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}