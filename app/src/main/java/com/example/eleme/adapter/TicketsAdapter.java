package com.example.eleme.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.R;
import com.example.eleme.data.OneTicket;

import java.util.List;

public class TicketsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public List ticketsList;

    class TicketViewHolder extends RecyclerView.ViewHolder {

        TextView ticketname;
        TextView ticketShop;
        TextView money;
        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            ticketname=(TextView) itemView.findViewById(R.id.ticket_name);
            ticketShop=(TextView) itemView.findViewById(R.id.ticket_shop);
            money=(TextView) itemView.findViewById(R.id.ticket_money);
        }


    }

    public TicketsAdapter(List<Object> l) {
        ticketsList = l;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneticket, parent, false);
        final TicketViewHolder holder = new TicketViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        OneTicket c= (OneTicket) ticketsList.get(position);
        ((TicketViewHolder) holder).ticketname.setText("优惠券");
        ((TicketViewHolder) holder).ticketShop.setText("适用商店："+c.getShopname());
        ((TicketViewHolder) holder).money.setText("￥"+c.gettMoney());
    }
    @Override
    public int getItemCount() {
        return ticketsList.size();
    }

}
