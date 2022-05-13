package com.android.youbike;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youbike.adapter.AmountAdapter;
import com.android.youbike.entity.RecordBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AccidentNotificationRecordDetailActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_info);

        RecordBean recordBean = (RecordBean) getIntent().getSerializableExtra("data");
        ((TextView)findViewById(R.id.tv1)).setText(recordBean.getTitle());
        ((TextView)findViewById(R.id.tv2)).setText("通報種類："+recordBean.getType());
        ((TextView)findViewById(R.id.tv3)).setText("通報內容："+recordBean.getContent());
        ((TextView)findViewById(R.id.tv4)).setText("通報時間："+recordBean.getTime());
        ((TextView)findViewById(R.id.tv5)).setText("通報地點："+recordBean.getAddress());
        ((TextView)findViewById(R.id.tv6)).setText("連絡電話："+recordBean.getTime());
    }
}
