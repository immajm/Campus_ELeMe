package com.example.eleme.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.R;
import com.example.eleme.ShopActivity;
import com.example.eleme.data.OneLine;
import com.example.eleme.data.OneShop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class recycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private int ITEM_CONTENT1 = 1;
    private int ITEM_CONTENT2 = 2;
    public List shopList;
//    private OnItemClickListener onItemClickListener;
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
//        this.onItemClickListener = onItemClickListener;
//    }
//    @Override
//    public void onClick(View v) {
//        //根据RecyclerView获得当前View的位置
//        int position = recycleAdapter.getChildAdapterPosition(v);
//        //程序执行到此，会去执行具体实现的onItemClick()方法
//        if (onItemClickListener!=null){
//            onItemClickListener.onItemClick(rv,v,position,mapList.get(position));
//        }
//    }

    static class ViewHolder2 extends RecyclerView.ViewHolder{
        TextView t1;
        TextView t2;
        TextView t3;
        TextView t4;
        ImageView m1;
        ImageView m2;
        ImageView m3;
        ImageView m4;
        List<ImageView> listImg=new ArrayList<ImageView>();
        List<TextView> listText=new ArrayList<TextView>();
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            t1=(TextView) itemView.findViewById(R.id.t1);
            t2=(TextView) itemView.findViewById(R.id.t2);
            t3=(TextView) itemView.findViewById(R.id.t4);
            t4=(TextView) itemView.findViewById(R.id.t4);
            m1=(ImageView) itemView.findViewById(R.id.m1);
            m2=(ImageView) itemView.findViewById(R.id.m2);
            m3=(ImageView) itemView.findViewById(R.id.m4);
            m4=(ImageView) itemView.findViewById(R.id.m4);
            listImg.add(m1);
            listImg.add(m2);
            listImg.add(m3);
            listImg.add(m4);
            listText.add(t1);
            listText.add(t2);
            listText.add(t3);
            listText.add(t4);
        }


    }
    static class ShopViewHolder extends RecyclerView.ViewHolder{
        LinearLayout shop;
        ImageView shopImg;
        TextView shopName;
        TextView shopStar;
        TextView salesVolume;
        TextView full1;
        TextView full2;
        TextView full3;
        List<TextView> list=new ArrayList<TextView>();
        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);
            shop=(LinearLayout)itemView.findViewById(R.id.one_shop);
            shopImg=(ImageView) itemView.findViewById(R.id.shopimg);
            shopName=(TextView) itemView.findViewById(R.id.shopname);
            shopStar=(TextView) itemView.findViewById(R.id.shopstar);
            salesVolume=(TextView) itemView.findViewById(R.id.salesvolume);
            full1=(TextView) itemView.findViewById(R.id.full1);
            full2=(TextView) itemView.findViewById(R.id.full2);
            full3=(TextView) itemView.findViewById(R.id.full3);
            list.add(full1);
            list.add(full2);
            list.add(full3);
        }


    }

    public recycleAdapter(List<Object> l){
        shopList=l;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        if (viewType == ITEM_CONTENT1) {
            View view = null;
            RecyclerView.ViewHolder holder = null;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneline, parent, false);
            holder = new ViewHolder2(view);
            return holder;
        }

        else{
            final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneshop, parent, false);
            //view.setOnClickListener(this);
            final ShopViewHolder holder =new ShopViewHolder(view);

            ((ShopViewHolder) holder).shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    OneShop s = (OneShop) shopList.get(position);
                    Toast.makeText(view.getContext(), s.getShopName(), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(v.getContext(), ShopActivity.class);
                    intent.putExtra("shop_id", s.getShopId());
                    intent.putExtra("shop_name", s.getShopName());
                    //目标activity获取参数String shopname = getIntent().getStringExtra("shopname");
                    v.getContext().startActivity(intent);
                }
            });

            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder2) {
            OneLine line = (OneLine) shopList.get(position);
                Iterator<Integer> it = line.getLine().keySet().iterator();  //map.keySet()得到的是set集合，可以使用迭代器遍历
            int i=0;
                while(it.hasNext()){
                    Integer key = it.next();
                    ((ViewHolder2) holder).listImg.get(i).setImageResource(key);
                    ((ViewHolder2) holder).listText.get(i).setText(line.getLine().get(key));
                    i++;
                }

        }
        else {
            OneShop shop = (OneShop) shopList.get(position);
            ((ShopViewHolder) holder).shopImg.setImageResource(shop.getShopImg());
            ((ShopViewHolder) holder).shopName.setText(shop.getShopName());
            ((ShopViewHolder) holder).shopStar.setText(shop.getShopStart() + "");

            for (int i = 0; i < 3; i++) {
                if (i < shop.getFr().size()) {
                    ((ShopViewHolder) holder).list.get(i).setText(shop.getFr().get(i).full + "减" + shop.getFr().get(i).redution);
                } else {
                    ((ShopViewHolder) holder).list.get(i).setVisibility(View.INVISIBLE);
                }
            }

        }
    }
    @Override
    public int getItemViewType(int position) {
        if (shopList.get(position) instanceof OneLine) {
            return ITEM_CONTENT1;
        } else if (shopList.get(position) instanceof OneShop) {
            return ITEM_CONTENT2;
        }
        return super.getItemViewType(position);
    }
    @Override
    public int getItemCount() {
        return shopList.size();
    }


}
