package com.example.eleme.HomeFragment1;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.eleme.HomeFragment1.Coordinary.Coordinary1;
import com.example.eleme.MainActivity;
import com.example.eleme.R;
import com.example.eleme.wjl.GoodsListActivity;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * RecyclerView  嵌套  各种样式的RecyclerView
 */
public class HomeFragment1 extends Fragment {

    View view;
    private RecyclerView recylcerview;//外层recyclerview
    private Button btn_show;
    List<String> tabData;
    public static DataInfor data;//假数据
    private int HORIZONTAL_VIEW_X = 0;//水平RecyclerView滑动的距离

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fg_home1, null);
        Thread initStoreData=new Thread(new T1());
        initStoreData.start();//为了防止下载数据线程 在跟新列表之后

        initData();
        initRecyclerView();
//        initTabData(1);
//        initTab();

        try {
            initStoreData.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        btn_show = (Button) view.findViewById(R.id.home_btn_show);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopWindow(v);
            }
        });
        return view;
    }

    private void initPopWindow(View v) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_popup, null, false);
        Button btn_show_photo = (Button) view.findViewById(R.id.btn_show_photo);
        Button btn_show_map = (Button) view.findViewById(R.id.btn_show_map);
        //1.构造一个PopupWindow，参数依次是加载的View，宽高
        final PopupWindow popWindow = new PopupWindow(view,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        popWindow.setAnimationStyle(R.anim.anim_pop);  //设置加载动画

        //这些为了点击非PopupWindow区域，PopupWindow会消失的，如果没有下面的
        //代码的话，你会发现，当你把PopupWindow显示出来了，无论你按多少次后退键
        //PopupWindow并不会关闭，而且退不出程序，加上下述代码可以解决这个问题
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x77777777));    //要为popWindow设置一个背景才有效


        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAsDropDown(v, 50, 0);

        //设置popupWindow里的按钮的事件
        btn_show_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "拍照", Toast.LENGTH_SHORT).show();
            }
        });
        btn_show_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "地图", Toast.LENGTH_SHORT).show();
                popWindow.dismiss();
            }
        });
    }


    /**
     * 制造一些假数据
     */
    private void initData() {
        data = new DataInfor();

        //顶部功能
        ArrayList<BeanQuickIcon> headData_list = new ArrayList<>();
        headData_list.add(new BeanQuickIcon(R.drawable.qi_scan,"扫一扫"));
        headData_list.add(new BeanQuickIcon(R.drawable.qi_paycode,"付款码"));
        headData_list.add(new BeanQuickIcon(R.drawable.qi_coupon,"红包卡券"));
        headData_list.add(new BeanQuickIcon(R.drawable.qi_ride,"骑车"));
        data.headIconData =headData_list;

        //快捷图标
        ArrayList<BeanQuickIcon> quickiconData_list = new ArrayList<>();
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_takeout,"外卖"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_food,"美食"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_hotel,"酒店/民宿"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_play,"休闲/娱乐"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_movie,"电影/演出"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_texi,"打车"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_redbag,"红包签到"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_shop,"跑腿代购"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_credit,"借钱信用卡"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_train,"火车票/机票"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_goodthing,"美团优选"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_fruit,"免费领水果"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_medicine,"买药"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_vegetable,"送菜上门"));
        quickiconData_list.add(new BeanQuickIcon(R.drawable.qi_hair,"美容美发"));
        data.quickIconData =quickiconData_list;

        //广告内容
        ArrayList<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.ad1);
        imageList.add(R.drawable.ad2);
        imageList.add(R.drawable.ad3);
        imageList.add(R.drawable.ad4);
        imageList.add(R.drawable.ad5);
        data.AdData =data.verticalData= imageList;

    }

    static class T1 implements Runnable {
        final ArrayList<BeanStore> storeList = new ArrayList<>();
        @Override
        public void run() {
            try {
                FormBody.Builder params = new FormBody.Builder();
                OkHttpClient client = new OkHttpClient();//创建http客户端
                Request request = new Request.Builder()
                        .url(MainActivity.service +"/queryallshop")
                        .post(params.build())
                        .build();//创造http请求
                Response responese = client.newCall(request).execute();//执行发送的指令
                final String string = responese.body().string();//获取返回回来的json格式的结果
                JSONArray array=new JSONArray(string);//转为json格式
                for(int i=0;i<array.length();i++){
                    JSONObject sonObject = array.getJSONObject(i);


                    BeanStore contentData=new BeanStore(
                            R.drawable.humburger,
                            String.valueOf(sonObject.getInt("shop_id")),
                            sonObject.getString("shop_name"),
                            String.valueOf(sonObject.getDouble("shop_star")),
                            "13",
                            "nice好吃极666");
                    storeList.add(contentData);
                }
                HomeFragment1.data.storeData=storeList;
            } catch (JSONException|IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 初始化外层RecyclerView
     */
    private void initRecyclerView() {
        recylcerview = (RecyclerView) view.findViewById(R.id.recylcerview);
        recylcerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recylcerview.setBackgroundResource(R.color.backgroundGray);
        recylcerview.setAdapter(new RecyclerViewAdapter());
    }

    /**
     * 外层RecyclerView的Adapter
     */
    private class RecyclerViewAdapter extends RecyclerView.Adapter<BaseHolder> {
        //条目样式
        private final int GRID_VIEW_HEAD = 1001;
        private final int GRID_VIEW_QUICKICON = 1004;//快捷图标
        private final int HORIZONTAL_VIEW_AD = 1002;
        private final int GRID_VIEW_STORE = 1003;


        @Override
        public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case GRID_VIEW_HEAD:
                    return new GridViewHolder_Head(R.layout.fg_home1_rv_item, parent, viewType);
                case GRID_VIEW_QUICKICON:
                    return new GridViewHolder_QuickIcon(R.layout.fg_home1_rv_item, parent, viewType);
                case HORIZONTAL_VIEW_AD:
                    return new HorizontalViewHolder_Ad(R.layout.fg_home1_rv_item, parent, viewType);
                case GRID_VIEW_STORE:
                    return new GridViewHolder_Store(R.layout.fg_home1_rv_storeitem, parent, viewType);
            }
            return null;
        }

        @Override
        public void onBindViewHolder(BaseHolder holder, int position) {

            if (holder instanceof GridViewHolder_Head) {
                holder.refreshData(data.headIconData, position);
            } else if (holder instanceof GridViewHolder_QuickIcon) {
                holder.refreshData(data.quickIconData, position);
            } else if (holder instanceof HorizontalViewHolder_Ad) {
                holder.refreshData(data.AdData, position);
            } else if (holder instanceof GridViewHolder_Store) {
                holder.refreshData(data.storeData, position);
            }

        }

        @Override// 当Item出现时调用此方法
        public void onViewAttachedToWindow(BaseHolder holder) {
            Log.i("onAttachedToWindow", "" + holder.getClass().toString());
        }
        @Override //当Item被回收时调用此方法
        public void onViewDetachedFromWindow(BaseHolder holder) {
            Log.i("onDetachedFromWindow", "" + holder.getClass().toString());
            if (holder instanceof HorizontalViewHolder_Ad) {
                ((HorizontalViewHolder_Ad) holder).saveStateWhenDestory();
            }
        }


        @Override
        public int getItemCount() {
            return 4;
        }
        @Override
        public int getItemViewType(int position) {
            if (position == 0) return GRID_VIEW_HEAD;
            if (position == 1) return GRID_VIEW_QUICKICON;
            if (position == 2) return HORIZONTAL_VIEW_AD;
            if (position == 3) return GRID_VIEW_STORE;
            return 0;
        }
    }
    //----------------------Holder----------------------------

    /**
     * 嵌套的水平RecyclerView
     * 当条目被回收时，下次加载会重新回到之前的x轴
     */
    private class HorizontalViewHolder_Ad extends BaseHolder<List<Integer>> {
        private RecyclerView hor_recyclerview;
        private List<Integer> data;
        private int scrollX;//纪录X移动的距离
        private boolean isLoadLastState = false;//是否加载了之前的状态

        public HorizontalViewHolder_Ad(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            hor_recyclerview = (RecyclerView) itemView.findViewById(R.id.item_recyclerview);
            //为了保存移动距离，所以添加滑动监听
            hor_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    //每次条目重新加载时，都会滑动到上次的距离
                    if (!isLoadLastState) {
                        isLoadLastState = true;
                        hor_recyclerview.scrollBy(HORIZONTAL_VIEW_X, 0);
                    }
                    //dx为每一次移动的距离，所以我们需要做累加操作
                    scrollX += dx;
                }
            });
        }
        @Override
        public void refreshData(List<Integer> data, int position) {
            this.data = data;
            hor_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            hor_recyclerview.setBackgroundResource(R.color.backgroundGray);
            hor_recyclerview.setAdapter(new HorizontalAdapter());
        }
        //在条目回收时调用，保存X轴滑动的距离
        public void saveStateWhenDestory() {
            HORIZONTAL_VIEW_X = scrollX;
            isLoadLastState = false;
            scrollX = 0;
        }
        private class HorizontalAdapter extends RecyclerView.Adapter<BaseHolder> {
            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder(R.layout.fg_home1_image_item, parent, viewType);
            }
            @Override
            public void onBindViewHolder(BaseHolder holder, int position) {
                holder.refreshData(data.get(position), position);
            }
            @Override
            public int getItemCount() {
                return data.size();
            }
        }
    }


    //GridView形状的RecyclerView
    private class GridViewHolder_Head extends BaseHolder<List<BeanQuickIcon>> {
        private RecyclerView item_recyclerview;
        private final int NumOfHead = 4;
        private List<BeanQuickIcon> datas;

        public GridViewHolder_Head(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            item_recyclerview = (RecyclerView) itemView.findViewById(R.id.item_recyclerview);
        }
        @Override
        public void refreshData(List<BeanQuickIcon> datas, int position) {
            super.refreshData(datas, position);
            this.datas = datas;
            item_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), NumOfHead, LinearLayoutManager.VERTICAL, false));
            item_recyclerview.setBackgroundResource(R.color.colorPrimary);
            item_recyclerview.setAdapter(new GridAdapter());
        }
        private class GridAdapter extends RecyclerView.Adapter<BaseHolder> {
            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder1(R.layout.fg_home_head_item, parent, viewType);
            }
            @Override
            public void onBindViewHolder(BaseHolder holder, int position) {
                holder.refreshData(datas.get(position), position);
            }
            @Override
            public int getItemCount() {
                return datas.size();
            }
        }
    }

    private class GridViewHolder_QuickIcon extends BaseHolder<List<BeanQuickIcon>> {

        private RecyclerView item_recyclerview;
        private final int NumOfQuick = 5;
        private List<BeanQuickIcon> datas;

        public GridViewHolder_QuickIcon(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            item_recyclerview = (RecyclerView) itemView.findViewById(R.id.item_recyclerview);
        }
        @Override
        public void refreshData(List<BeanQuickIcon> datas, int position) {
            super.refreshData(datas, position);
            this.datas = datas;
            item_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), NumOfQuick, LinearLayoutManager.VERTICAL, false));
            item_recyclerview.setBackgroundResource(R.color.white);
            item_recyclerview.setAdapter(new GridAdapter());
        }
        private class GridAdapter extends RecyclerView.Adapter<BaseHolder> {
            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder1(R.layout.fg_home_quickicon_item, parent, viewType);
            }
            @Override
            public void onBindViewHolder(BaseHolder holder, int position) {
                holder.refreshData(datas.get(position), position);
            }
            @Override
            public int getItemCount() {
                return datas.size();
            }
        }
    }


    private class GridViewHolder_Store extends BaseHolder<List<BeanStore>> {
        private RecyclerView item_recyclerview;
        private final int NumOfStore = 2;
        private List<BeanStore> datas=new ArrayList<>();

        public GridViewHolder_Store(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            item_recyclerview = (RecyclerView) itemView.findViewById(R.id.item_recyclerview1);
        }
        @Override
        public void refreshData(List<BeanStore> datas, int position) {
            super.refreshData(datas, position);
            this.datas = datas;
            item_recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), NumOfStore, LinearLayoutManager.VERTICAL, false));
            item_recyclerview.setBackgroundResource(R.color.white);
            item_recyclerview.setAdapter(new GridAdapter());
        }

        private class GridAdapter extends RecyclerView.Adapter<BaseHolder> {
            @Override
            public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder_Store(R.layout.fg_home_store_item, parent, viewType);
            }
            @Override
            public void onBindViewHolder(BaseHolder holder, int position) {
                holder.refreshData(datas.get(position), position);
            }
            @Override
            public int getItemCount() {
                return datas.size();
            }
        }
    }

    //head&quickicon基本子条目（1图1文字）
    private class ItemViewHolder1 extends BaseHolder<BeanQuickIcon> {
        private ImageView home_quickicon_icon;
        private TextView home_quickicon_name;
        public ItemViewHolder1(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            this.home_quickicon_icon =itemView.findViewById(R.id.home_quickicon_icon);
            this.home_quickicon_name =itemView.findViewById(R.id.home_quickicon_name);
        }
        @Override
        public void refreshData(BeanQuickIcon data, final int position) {
            home_quickicon_icon.setImageResource(data.getHome_quickicon_icon());
            home_quickicon_name.setText(data.getHome_quickicon_name());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //ad基本子条目（1图）
    private class ItemViewHolder extends BaseHolder<Integer> {
        private ImageView imageview_item;
        public ItemViewHolder(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            imageview_item = (ImageView) itemView.findViewById(R.id.imageview_item);
        }
        @Override
        public void refreshData(Integer data, final int position) {
            imageview_item.setBackgroundResource(data);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //store基本子条目（一图多字）
    private class ItemViewHolder_Store extends BaseHolder<BeanStore> {
        private TextView Home_ShopName;
        private ImageView Home_ShopIcon;
        private TextView Home_Level;
        private TextView Home_agvCost;
        private TextView Home_Introduce;
        public ItemViewHolder_Store(int viewId, ViewGroup parent, int viewType) {
            super(viewId, parent, viewType);
            this.Home_ShopIcon =itemView.findViewById(R.id.Home_ShopIcon);
            this.Home_ShopName =itemView.findViewById(R.id.Home_ShopName);
            this.Home_Level =itemView.findViewById(R.id.Home_Level);
            this.Home_agvCost =itemView.findViewById(R.id.Home_agvCost);
            this.Home_Introduce =itemView.findViewById(R.id.Home_Introduce);
        }
        @Override
        public void refreshData(final BeanStore data, final int position) {
            Home_ShopIcon.setImageResource(R.drawable.humburger);
            Home_ShopName.setText(data.getHome_ShopName());
            Home_Level.setText(data.getHome_Level());
            Home_agvCost.setText(data.getHome_agvCost());
            Home_Introduce.setText(data.getHome_Introduce());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(), data.getHome_ShopName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(v.getContext(), GoodsListActivity.class);
                    intent.putExtra("shop_id", Integer.parseInt(data.getHome_ShopId()));
                    Log.v("shopid:",data.getHome_ShopId());
                    intent.putExtra("shop_name",data.getHome_ShopName());
                    //目标activity获取参数String shopname = getIntent().getStringExtra("shopname");
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
    public void initTabData(int pager) {
        tabData = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            tabData.add("pager" + pager + " 第" + i + "个item");
        }
    }

    public void initTab(){
        //设置TabLayout
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
            tabLayout.addTab(tabLayout.newTab().setText("附近推荐"));
            tabLayout.addTab(tabLayout.newTab().setText("发现好菜"));
            tabLayout.addTab(tabLayout.newTab().setText("百亿补贴"));
            tabLayout.addTab(tabLayout.newTab().setText("吃货联盟"));
            tabLayout.addTab(tabLayout.newTab().setText("30分钟送达"));
            tabLayout.addTab(tabLayout.newTab().setText("超市"));
            tabLayout.addTab(tabLayout.newTab().setText("水果"));
            tabLayout.addTab(tabLayout.newTab().setText("买菜"));
            tabLayout.addTab(tabLayout.newTab().setText("医药"));
            tabLayout.addTab(tabLayout.newTab().setText("零食"));
            tabLayout.addTab(tabLayout.newTab().setText("百货"));
        //TabLayout的切换监听
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initTabData(tab.getPosition() + 1);
                setScrollViewContent();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        setScrollViewContent();
    }

    /**
     * 刷新ScrollView的内容
     */
    private void setScrollViewContent() {
        //NestedScrollView下的LinearLayout
//        LinearLayout layout = (LinearLayout) view.findViewById(R.id.ll_sc_content);
//        layout.removeAllViews();
        for (int i = 0; i < tabData.size(); i++) {
            View view = View.inflate(getActivity(), R.layout.fg_home1_rv_item, null);
//            //动态添加 子View
//            layout.addView(view, i);
            initNestedList();
        }
    }
    public void initNestedList(){
        //适配器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.Nestedrecylcerview);
        recyclerView.setLayoutManager(layoutManager);//少了这一句就显示不了哦~~~~
        RVAdapter_NestedList adapter = new RVAdapter_NestedList(data.storeData);
        recyclerView.setAdapter(adapter);

    }


}
