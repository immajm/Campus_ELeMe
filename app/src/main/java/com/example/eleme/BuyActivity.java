package com.example.eleme;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.adapter.ChangeAdapter;
import com.example.eleme.adapter.OneGoodsInCartAdapter;
import com.example.eleme.data.OneCart;
import com.example.eleme.data.OneChange;
import com.example.eleme.wjl.GoodsListActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BuyActivity extends AppCompatActivity {
    public List<Object> list=new ArrayList<>();
    public List<Object> listChange=new ArrayList<>();
    View buyAddress;
    ImageButton buyVolumn;
    ImageButton buyClock;
    ImageButton buyCheck;
    ImageButton buyCall;
    TextView buyPrice;
    Button buy;
    TextView address_address;
    TextView address_nameandtel;
    LinearLayout linearLayout;
    public static int chosenAddress=0;
    int shopId;
    public static int p=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        final Context c=this;
        p=0;
        buyVolumn=findViewById(R.id.buy_volumn);
        Drawable volumn=buyVolumn.getDrawable();
        volumn.setTint(Color.argb(255, 222,176,97));
        buyClock=findViewById(R.id.buy_clock);
        Drawable clock=buyClock.getDrawable();
        clock.setTint(Color.argb(255, 255,255,255));
        buyCheck=findViewById(R.id.buy_check);
        Drawable check=buyCheck.getDrawable();
        check.setTint(Color.argb(255, 255,255,255));
        buyCall=findViewById(R.id.buy_call);
        Drawable call=buyCall.getDrawable();
        call.setTint(Color.argb(255, 255,255,255));

        buyPrice=findViewById(R.id.buy_price);
        shopId= getIntent().getIntExtra("shop_id",1);
        Log.v("shopid!!after",shopId+"");
        Toolbar toolbar = findViewById(R.id.buy_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {//?????????  ????????????activity
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyActivity.this, GoodsListActivity.class);
                intent.putExtra("shop_id", shopId);
                startActivity(intent);
            }
        });
        buyAddress = findViewById(R.id.buy_addrsss);
        address_address=findViewById(R.id.address_address);
        address_nameandtel=findViewById(R.id.address_nameandtel);
        buyAddress.setOnClickListener(new chooseAddress());
        if(BuyActivity.chosenAddress!=0){
            chosenAddress= getIntent().getIntExtra("address_id",1);
            address_address.setText( getIntent().getStringExtra("address_address"));
            address_nameandtel.setText( getIntent().getStringExtra("address_nameandtel"));
        }
        buy=(Button)findViewById(R.id.buy_btn_buy);
        buy.setOnClickListener(new BuyOnClick());
        initBuy();//??????????????????
        buildGoods();
        initChange();
        buildChange();

