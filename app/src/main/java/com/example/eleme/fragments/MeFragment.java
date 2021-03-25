package com.example.eleme.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.MainActivity;
import com.example.eleme.MyMsgActivity;
import com.example.eleme.R;
import com.example.eleme.adapter.AddressAdapter;
import com.example.eleme.adapter.TicketsAdapter;
import com.example.eleme.data.OneAddress;
import com.example.eleme.data.OneTicket;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MeFragment extends Fragment {
    static List<Object> list=new ArrayList<>();
    NavigationView navigationView;
    static Context c;
    static View view;
    static TicketsAdapter adapter;
    static AddressAdapter adapter2;
    static RecyclerView recycleView;
    static RecyclerView recycleView2;
    private Uri imageUri;
    public static final int TAKE_PHOTO = 1;
    ImageView meHead;
    TextView name;
    public MeFragment(Context context) {
        c=context;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        meHead=(ImageView)view.findViewById(R.id.me_head);
        meHead.setOnClickListener(new HeadOnClick());
        File outputImage = new File(getActivity().getExternalCacheDir() , "tempImage.jpg");
        if (outputImage.exists()) {
            imageUri=Uri.fromFile(outputImage);
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            meHead.setImageBitmap(bitmap);
        }
        name=view.findViewById(R.id.user_name);
        name.setOnClickListener(new NameOnClick());
        navigationView= view.findViewById(R.id.me_navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.my_ticket:

                        recycleView = view.findViewById(R.id.ticket_recycleView);
                        recycleView.setLayoutManager(layoutManager);
                        initTickets();
                        adapter = new TicketsAdapter(list);
                        recycleView.setAdapter(adapter);
                        break;
                    case R.id.my_address:

                        recycleView2 = view.findViewById(R.id.ticket_recycleView);
                        recycleView2.setLayoutManager(layoutManager);
                        initAddress();
                        adapter2 = new AddressAdapter(list,0,1);//0代表 仅查看 不是购买
                        recycleView2.setAdapter(adapter2);

                        break;

                }
                return true;
            }
        });
        return view;
    }

    private void initTickets(){
        list.clear();
//        list.add(new OneTicket(5, Timestamp.valueOf("2020-01-01 1:1:1"), Timestamp.valueOf("2020-01-01 11:11:11")));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FormBody.Builder params=new FormBody.Builder();
                    params.add("customer_id",""+MainActivity.customer_id);
                    OkHttpClient client=new OkHttpClient();//创建http客户端
                    Request request=new Request.Builder()
                            .url(MainActivity.service+"/queryuserticket")
                            .post(params.build())
                            .build();//创造http请求
                    Response responese=client.newCall(request).execute();//执行发送的指令
                    final String responseData=responese.body().string();//获取返回的json格式的结果
                    JSONArray ja=new JSONArray(responseData);//字符串转jsonarray
                    for(int i=0;i<ja.length();i++){
                        JSONObject jb=ja.getJSONObject(i);


                        FormBody.Builder params1=new FormBody.Builder();//用id查商家名称
                        params1.add("shop_id",""+jb.getInt("shop_id"));
                        OkHttpClient client1=new OkHttpClient();//创建http客户端
                        Request request1=new Request.Builder()
                                .url(MainActivity.service+"/queryshopname")
                                .post(params1.build())
                                .build();//创造http请求
                        Response responese1=client1.newCall(request1).execute();//执行发送的指令
                        String responseData1=responese1.body().string();//返回商店名
//                        JSONObject shopJB=new JSONObject(responseData1);

                        OneTicket a=new OneTicket("优惠券",
                                (float) jb.getDouble("ticket_money"),
                                responseData1,
                                jb.getInt("ticket_count")
                                );
                        if(jb.getInt("ticket_count")!=0)//不为0 则加载出优惠券
                        list.add(a);
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                buildTicket();
                            }
                        });

                    }
                }catch(Exception e){
                    e.printStackTrace();
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(c, "获取失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();
    }
    private void initAddress(){
        list.clear();
//        list.add(new OneAddress("浙大城市学院北校区","兰淑敏","19858"));
//        list.add(new OneAddress("浙大城市学院", "lsm","198"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FormBody.Builder params=new FormBody.Builder();
                    params.add("id",MainActivity.customer_id+"");
                    OkHttpClient client=new OkHttpClient();//创建http客户端
                    Request request=new Request.Builder()
                            .url(MainActivity.service+"/queryaddress")
                            .post(params.build())
                            .build();//创造http请求
                    Response responese=client.newCall(request).execute();//执行发送的指令
                    final String responseData=responese.body().string();//获取返回的json格式的结果
                    JSONArray ja=new JSONArray(responseData);//字符串转jsonarray
                    for(int i=0;i<ja.length();i++){
                        JSONObject jb=ja.getJSONObject(i);
                        OneAddress a=new OneAddress(jb.getInt("address_id"),
                                jb.getString("province")+jb.getString("town")+jb.getString("block")+jb.getString("specific_address"),
                                jb.getString("contact_name"),
                                jb.getString("contact_tel"));
                        list.add(a);
                        view.post(new Runnable() {
                            @Override
                            public void run() {
                                buildAddress();
                            }
                        });

                    }
                }catch(Exception e){
                    e.printStackTrace();
                    e.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(c, "获取失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }).start();

    }

    private class NameOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MyMsgActivity.class);
            startActivity(intent);
        }
    }
    private class HeadOnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            File outputImage = new File(getActivity().getExternalCacheDir() , "tempImage.jpg");
            try {
                if (outputImage.exists()) {
                    outputImage.delete();
                }
                outputImage.createNewFile();
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
                    imageUri= FileProvider.getUriForFile(getActivity(),"com.example.eleme.fileprovider",outputImage);
                }
                else{
                    imageUri= Uri.fromFile(outputImage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //启动相机程序
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, TAKE_PHOTO); // 启动相机程序
        }
    }
    public static void buildAddress(){
        adapter2= new AddressAdapter(list,0,1);
        recycleView2.setAdapter(adapter2);
    }
    public static void buildTicket(){
        adapter= new TicketsAdapter(list);
        recycleView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode ==-1){
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        meHead.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
