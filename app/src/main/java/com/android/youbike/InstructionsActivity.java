package com.android.youbike;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InstructionsActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), navigationActivity.class));
            }
        });

        findViewById(R.id.cl1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),EquipmentIntroductionActivity.class));
            }
        });


        findViewById(R.id.cl2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LeaseModeActivity.class));
            }
        });


        findViewById(R.id.cl3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),FareDescriptionActivity.class));
            }
        });


        findViewById(R.id.cl4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SafeRideActivity.class));
            }
        });
    }
}