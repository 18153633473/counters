package com.example.asus.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FrameActivity extends FragmentActivity {
    private Fragment mFragments[];
    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private RadioButton rbtHome,rbtFunc,rbtSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        mFragments=new Fragment[3];
        fragmentManager=getSupportFragmentManager();
        mFragments[0]=fragmentManager.findFragmentById(R.id.fragment_main);
        mFragments[1]=fragmentManager.findFragmentById(R.id.fragment_func);
        mFragments[2]=fragmentManager.findFragmentById(R.id.fragment_setting);
        fragmentTransaction=fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
        fragmentTransaction.show(mFragments[0]).commit();
        radioGroup=(RadioGroup)findViewById(R.id.bottomGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.i("radiogroup","checkid="+checkedId);
                fragmentTransaction=fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
                rbtHome.setBackgroundResource(R.drawable.shape);
                rbtFunc.setBackgroundResource(R.drawable.shape);
                rbtSetting.setBackgroundResource(R.drawable.shape);


                switch (checkedId){
                    case R.id.radioHome:
                        fragmentTransaction.show(mFragments[0]).commit();
                        rbtHome.setBackgroundResource(R.drawable.shape2);
                        break;
                    case R.id.radioFunc:
                        fragmentTransaction.show(mFragments[1]).commit();
                        rbtFunc.setBackgroundResource(R.drawable.shape2);
                        break;
                    case R.id.radioSetting:
                        fragmentTransaction.show(mFragments[2]).commit();
                        rbtSetting.setBackgroundResource(R.drawable.shape2);
                        break;
                    default:
                        break;
                }
            }
        });


    }
}
