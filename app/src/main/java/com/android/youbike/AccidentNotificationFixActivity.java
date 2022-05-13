package com.android.youbike;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youbike.adapter.FixAdapter;
import com.android.youbike.entity.FixBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AccidentNotificationFixActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_list);

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        List<FixBean> list = new ArrayList<>();
        list.add(new FixBean("案件一","自行車","台北市",
                "A8406","BI-479","後輪","後輪爆胎了"));
        list.add(new FixBean("案件二","自行車","台北市",
                "A7459","BI-219","坐墊","坐墊無法固定"));
        list.add(new FixBean("案件三","自行車","新北市",
                "A7215","BI-409","前輪","前輪沒有氣了"));
        list.add(new FixBean("案件四","自行車","台北市",
                "A6501","BI-148","坐墊","坐墊毀損"));
        list.add(new FixBean("案件五","自行車","新北市",
                "A3891","BI-749","鏈條","鏈條脫落"));
        FixAdapter resTypeAdapter = new FixAdapter(list);
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(resTypeAdapter);

        resTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //
                Intent intent = new Intent(getApplicationContext(),AccidentNotificationFixDetailActivity.class);
                intent.putExtra("data",list.get(position));
                startActivity(intent);
            }
        });
    }
}
