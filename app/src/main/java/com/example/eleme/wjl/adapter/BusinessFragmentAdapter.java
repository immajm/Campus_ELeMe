package com.example.eleme.wjl.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.eleme.wjl.fragment.CommentFragment;
import com.example.eleme.wjl.fragment.OrderFragment;
import com.example.eleme.wjl.fragment.RetailerFragment;


public class BusinessFragmentAdapter extends FragmentPagerAdapter {
    private CommentFragment commentFragment;
    private OrderFragment orderFragment;
    private RetailerFragment retailerFragment;
    private  String[]mtitle;

    public BusinessFragmentAdapter(@NonNull FragmentManager fm,String[]mtitle,CommentFragment commentFragment,
                                   OrderFragment orderFragment,RetailerFragment retailerFragment) {
        super(fm);
        this.mtitle=mtitle;
        this.commentFragment=commentFragment;
        this.orderFragment=orderFragment;
        this.retailerFragment=retailerFragment;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return orderFragment;
            case 1:
                return commentFragment;
            case 2:
                return retailerFragment;
        }
        return orderFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return  mtitle[position];
    }

}
