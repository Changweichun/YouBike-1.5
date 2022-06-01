package com.android.youbike;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class BikeReportSysActivity extends MyBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_reportsys);

        ArrayList<String> titleDatas=new ArrayList<>();
        titleDatas.add("自行車");
        titleDatas.add("車柱");
        titleDatas.add("其他");
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new Fragment_bike_report());
        fragmentList.add(new Fragment_poll_report());
        fragmentList.add(new Fragment_other_report());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas,fragmentList);
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tableLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(myViewPageAdapter);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setTabsFromPagerAdapter(myViewPageAdapter);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }




}
