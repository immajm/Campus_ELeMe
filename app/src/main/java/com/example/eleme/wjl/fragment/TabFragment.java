package com.example.eleme.wjl.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.eleme.wjl.GoodsInput;
import com.example.eleme.wjl.adapter.BusinessFragmentAdapter;
import com.example.eleme.wjl.bean.BeanComment;
import com.example.eleme.wjl.bean.BeanGoods;
import com.example.eleme.wjl.bean.BeanMenu;
import com.google.android.material.tabs.TabLayout;

import com.example.eleme.R;

public class TabFragment extends Fragment
{
    private ViewPager pager;
    private BusinessFragmentAdapter businessFragmentAdapter;
    private String[] title = {"点单", "评价", "商家"};
    private TabLayout tabLayout;

    private CommentFragment commentFragment;
    private OrderFragment orderFragment;
    private RetailerFragment retailerFragment;

    public static BeanMenu[] beanMenus;
    public static BeanComment[] beanComments;
    public static BeanGoods[] beanGoods;
    private MyHandler myHandler;
    private MyHandler2 myHandler2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_tablayout, container, false);
        pager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tab);
        return view;
    }


    private static class MyHandler extends Handler
    {
        public MyHandler()
        {

        }

        @Override
        public void handleMessage(Message msg)
        {
            Log.d("handler",msg.toString());
            beanMenus = (BeanMenu[]) msg.obj;
        }
    }

    private static class MyHandler2 extends Handler
    {

        public MyHandler2()
        {

        }
        @Override
        public void handleMessage(Message msg)
        {
            Log.d("handler",msg.toString());
            beanGoods = (BeanGoods []) msg.obj;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        init();
//        myHandler = new MyHandler();
//        myHandler2 = new MyHandler2();
        GoodsInput goodsInput = new GoodsInput();
        Thread t = new Thread(goodsInput);
        t.start();


        commentFragment = new CommentFragment(beanComments);
        orderFragment = new OrderFragment();
        retailerFragment = new RetailerFragment();

        businessFragmentAdapter = new BusinessFragmentAdapter(getActivity().getSupportFragmentManager(), title, commentFragment, orderFragment, retailerFragment);
        pager.setAdapter(businessFragmentAdapter);
        tabLayout.setupWithViewPager(pager);//与ViewPage建立关系
        try
        {
            t.join();
        }

        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    private void init()
    {
//        beanMenus = new BeanMenu[3];
        beanComments = new BeanComment[10];
//        beanGoods = new BeanGoods[10];
//
//        beanMenus[0] = new BeanMenu();
//        beanMenus[1] = new BeanMenu();
//        beanMenus[2] = new BeanMenu();
//        beanMenus[0].setMenuname("刚刚看过");
//        List<BeanGoods> list = new ArrayList<>();
//        beanMenus[0].isSelected=true;
//        beanMenus[1].setMenuname("必选品");
//        beanMenus[2].setMenuname("超享折扣");
//        for (int i = 0; i < 5; i++) {
//            BeanGoods b=new BeanGoods();
//            b.setDes("羊肉串2串+牛肉串2串+骨肉相连2串");
//            b.setName("超级福满多套餐");
//            b.setNum("月售102");
//            b.setPrice("68");
//            b.setVipprice("品牌会员价￥31.9");
//            b.setImg(R.drawable.glgs);
//            b.setTitlename("刚刚看过");
//            b.setTag(0+"");
//            beanGoods[i]=b;
//            list.add(b);
//        }
//        beanMenus[0].setCata(list);
//        list=new ArrayList<>();
//        for (int i = 5; i < 9; i++) {
//            BeanGoods b=new BeanGoods();
//            b.setDes("羊肉串2串");
//            b.setName("羊肉串");
//            b.setNum("月售1020");
//            b.setPrice("15");
//            b.setVipprice("品牌会员价￥11.9");
//            b.setImg(R.drawable.eye_img);
//            b.setTitlename("必选品");
//            b.setTag(""+1);
//            beanGoods[i]=b;
//            list.add(b);
//        }
//        beanMenus[1].setCata(list);
//        BeanGoods b=new BeanGoods();
//        b.setDes("羊肉串2串+牛肉串2串+骨肉相连2串");
//        b.setName("超级福满多套餐");
//        b.setNum("月售102");
//        b.setPrice("68");
//        b.setVipprice("品牌会员价￥31.9");
//        b.setImg(R.drawable.glgs);
//        b.setTitlename("超享折扣");
//        b.setTag(""+2);
//        beanGoods[9]=b;
//        list=new ArrayList<>();
//        list.add(b);
//        beanMenus[2].setCata(list);
        for (int i = 0; i < 10; i++)
        {
            BeanComment bc = new BeanComment();
            bc.setName("momoshenchi");
            bc.setAvator(R.drawable.avator);
            bc.setContent("太好吃了,宝宝已经去医院抢救好几次了");
            bc.setDate("2021-01-09");
            bc.setImg(new int[]{});
            beanComments[i] = bc;
        }
    }
}
