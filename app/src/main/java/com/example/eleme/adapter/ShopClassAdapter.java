package com.example.eleme.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.R;
import com.example.eleme.data.OneClass;
import com.example.eleme.fragments.GoodsFragment;

import java.util.List;

public class ShopClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public List classList;
        View view;
    RecyclerView recycleViewright;
    GoodsAdapter adapterright;
    class ClassViewHolder extends RecyclerView.ViewHolder {
            TextView classname;

            public ClassViewHolder(@NonNull View itemView) {
                super(itemView);
                classname = (TextView) itemView.findViewById(R.id.classname);
            }


        }

        public ShopClassAdapter(List<Object> l) {
            classList = l;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneclass, parent, false);
            ClassViewHolder holder = new ClassViewHolder(view);

               // GoodsFragment.recycleViewright = view.findViewById(R.id.goods_recycleView);
                return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                OneClass c= (OneClass) classList.get(position);
                ((ClassViewHolder) holder).classname.setText(c.getClassName());
                ((ClassViewHolder) holder).classname.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    final OneClass c = (OneClass) classList.get(position);
//                    Toast.makeText(view.getContext(), "clicked" + "class_id:"+c.class_id, Toast.LENGTH_SHORT).show();
                    Log.v("click class_id:",""+c.class_id);
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            GoodsFragment.initGoods(c.getClass_id());
                            //Toast.makeText(view.getContext(), "clicked" + "class_id:"+c.class_id, Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });

        }
        @Override
        public int getItemCount() {
            return classList.size();
        }
//    public void buildright(List<Object> listgoods){
//        recycleViewright = (RecyclerView) view.findViewById(R.id.goods_recycleView);
//        LinearLayoutManager layoutManagerright= new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL, false);
//        recycleViewright.setLayoutManager(layoutManagerright);
//        adapterright= new GoodsAdapter(listgoods);
//        recycleViewright.setAdapter(adapterright);
//    }
}
