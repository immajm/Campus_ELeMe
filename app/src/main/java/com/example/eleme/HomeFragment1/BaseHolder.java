package com.example.eleme.HomeFragment1;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class BaseHolder<T> extends RecyclerView.ViewHolder {

    public BaseHolder(int viewId, ViewGroup parent, int viewType) {
        super(((LayoutInflater) parent.getContext()
                .getSystemService(parent.getContext().LAYOUT_INFLATER_SERVICE))
                .inflate(viewId, parent,false));
    }

    public void refreshData(T data, int position) {

    }
}
