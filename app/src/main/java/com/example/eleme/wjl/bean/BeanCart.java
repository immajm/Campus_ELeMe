package com.example.eleme.wjl.bean;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.eleme.MainActivity;
import com.example.eleme.wjl.DataChangeListener;
import com.example.eleme.wjl.GoodsListActivity;
import com.example.eleme.wjl.adapter.GoodsRecyclerAdapter;
import com.example.eleme.wjl.adapter.MenuRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @version : 1.0
 * @author: momoshenchi
 * @date: 2021/1/14 - 16:07
 */
public class BeanCart
{

    private List<BeanCartItem> goods;
    private double mailPrice;
    private int totalNum;
    private double totalPrice;
    private DataChangeListener dataChangeListener;

    public DataChangeListener getDataChangeListener()
    {
        return dataChangeListener;
    }

    public int getTotalNum()
    {
        return totalNum;
    }

    public void setTotalNum(int totalNum)
    {
        this.totalNum = totalNum;
    }

    public void setDataChangeListener(DataChangeListener dataChangeListener)
    {
        this.dataChangeListener = dataChangeListener;
    }

    public List<BeanCartItem> getGoods()
    {
        return goods;
    }

    public int getGoodsNum(BeanGoods b)
    {
        for (int i = 0; i < goods.size(); i++)
        {
            if (goods.get(i).getBeanGoods().equals(b))
            {
                return goods.get(i).getNum();
            }
        }
        return 0;
    }

    private int getGoodsIndex(BeanGoods b)
    {
        for (int i = 0; i < goods.size(); i++)
        {
            if (goods.get(i).getBeanGoods().equals(b))
            {
                return i;
            }
        }
        return -1;
    }

    public boolean isEmpty()
    {
        return goods.isEmpty();
    }

    public BeanCart()
    {
        goods = new ArrayList<>();
        totalPrice = 0;
        totalNum = 0;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addGoods(BeanGoods b)
    {
        int num = getGoodsIndex(b);

        if (num != -1)
        {
            int i = goods.get(num).getNum();
            goods.get(num).setNum(i + 1);
            GoodsRecyclerAdapter.dataChangeListener.change(goods.get(num).getNum());
        }
        else
        {
            goods.add(new BeanCartItem(b, 1));
            GoodsRecyclerAdapter.dataChangeListener.change(1);
        }
        totalNum += 1;
        totalPrice += Double.parseDouble(b.getPrice());
        dataChangeListener.change(totalNum);
        MenuRecyclerAdapter.dataChangeListener.change(1);

        final  BeanGoods goods=b;
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    FormBody.Builder params = new FormBody.Builder();
                    params.add("shop_id",  GoodsListActivity.shopId+ "");
                    params.add("goods_count", "1");
                    params.add("class_id", goods.getClassid() + "");
                    params.add("goods_id", goods.getGoodsid() + "");
                    params.add("customer_id", MainActivity.customer_id + "");
                    Log.v("id",GoodsListActivity.shopId+" "+goods.getClassid() +" "+goods.getGoodsid());
                    OkHttpClient client = new OkHttpClient();//创建http客户端
                    Request request = new Request.Builder()
                            .url(MainActivity.service+"/addcart")
                            .post(params.build())
                            .build();//创造http请求
                    Response responese = client.newCall(request).execute();//执行发送的指令
                    final String responseData = responese.body().string();//获取返回回来的json格式的结果
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void subGoods(BeanGoods b)
    {
        int num = getGoodsIndex(b);
        if (num != -1)
        {
            int i = goods.get(num).getNum();
            int value;
            if (i == 1)
            {
                goods.remove(num);
                GoodsRecyclerAdapter.dataChangeListener.change(0);
            }
            else
            {
                goods.get(num).setNum(i - 1);
                GoodsRecyclerAdapter.dataChangeListener.change(goods.get(num).getNum());
            }
            totalNum -= 1;
            totalPrice -= Double.parseDouble(b.getPrice());
            dataChangeListener.change(totalNum);
            MenuRecyclerAdapter.dataChangeListener.change(-1);
        }
    }

    public boolean containsGoods(BeanGoods b)
    {
        return getGoodsIndex(b) != -1;
    }

    public double getMailPrice()
    {
        return mailPrice;
    }

    public void setMailPrice(double mailPrice)
    {
        this.mailPrice = mailPrice;
    }

    public double getTotalPrice()
    {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice)
    {
        this.totalPrice = totalPrice;
    }
}
