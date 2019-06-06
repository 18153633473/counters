package com.example.asus.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPageAdapter extends FragmentPagerAdapter{
    private String[] title=new String[]{"猫","狗","牛"};

    public MyPageAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new FirstFragment();
        }else if (position==1){
            return new SecondFragment();
        }else{
            return new ThirdFragment();
        }
    }
    @Override
    public CharSequence getPageTitle(int position){
        return title[position];
    }
    @Override
    public int getCount() {
        return 3;
    }
}
