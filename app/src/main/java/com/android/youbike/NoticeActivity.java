package com.android.youbike;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NoticeActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        findViewById(R.id.cl3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AccidentNotificationFixActivity.class));
            }
        });


        findViewById(R.id.cl4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AccidentNotificationRecordActivity.class));
            }
        });
    }
}