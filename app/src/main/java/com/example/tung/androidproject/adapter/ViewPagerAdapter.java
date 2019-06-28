package com.example.tung.androidproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tung.androidproject.fragment.SignInFragment;
import com.example.tung.androidproject.fragment.SignUpFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {super(fm);}

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new SignInFragment();
                break;

            case 1:
                fragment = new SignUpFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";

        switch (position) {
            case 0:
                title = "Đăng nhập";
                break;

            case 1:
                title = "Đăng ký";
                break;
        }
        return title;
    }
}
