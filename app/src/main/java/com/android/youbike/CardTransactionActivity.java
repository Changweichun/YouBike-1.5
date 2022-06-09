package com.android.youbike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CardTransactionActivity extends AppCompatActivity {
    private TextView card;
    private Values values;
    private Context context = this;
    private String cookieStr;
    private String url = "http://team8.byethost6.com/";
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_transaction);

        values = (Values) getApplication();
        cookieStr = values.getCookieStr();

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CardManageActivity.class));
            }

        });

        Bundle bundle = getIntent().getExtras();
        String cardnum = bundle.getString("card");
        card = (TextView)findViewById(R.id.Trancard);
        card.setText(cardnum);


    }
}