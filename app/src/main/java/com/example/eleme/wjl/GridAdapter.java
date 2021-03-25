package com.example.eleme.wjl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.eleme.R;
public class GridAdapter extends BaseAdapter {
    public Context context;
    public int[] content_photo;
    public GridAdapter(int[] t, Context context) {
        content_photo=t;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(content_photo==null)
            return  0;
        return content_photo.length;
    }

    @Override
    public Object getItem(int position) {
        return content_photo[position];
    }

    @Override
    public long getItemId(int position) {
         return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView =inflater.inflate(R.layout.layout_gridview, null);
            viewHolder = new ViewHolder();
            viewHolder.itemImg =  convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(position<content_photo.length)
            viewHolder.itemImg.setImageResource(content_photo[position]);
        else
            viewHolder.itemImg.setVisibility(View.GONE);
        return convertView;
    }
    class ViewHolder {
        ImageView itemImg;
    }

}