//        linearLayout = findViewById(R.id.buy_canvas);
//        MyView myView = new MyView(this);
//        linearLayout.addView(myView);

    }
    private class chooseAddress implements View.OnClickListener{//??????????????????

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(BuyActivity.this, ChooseAddressActivity.class);
            intent.putExtra("shop_id",shopId);
            startActivity(intent);
        }
    }
    private class BuyOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(BuyActivity.chosenAddress!=0){//????????????
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            FormBody.Builder params=new FormBody.Builder();
                            params.add("customer_id", MainActivity.customer_id+"");
                            params.add("address_id",chosenAddress+"");
                            params.add("shop_id",shopId+"");
                            Log.v("shopid!!!",shopId+"");
                            OkHttpClient client=new OkHttpClient();//??????http?????????
                            Request request=new Request.Builder()
                                    .url(MainActivity.service+"/useraddorders")
                                    .post(params.build())
                                    .build();//??????http??????
                            Response responese=client.newCall(request).execute();//?????????????????????
                            final String responseData=responese.body().string();//?????????????????????json???????????????
                            JSONObject jb=new JSONObject(responseData);//????????????jsonarray

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(BuyActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                                }
                            });
                            Intent intent = new Intent(BuyActivity.this, BuyEndActivity.class);
                            intent.putExtra("order_id", jb.getInt("order_id"));
                            startActivity(intent);
                        }catch(Exception e){
                            e.printStackTrace();
                            Looper.prepare();
                            Toast.makeText(BuyActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                            Looper.loop();

                        }
                    }
                }).start();

            }
            else {
                Toast.makeText(BuyActivity.this, "?????????????????????", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void initBuy(){
        list.clear();
        p=0;
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    FormBody.Builder params=new FormBody.Builder();
                    params.add("customer_id", MainActivity.customer_id+"");
                    params.add("shop_id",shopId+"");
                    OkHttpClient client=new OkHttpClient();//??????http?????????
                    Request request=new Request.Builder()
                            .url(MainActivity.service+"/queryusershopcart")
                            .post(params.build())
                            .build();//??????http??????
                    Response responese=client.newCall(request).execute();//?????????????????????
                    final String responseData=responese.body().string();//?????????????????????json???????????????
                    JSONArray ja=new JSONArray(responseData);//????????????jsonarray

                    for(int i=0;i<ja.length();i++){
                        JSONObject jb=ja.getJSONObject(i);

                        FormBody.Builder params1=new FormBody.Builder();
                        params1.add("goods_id", jb.getInt("goods_id")+"");
                        Log.v("goodsid",jb.getInt("goods_id")+"");
                        OkHttpClient client1=new OkHttpClient();//??????http?????????
                        Request request1=new Request.Builder()
                                .url(MainActivity.service+"/queryonegoods")
                                .post(params1.build())
                                .build();//??????http??????
                        Response responese1=client1.newCall(request1).execute();//?????????????????????
                        final String responseData1=responese1.body().string();//?????????????????????json???????????????
                        JSONArray ja1=new JSONArray(responseData1);
                        JSONObject jb1=ja1.getJSONObject(0);
                        OneCart a=new OneCart(
                                R.drawable.noodles,
                                jb1.getString("goods_name"),
                                jb.getInt("goods_count"),
                                Float.parseFloat(jb1.getString("d_price")),
                                Float.parseFloat(jb1.getString("d_price"))+2
                        );
                        p+=jb.getInt("goods_count")*jb1.getDouble("d_price");
                        list.add(a);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buildGoods();
                        }
                    });
                }catch(Exception e){
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(BuyActivity.this, "????????????", Toast.LENGTH_SHORT).show();
                    Looper.loop();

                }
            }
        }).start();
    }
    public void buildGoods(){
        RecyclerView recycleView = (RecyclerView) findViewById(R.id.buy_recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleView.setLayoutManager(layoutManager);
        OneGoodsInCartAdapter adapter = new OneGoodsInCartAdapter(list);
        recycleView.setAdapter(adapter);
        buyPrice.setText("???"+p);
    }
    private void initChange(){
        listChange.clear();
        listChange.add(new OneChange(R.drawable.p13,"?????????",10,8,"85?????????"));
        listChange.add(new OneChange(R.drawable.p14,"????????????",5,3.5,"85?????????"));
        listChange.add(new OneChange(R.drawable.p15,"??????",10,8.5,"85?????????"));
        listChange.add(new OneChange(R.drawable.p16,"??????",5,3.5,"85?????????"));

    }
    public void buildChange(){
        RecyclerView recycleView = (RecyclerView) findViewById(R.id.change_recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager((this));
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView.setLayoutManager(layoutManager);
        ChangeAdapter adapter = new ChangeAdapter(listChange);
        recycleView.setAdapter(adapter);
    }
    class MyView extends View{
        Paint paint;

        public MyView(Context context) {
            super(context);
            paint = new Paint(); //???????????????????????????3??????????????????
            paint.setColor(Color.YELLOW);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(3);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            RectF rect = new RectF(20, 20, 50, 50);
            canvas.drawRoundRect(rect,
                    30, //x????????????
                    30, //y????????????
                    paint);

        }
    }

}