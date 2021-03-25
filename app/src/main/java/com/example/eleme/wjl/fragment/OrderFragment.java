package com.example.eleme.wjl.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.R;
import com.example.eleme.wjl.CheckListener;
import com.example.eleme.wjl.GoodsDetailActivity;
import com.example.eleme.wjl.GoodsInput;
import com.example.eleme.wjl.ItemHeaderDecoration;
import com.example.eleme.wjl.adapter.GoodsRecyclerAdapter;
import com.example.eleme.wjl.adapter.MenuRecyclerAdapter;

import java.util.Arrays;


public class OrderFragment extends Fragment implements CheckListener
{

    private RecyclerView menu;
    private RecyclerView goods;
    private Context mcontext;
//    private BeanMenu[] name;
//    private BeanGoods[] goodslist;
    LinearLayoutManager layoutManager;
    LinearLayoutManager layoutManager2;
    private ItemHeaderDecoration itemDecoration;

    private  CheckListener checkListener;
    private GoodsRecyclerAdapter goodsRecyclerAdapter;

    private boolean move = false;
    private int mIndex = 0;

    public OrderFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_order, container, false);

        menu = view.findViewById(R.id.menu_list);
        goods = view.findViewById(R.id.goods_list);
        layoutManager = new LinearLayoutManager(mcontext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager2 = new LinearLayoutManager(mcontext);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        view.post(new Runnable()
        {
            @Override
            public void run()
            {
                Log.d("order", Arrays.toString(GoodsInput.listclass));
                Log.d("order",GoodsInput.listgoods.toString());
                GoodsRecyclerAdapter goodsRecyclerAdapter = new GoodsRecyclerAdapter(GoodsInput.listgoods)
                {
                    @Override
                    public void start(int position)
                    {
                        Intent i=new Intent(getContext(), GoodsDetailActivity.class);
                        i.putExtra("index",position);
                        startActivity(i);
                    }
                };
                MenuRecyclerAdapter menuRecyclerAdapter = new MenuRecyclerAdapter(GoodsInput.listclass)
                {
                    @Override
                    public void move(int position)
                    {
                        check(position,true);

                    }
                };

                menu.setAdapter(menuRecyclerAdapter);
                menu.setLayoutManager(layoutManager);
                goods.addOnScrollListener(new RecyclerViewListener());
                goods.setAdapter(goodsRecyclerAdapter);
                goods.setLayoutManager(layoutManager2);

            }
        });
        itemDecoration=new ItemHeaderDecoration(getContext(),GoodsInput.listgoods);
        itemDecoration.setCheckListener(this);
        goods.addItemDecoration(itemDecoration);
        return view;

    }
    private  void setcheck()
    {

    }
//滑动到右边第n个位置
    private void setData(int n) {
        mIndex = n;
        goods.stopScroll();
        smoothMoveToPosition(n);
    }

    private void smoothMoveToPosition(int n)
    {
        int firstItem = layoutManager2.findFirstVisibleItemPosition();
        int lastItem = layoutManager2.findLastVisibleItemPosition();
        if (n <= firstItem)
        {
            goods.scrollToPosition(n);
        }
        else if (n <= lastItem)
        {
            Log.d("pos---->", String.valueOf(n) + "VS" + firstItem);
            int top = goods.getChildAt(n - firstItem).getTop();
            Log.d("top---->", String.valueOf(top));
            goods.scrollBy(0, top);
        }
        else
        {
            goods.scrollToPosition(n);
            move = true;
        }
    }

    @Override
    public void check(int position, boolean isScroll) {
        if(isScroll)
        {
            int count=0;
            for (int i = 0; i < position; i++)
            {
                count +=GoodsInput.listclass[i].getCataSize();
            }
            count+=position;
            setData(count);
            ItemHeaderDecoration.setCurrentTag(String.valueOf(mIndex));
        }
        else{

            if(move)
            {
                move=false;
            }
            ItemHeaderDecoration.setCurrentTag(String.valueOf(position));
        }
//        moveToCenter(position);
    }
    private  void moveToCenter(int position)
    {
        View childAt =goods.getChildAt(position-layoutManager2.findFirstVisibleItemPosition());
                if(childAt!=null)
                {
                    int y=(childAt.getTop()-goods.getHeight()/2);
                   goods.smoothScrollBy(0,y);
                }
    }
    public void setListener(CheckListener listener) {
        this.checkListener = listener;
    }
    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mIndex - layoutManager2.findFirstVisibleItemPosition();
                if (0 <= n && n < goods.getChildCount()) {
                    int top = goods.getChildAt(n).getTop();
                    goods.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move) {
                move = false;
                int n = mIndex - layoutManager2.findFirstVisibleItemPosition();
                if (0 <= n && n < goods.getChildCount()) {
                    int top = goods.getChildAt(n).getTop();
                    goods.scrollBy(0, top);
                }
            }
        }
    }
}
