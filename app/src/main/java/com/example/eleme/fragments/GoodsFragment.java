package com.example.eleme.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.MainActivity;
import com.example.eleme.adapter.GoodsAdapter;
import com.example.eleme.R;
import com.example.eleme.adapter.ShopClassAdapter;
import com.example.eleme.data.OneClass;
import com.example.eleme.data.OneGoods;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodsFragment extends Fragment {

    public static List<Object> listclass=new ArrayList<>();
    public static List<Object> listgoods=new ArrayList<>();
    static Context c;
    static View view;
    public static int shopId;
    static int firstClass_id=-1;

    static RecyclerView recycleViewright;
    static GoodsAdapter adapterright;

    public GoodsFragment(int s, Context context) {
        shopId=s;
        c=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        firstClass_id=-1;
        view = inflater.inflate(R.layout.fragment_shop, container, false);
        initGoodsClass();

        RecyclerView recycleViewleft = view.findViewById(R.id.class_recycleView);
        LinearLayoutManager layoutManagerleft= new LinearLayoutManager(this.getActivity());
        recycleViewleft.setLayoutManager(layoutManagerleft);
        ShopClassAdapter adapterleft = new ShopClassAdapter(listclass);
        layoutManagerleft.setOrientation(RecyclerView.VERTICAL);
        recycleViewleft.setAdapter(adapterleft);

        RecyclerView recycleViewright = view.findViewById(R.id.goods_recycleView);
        LinearLayoutManager layoutManagerright= new LinearLayoutManager(this.getActivity());
        recycleViewright.setLayoutManager(layoutManagerright);
        GoodsAdapter adapterright= new GoodsAdapter(listgoods);
        layoutManagerright.setOrientation(RecyclerView.VERTICAL);
        recycleViewright.setAdapter(adapterright);
        if(firstClass_id!=-1){
            initGoods(firstClass_id);
            adapterright = new GoodsAdapter(listgoods);
            recycleViewright.setAdapter(adapterright);
        }

        return view;
    }
    public void initGoodsClass(){
        listclass.clear();
//        listclass.add(new OneClass(1,"类名1"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FormBody.Builder params=new FormBody.Builder();
                    params.add("id",shopId+"");
                    OkHttpClient client=new OkHttpClient();//创建http客户端
                    Request request=new Request.Builder()
                            .url(MainActivity.service+"/queryshopgoodsclass")
                            .post(params.build())
                            .build();//创造http请求

                    Response responese=client.newCall(request).execute();
                    final String responseData=responese.body().string();
                    JSONArray ja=new JSONArray(responseData);//字符串转jsonarray

                    for(int i=0;i<ja.length();i++){
                        JSONObject jb=ja.getJSONObject(i);
                        OneClass a=new OneClass(jb.getInt("goods_class_id"),
                                jb.getString("goods_class_name"));
                        listclass.add(a);
                    }
                    if(listclass.size()>0){
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                initGoods(((OneClass)listclass.get(0)).getClass_id());
//                                buildright();
                            }
                        });
                        firstClass_id=((OneClass)listclass.get(0)).getClass_id();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(c, "获取失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();

                }
            }
        }).start();
    }
    public static void initGoods(final int classId){
        listgoods.clear();
        Log.v("class_id",classId+"");
//        listgoods.add(new OneGoods(R.drawable.shopping3,"商品1",100,85,1997));
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    FormBody.Builder params=new FormBody.Builder();
                    params.add("shop_id",shopId+"");
                    params.add("goods_class_id",classId+"");
                    OkHttpClient client=new OkHttpClient();//创建http客户端
                    Request request=new Request.Builder()
                            .url(MainActivity.service+"/queryshopclassgoods")
                            .post(params.build())
                            .build();//创造http请求

                    Response responese=client.newCall(request).execute();//执行发送的指令
                    final String responseData=responese.body().string();//获取返回回来的json格式的结果
                    JSONArray ja=new JSONArray(responseData);//字符串转jsonarray

                    for(int i=0;i<ja.length();i++){
                        JSONObject jb=ja.getJSONObject(i);
                        OneGoods a=new OneGoods(
                                jb.getInt("goods_id"),R.drawable.noodles,
                                jb.getString("goods_name"),
                                Float.parseFloat(jb.getString("d_price"))+2,
                                Float.parseFloat(jb.getString("d_price")),
                                jb.getInt("goods_count"),
                                classId
                        );
                        listgoods.add(a);
                        Log.v("listgoods"+i,a.goodsName);
                    }
                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            buildright();
                        }
                    });

                }catch(Exception e){
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(c, "获取失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();

                }
            }
        }).start();
    }
    public static void buildright(){
        recycleViewright = (RecyclerView) view.findViewById(R.id.goods_recycleView);
        LinearLayoutManager layoutManagerright= new LinearLayoutManager(c,LinearLayoutManager.VERTICAL, false);
        recycleViewright.setLayoutManager(layoutManagerright);
        adapterright= new GoodsAdapter(listgoods);
        recycleViewright.setAdapter(adapterright);
    }
}
