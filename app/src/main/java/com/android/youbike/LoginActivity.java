package com.android.youbike;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class LoginActivity extends MyBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TermOfUseActivity.class));
            }
        });
    }
}