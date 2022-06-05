package com.android.youbike;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youbike.adapter.FixAdapter;
import com.android.youbike.entity.FixBean;
import com.android.youbike.entity.FixBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class AccidentNotificationFixDetailActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_info);

        FixBean recordBean = (FixBean) getIntent().getSerializableExtra("data");
        ((TextView)findViewById(R.id.tv1)).setText("通報案號: "+recordBean.getTitle());
        ((TextView)findViewById(R.id.tv2)).setText("通報種類："+recordBean.getType());
        ((TextView)findViewById(R.id.tv3)).setText("通報時間："+recordBean.getTime());
        ((TextView)findViewById(R.id.tv4)).setText("所在地區："+recordBean.getArea());
        ((TextView)findViewById(R.id.tv5)).setText("車柱柱號："+recordBean.getNumber());
        ((TextView)findViewById(R.id.tv6)).setText("自行車編號："+recordBean.getNumber2());
        ((TextView)findViewById(R.id.tv7)).setText("維修項目："+recordBean.getProject());
        ((TextView)findViewById(R.id.tv8)).setText("備註："+recordBean.getRemark());

    }
}
