package com.example.tung.androidproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.tung.androidproject.R;
import com.example.tung.androidproject.fragment.DiachiFragment;

public class MuahangActivity extends AppCompatActivity {
    ImageView btnBack;

    public Fragment fragment;
    public FragmentTransaction transaction;

    String tongtien;
    String tennguoinhan;
    String sodtnguoinhan;
    String diachinguoinhan;

    public String getTongtien() {
        return tongtien;
    }

    public String getTennguoinhan() {
        return tennguoinhan;
    }

    public void setTennguoinhan(String tennguoinhan) {
        this.tennguoinhan = tennguoinhan;
    }

    public String getSodtnguoinhan() {
        return sodtnguoinhan;
    }

    public void setSodtnguoinhan(String sodtnguoinhan) {
        this.sodtnguoinhan = sodtnguoinhan;
    }

    public String getDiachinguoinhan() {
        return diachinguoinhan;
    }

    public void setDiachinguoinhan(String diachinguoinhan) {
        this.diachinguoinhan = diachinguoinhan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muahang);

        initView();

        initData();

        initEvent();
    }

    private void initEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.leftin, R.anim.rightout);
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        tongtien = intent.getStringExtra("tongtien");
    }

    private void initView() {
        btnBack = findViewById(R.id.btn_back);

        fragment = new DiachiFragment();
        loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment)
    {
        // load fragment
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
