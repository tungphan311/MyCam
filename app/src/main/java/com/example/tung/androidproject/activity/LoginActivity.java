package com.example.tung.androidproject.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tung.androidproject.R;
import com.example.tung.androidproject.adapter.ViewPagerAdapter;

public class LoginActivity extends AppCompatActivity {
    private ImageView btnThoat;

    private ViewPager viewPager;
    private TabLayout tabLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initView();

        initEvent();

        initData();
    }

    private void initData() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initEvent() {
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                Intent intent = new Intent(LoginActivity.this, MainScreen.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        btnThoat = findViewById(R.id.btn_thoat);
        viewPager = findViewById(R.id.vp_view_pager);
        tabLayout = findViewById(R.id.tb_tab_layout);
    }
}
