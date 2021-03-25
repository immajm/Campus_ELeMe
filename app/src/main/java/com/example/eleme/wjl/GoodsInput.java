package com.example.eleme.wjl;

import android.util.Log;

import com.example.eleme.MainActivity;
import com.example.eleme.R;
import com.example.eleme.wjl.bean.BeanGoods;
import com.example.eleme.wjl.bean.BeanMenu;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodsInput implements Runnable
{

    public static BeanMenu[] listclass;

    public  static  List<BeanGoods>listgoods;

//    private Handler handler;
//    private Handler handler2;

//    public GoodsInput(Handler handler,Handler handler2)
//    {
//        this.handler = handler;
//        this.handler2 =handler2;
//    }

    public GoodsInput()
    {
    }

    private void initGoods(final int classId, int index)
    {
        List<BeanGoods> list = new ArrayList<>();
        try
        {
            FormBody.Builder params = new FormBody.Builder();
            params.add("shop_id", GoodsListActivity.shopId + "");
            params.add("goods_class_id", classId + "");
            OkHttpClient client = new OkHttpClient();//创建http客户端
            Request request = new Request.Builder()
                    .url(MainActivity.service+"/queryshopclassgoods")
                    .post(params.build())
                    .build();//创造http请求
            Response responese = client.newCall(request).execute();//执行发送的指令
            Log.d("connecttt",responese.toString());
            final String responseData = responese.body().string();//获取返回回来的json格式的结果
            JSONArray ja = new JSONArray(responseData);//字符串转jsonarray
        Log.d("connecttt",ja.toString());
            for (int i = 0; i < ja.length(); i++)
            {
                JSONObject jb = ja.getJSONObject(i);
                BeanGoods a = new BeanGoods();
                a.setGoodsid(jb.getString("goods_id"));
                a.setClassid(String.valueOf(classId));
                a.setImg(R.drawable.glgs);
                a.setDes("羊肉串2串+牛肉串2串+骨肉相连2串");
                a.setName(jb.getString("goods_name"));
                a.setPrice(jb.getString("d_price"));
                a.setNum("月售102");
                a.setVipprice("品牌会员价￥31.9");
                a.setTag(String.valueOf(index));
                a.setTitlename(listclass[index].getMenuname());
                list.add(a);
                listgoods.add(a);
                listclass[index].setCata(list);
                Log.v("listgoods" + i, a.getName());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        listgoods=new ArrayList<>();
        try
        {
            FormBody.Builder params = new FormBody.Builder();
            params.add("id", GoodsListActivity.shopId + "");
            OkHttpClient client = new OkHttpClient();//创建http客户端
            Request request = new Request.Builder()
                    .url(MainActivity.service+"/queryshopgoodsclass")
                    .post(params.build())
                    .build();//创造http请求

            Response responese = client.newCall(request).execute();
            final String responseData = responese.body().string();
            JSONArray ja = new JSONArray(responseData);//字符串转jsonarray
            Log.d("err",ja.toString());
            listclass = new BeanMenu[ja.length()];
            for (int i = 0; i < ja.length(); i++)
            {
                JSONObject jb = ja.getJSONObject(i);
                System.out.println(jb);
                Log.d("err",jb.toString());
                BeanMenu beanMenu = new BeanMenu();
                beanMenu.setMenuname(jb.getString("goods_class_name"));
                beanMenu.setMenuid(jb.getInt("goods_class_id"));

                listclass[i] = beanMenu;
                initGoods((listclass[i]).getMenuid(),i);
            }
            System.out.println(listclass);
            System.out.println(listgoods);

//            Message msg = new Message();
//            msg.obj=listclass;
//            msg.arg1 = 1;
//            handler.sendMessage(msg);
//            Message msg2 = new Message();
//            msg2.obj=listgoods;
//            msg2.arg1 = 1;
//            handler2.sendMessage(msg2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
