package com.android.youbike;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youbike.adapter.AmountAdapter;
import com.android.youbike.entity.RecordBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AccidentNotificationRecordActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        List<RecordBean> list = new ArrayList<>();
        list.add(new RecordBean("案件一","行車意外","我在路上擦撞到路邊車輛",
                "2022/3/28  13：16","台北市中正區市民大道三段8號","0988767431"));
        list.add(new RecordBean("案件二","行車意外","被後方來車追撞，已經去往醫院",
                "2022/4/2  07：35","新北市新莊區文山路35巷2號","0912388530"));
        list.add(new RecordBean("案件三","行車意外","被路上的坑洞絆倒，車頭歪斜",
                "2022/4/5  22：10","台北市信義區廣和路39號","0989760325"));
        list.add(new RecordBean("案件四","行車意外","被路邊停的汽車開門撞倒",
                "2022/4/10  12：47","新北市板橋區國慶路134巷17號","0988767431"));
        list.add(new RecordBean("案件五","行車意外","與機車發生擦撞",
                "2022/4/18  19：39","台北市萬華區龍門路213巷15弄7號","0988643675"));
        AmountAdapter resTypeAdapter = new AmountAdapter(list);
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(resTypeAdapter);

        resTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                //
                Intent intent = new Intent(getApplicationContext(),AccidentNotificationRecordDetailActivity.class);
                intent.putExtra("data",list.get(position));
                startActivity(intent);
            }
        });
    }
}
