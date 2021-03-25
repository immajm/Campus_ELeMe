package com.example.eleme;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eleme.HomeFragment1.HomeFragment1;
import com.example.eleme.fragments.MeFragment;
import com.example.eleme.fragments.OrdersFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainPage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Context c=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        // 创建一个要放入 Activity 布局中的新 Fragment,将该 Fragment 添加到“content” FrameLayout 中
        final Context c=this;
        HomeFragment1 fragment = new HomeFragment1();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_content, fragment).commit();

        bottomNavigationView=findViewById(R.id.main_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home1:
                        HomeFragment1 fragment = new HomeFragment1();
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_content, fragment).commit();
                        break;
                    case R.id.order:
                        OrdersFragment oragment = new OrdersFragment(c);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_content, oragment).commit();
                        break;
                    case R.id.person:
                        MeFragment mfragment = new MeFragment(c);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_content, mfragment).commit();
                        break;
                }
                return true;
            }
        });
    }
}