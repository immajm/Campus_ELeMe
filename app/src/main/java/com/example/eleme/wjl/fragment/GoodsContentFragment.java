package com.example.eleme.wjl.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.eleme.R;

/**
 * @version : 1.0
 * @author: momoshenchi
 * @date: 2021/1/15 - 0:14
 */
public class GoodsContentFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_goods_content_bottom, container,false);
    }
}
