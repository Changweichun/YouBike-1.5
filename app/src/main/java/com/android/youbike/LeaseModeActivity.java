package com.android.youbike;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class LeaseModeActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease_mode);

        ArrayList<String> titleDatas=new ArrayList<>();
        titleDatas.add("靠卡借車");
        titleDatas.add("掃碼借車");
        ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new LeaseModeOneFragment());
        fragmentList.add(new LeaseModeTwoFragment());
        MyViewPageAdapter myViewPageAdapter = new MyViewPageAdapter(getSupportFragmentManager(), titleDatas,fragmentList);
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tableLayout = findViewById(R.id.tabLayout);
        viewPager.setAdapter(myViewPageAdapter);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setTabsFromPagerAdapter(myViewPageAdapter);

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}