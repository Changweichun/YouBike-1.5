package com.android.youbike;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class BikeReportSysActivity extends MyBaseActivity {
    private Values values;

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


        values = (Values) getApplication();
        String account = values.getAccount();
        if (account == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(BikeReportSysActivity.this);
            View view1 = LayoutInflater.from(BikeReportSysActivity.this).inflate(R.layout.error_dialog, null);
            TextView title = view1.findViewById(R.id.error_title);
            title.setText("請先登入");

            builder.setView(view1).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setCancelable(false).show();
        }
    }

    public void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
