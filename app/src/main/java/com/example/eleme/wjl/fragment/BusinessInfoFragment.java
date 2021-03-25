package com.example.eleme.wjl.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.eleme.R;


public class BusinessInfoFragment extends Fragment
{
    private ConstraintLayout constraintLayout;
    private  View view;
    private   Dialog dialog;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_business_info, container, false);
        constraintLayout = view.findViewById(R.id.btn_constarint);
        constraintLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                appear(container);
            }
        });
        return view;
    }

    public void appear(ViewGroup container)
    {
        dialog = new Dialog(getActivity(), R.style.bottom_anim_theme);

            view = View.inflate( dialog.getContext(),R.layout.fragment_rule_bottom, null);

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);

        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        dialog.findViewById(R.id.rule_bottom_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

    }
}
