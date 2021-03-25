package com.example.eleme.wjl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

public class TabBehavior extends CoordinatorLayout.Behavior<View> {
    public TabBehavior() {

    }

    public TabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
       //让tab 紧贴toolbar的下边缘
//        int delta = (int) dependency.getTranslationY() + dependency.getBottom();
//        delta = delta - child.getTop();
//        child.offsetTopAndBottom(delta);
//        child.setY(delta);
        child.setY(dependency.getY()+dependency.getHeight()-10);
        return true;
    }
}
