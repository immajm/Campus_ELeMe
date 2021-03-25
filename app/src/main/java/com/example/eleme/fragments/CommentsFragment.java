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
import com.example.eleme.adapter.CommentsAdapter;
import com.example.eleme.R;
import com.example.eleme.data.OneComment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommentsFragment extends Fragment {
    static List<Object> list=new ArrayList<>();//评价列表
    int shopId;
    static Context c;
    static View view;
    static CommentsAdapter adapter;
    static RecyclerView recycleView;
    public CommentsFragment(int s, Context context) {
        shopId=s;
        c=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shop_comment, container, false);
        recycleView = view.findViewById(R.id.comments_recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recycleView.setLayoutManager(layoutManager);
        initComments();
        adapter = new CommentsAdapter(list);
        recycleView.setAdapter(adapter);

        return view;
    }
    private void initComments(){
        list.clear();
        list.add(new OneComment("兰淑敏","很普通",3));
        list.add(new OneComment("徐梦璠","好好好",5));
        list.add(new OneComment("魏弋力","难吃",1));

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    FormBody.Builder params=new FormBody.Builder();
                    params.add("shop_id",shopId+"");
                    OkHttpClient client=new OkHttpClient();//创建http客户端
                    Request request=new Request.Builder()
                            .url(MainActivity.service+"/querycomment")
                            .post(params.build())
                            .build();//创造http请求

                    Response responese=client.newCall(request).execute();
                    final String responseData=responese.body().string();
                    JSONArray ja=new JSONArray(responseData);
                    for(int i=0;i<ja.length();i++){
                        JSONObject jb=ja.getJSONObject(i);

                        FormBody.Builder params1=new FormBody.Builder();
                        params1.add("id",jb.getInt("customer_id")+"");
                        OkHttpClient client1=new OkHttpClient();//创建http客户端
                        Request request1=new Request.Builder()
                                .url(MainActivity.service+"/queryuser")
                                .post(params1.build())
                                .build();//创造http请求

                        Response responese1=client1.newCall(request1).execute();//执行发送的指令
                        final String responsecustomername=responese1.body().string();//获取返回回来的json格式的结果
                        Log.d("responsecustomername", responsecustomername);
                        JSONObject customerJB=new JSONObject(responsecustomername);//字符串转jsonarray

                        OneComment a=new OneComment(
                                customerJB.getString("customer_name"),
                                jb.getString("comment_content"),
                                jb.getDouble("star")

                        );
                        list.add(a);

                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                buildComments();
                            }
                        });
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

    public static void buildComments(){

        recycleView = view.findViewById(R.id.comments_recycleView);
        LinearLayoutManager layoutManagercomment= new LinearLayoutManager(c,LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(layoutManagercomment);
        adapter= new CommentsAdapter(list);
        recycleView.setAdapter(adapter);
    }
}
