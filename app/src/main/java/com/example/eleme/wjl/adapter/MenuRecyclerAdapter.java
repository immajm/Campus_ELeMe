package com.example.eleme.wjl.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.wjl.DataChangeListener;
import com.example.eleme.wjl.bean.BeanMenu;
import com.example.eleme.R;
import com.example.eleme.wjl.components.MyTextView;

public abstract class MenuRecyclerAdapter extends  RecyclerView.Adapter<MenuRecyclerAdapter.VH> {
    private BeanMenu[] list;
    private Context mContext;
    private  int[]number;
    public static DataChangeListener dataChangeListener;

    private int mSelectedPosition;
    public void setSelectedPosition(int position) {

        mSelectedPosition = position;
        notifyDataSetChanged();
    }

    private static class MyHandler2 extends Handler
    {
        private  VH holder;
        public MyHandler2(final VH holder)
        {

        }
        @Override
        public void handleMessage(Message msg)
        {
            Log.d("handler",msg.toString());

        }
    }
    public MenuRecyclerAdapter(BeanMenu[] list) {
        this.list = list;
    }
     class VH extends RecyclerView.ViewHolder {

        public final MyTextView menu;

        public VH(View v) {
            super(v);
            menu = v.findViewById(R.id.menu);

        }

    }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View v = LayoutInflater.from(mContext).inflate(R.layout.recycler_menu, parent, false);
        final VH vh=new VH(v);
        v.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int position=vh.getAdapterPosition();
                move(position);
                setSelectedPosition(position);


            }
        });
        return  vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final VH holder, final int position) {
        MyHandler2 myHandler2=new MyHandler2( holder);
        holder.menu.setText(list[position].getMenuname());
        if(position!=mSelectedPosition)
        {
            holder.menu.setBackgroundColor(Color.rgb(238,238,238));
            holder.menu.setTextColor(Color.GRAY);
        }
        else
        {
            holder.menu.setBackgroundColor(Color.WHITE);
            holder.menu.setTextColor(Color.BLACK);
        }
        number=new int[list.length];
        dataChangeListener=new DataChangeListener()
        {
            @Override
            public void change(int data)
            {
                MyTextView menu=holder.menu;
                if(data==1)
                {
                   number[0]++;
                }
                else
                {
                  number[0]--;
                }
                menu.setNum(number[0]);
            }
        };
    }
    public abstract  void move(int position);



    @Override
    public int getItemCount() {
        return list.length;
    }
}
