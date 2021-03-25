package com.example.eleme.adapter;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.MainActivity;
import com.example.eleme.R;
import com.example.eleme.data.OneGetTicket;

import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetTicketAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public List list;
        View view;
    class GetTicketViewHolder extends RecyclerView.ViewHolder {
            TextView money;
            Button btnGet;
            public GetTicketViewHolder(@NonNull View itemView) {
                super(itemView);
                money= (TextView) itemView.findViewById(R.id.get_ticket_money);
                btnGet=itemView.findViewById(R.id.get_btn);
            }


        }

        public GetTicketAdapter(List<Object> l) {
            list = l;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.onegetticket, parent, false);
            GetTicketViewHolder holder = new GetTicketViewHolder(view);


                return holder;

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                OneGetTicket c= (OneGetTicket) list.get(position);
                ((GetTicketViewHolder) holder).money.setText(c.getMoney()+"");
                ((GetTicketViewHolder) holder).btnGet.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
//                    final OneGetTicket c = (OneGetTicket) list.get(position);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                FormBody.Builder params=new FormBody.Builder();
                                params.add("customer_id", MainActivity.customer_id+"");
                                params.add("shop_id", MainActivity.customer_id+"");
                                params.add("ticket_id", ((OneGetTicket) list.get(position)).getId()+"");
                                OkHttpClient client=new OkHttpClient();//创建http客户端
                                Request request=new Request.Builder()
                                        .url(MainActivity.service+"/adduserticket")
                                        .post(params.build())
                                        .build();//创造http请求
                                Response responese=client.newCall(request).execute();//执行发送的指令
                                final String responseData=responese.body().string();//获取返回的json格式的结果
                                view.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(view.getContext(), responseData, Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }catch(Exception e){
                                e.printStackTrace();
                                e.printStackTrace();
                                Looper.prepare();
                                Toast.makeText(view.getContext(), "获取失败", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    }).start();
                    view.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }
            });

        }
        @Override
        public int getItemCount() {
            return list.size();
        }
}
