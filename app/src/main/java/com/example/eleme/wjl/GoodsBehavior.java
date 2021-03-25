package com.example.eleme.wjl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

public class GoodsBehavior extends CoordinatorLayout.Behavior<RecyclerView> {
    public GoodsBehavior() {

    }

    public GoodsBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        //让recyclerview紧贴bar的下边缘

        int delta = (int) dependency.getTranslationY() + dependency.getBottom();
        delta = delta - child.getTop();
        child.offsetTopAndBottom(delta);
        return true;
    }
}
