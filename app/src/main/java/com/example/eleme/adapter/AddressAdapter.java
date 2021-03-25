package com.example.eleme.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eleme.BuyActivity;
import com.example.eleme.R;
import com.example.eleme.data.OneAddress;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public List addressList;
        public int address_type;//是选择地址还是仅查看  1选择 0查看
    public int shopId;

    class AddressViewHolder extends RecyclerView.ViewHolder {
            TextView address;
            TextView nameAndTel;
            TextView edit;
            LinearLayout oneAddress;
            public AddressViewHolder(@NonNull View itemView) {
                super(itemView);
                address = (TextView) itemView.findViewById(R.id.address_address);
                nameAndTel = (TextView) itemView.findViewById(R.id.address_nameandtel);
                edit = (TextView) itemView.findViewById(R.id.address_edit);
                oneAddress=(LinearLayout)itemView.findViewById(R.id.one_address);
            }


        }

        public AddressAdapter(List<Object> l,int t,int shopid) {
        shopId=shopid;
            addressList = l;
            address_type=t;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {

                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oneaddress, parent, false);
                final AddressViewHolder holder = new AddressViewHolder(view);
                holder.edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getAdapterPosition();
                        OneAddress oa=(OneAddress)addressList.get(position);
                        Toast.makeText(view.getContext(), "you clicked view" + oa.getAddress(), Toast.LENGTH_SHORT).show();
                    }
                });
            if (address_type == 1) {
                holder.oneAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getAdapterPosition();
                        OneAddress oa=(OneAddress)addressList.get(position);
                        BuyActivity.chosenAddress=oa.getAddressId();
                        Toast.makeText(view.getContext(), "选择成功" + oa.getAddress(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(v.getContext(), BuyActivity.class);
                        intent.putExtra("shop_id",shopId);
                        intent.putExtra("address_address",oa.getAddress());
                        intent.putExtra("address_nameandtel",oa.getName()+" "+oa.getTel());
                        intent.putExtra("address_id", oa.getAddressId());
                        //目标activity获取参数String shopname = getIntent().getStringExtra("shopname");
                        v.getContext().startActivity(intent);
                    }
                });
            }
                return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
                OneAddress c= (OneAddress) addressList.get(position);
                ((AddressViewHolder) holder).address.setText(c.getAddress());
                ((AddressViewHolder) holder).nameAndTel.setText(c.getName()+" "+c.getTel());
        }
        @Override
        public int getItemCount() {
            return addressList.size();
        }

}
