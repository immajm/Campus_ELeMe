package com.example.eleme.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.MainActivity;
import com.example.eleme.R;
import com.example.eleme.data.FullRedution;
import com.example.eleme.data.OneLine;
import com.example.eleme.data.OneShop;
import com.example.eleme.adapter.recycleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment {
    static List<Object> list=new ArrayList<>();
    static Context c;
    static View view;
    static recycleAdapter adapter;
    static RecyclerView recycleView;
    public HomeFragment(Context context) {
        c=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        recycleView = view.findViewById(R.id.recycleView);
        LinearLayoutManager layoutManagershop= new LinearLayoutManager(c,LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(layoutManagershop);
        initClass();
        initShop();
        adapter= new recycleAdapter(list);
        recycleView.setAdapter(adapter);
        return view;
    }
    private void initClass(){
        list.clear();
        Map<Integer,String> m =new HashMap<Integer,String>();
        m.put(R.drawable.eat,"美食外卖");
        m.put(R.drawable.shop,"超市便利");
        m.put(R.drawable.hair,"团购优惠");
        m.put(R.drawable.shopping2,"休闲玩乐");
        OneLine line=new OneLine(m);
        list.add(line);

        m =new HashMap<Integer,String>();
        m.put(R.drawable.eat2,"夜宵");
        m.put(R.drawable.fruit,"水果");
        m.put(R.drawable.drink2,"甜品饮品");
        m.put(R.drawable.cai,"买菜");
        line=new OneLine(m);
        list.add(line);


        m =new HashMap<Integer,String>();
        m.put(R.drawable.medicine,"送药上门");
        m.put(R.drawable.hair,"丽人美发");
        m.put(R.drawable.tree,"0元零水果");
        m.put(R.drawable.money,"省钱好券");
        line=new OneLine(m);
        list.add(line);

    }
    private void initShop(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FormBody.Builder params=new FormBody.Builder();
                    OkHttpClient client=new OkHttpClient();//创建http客户端
                    Request request=new Request.Builder()
                            .url(MainActivity.service+"/queryallshop")
                            .post(params.build())
                            .build();//创造http请求

                    Response responese=client.newCall(request).execute();//执行发送的指令
                    final String responseData=responese.body().string();//获取返回回来的json格式的结果
                    JSONArray ja=new JSONArray(responseData);//字符串转jsonarray


                    for(int i=0;i<ja.length();i++){
                        JSONObject jb=ja.getJSONObject(i);

                        FormBody.Builder params1=new FormBody.Builder();//查询满减
                        params1.add("shop_id",jb.getInt("shop_id")+"");
                        OkHttpClient client1=new OkHttpClient();//创建http客户端
                        Request request1=new Request.Builder()
                                .url(MainActivity.service+"/queryfullredution")
                                .post(params1.build())
                                .build();//创造http请求

                        Response responese1=client1.newCall(request1).execute();//执行发送的指令
                        final String responseData1=responese1.body().string();//获取返回回来的json格式的结果
                        JSONArray frJA=new JSONArray(responseData1);//字符串转jsonarray
                        List<FullRedution> frlist=new ArrayList<FullRedution>();
                        for(int j=0;j<frJA.length();j++){
                            JSONObject frJB=frJA.getJSONObject(j);
                            FullRedution fr=new FullRedution(frJB.getDouble("full_money"),frJB.getDouble("redution_money"));
                            frlist.add(fr);
                        }

                        OneShop shop=new OneShop(
                                jb.getInt("shop_id"),R.drawable.shop,
                                jb.getString("shop_name"),
                                jb.getDouble("shop_star"),
                                frlist);

                        list.add(shop);
                    }

                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            buildShops();
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
    public static void buildShops(){

        recycleView = view.findViewById(R.id.recycleView);
        LinearLayoutManager layoutManagershop= new LinearLayoutManager(c,LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(layoutManagershop);
        adapter= new recycleAdapter(list);
        recycleView.setAdapter(adapter);
    }
}
