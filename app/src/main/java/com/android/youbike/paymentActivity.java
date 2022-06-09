package com.android.youbike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.youbike.entity.LostAndFoundList;
import com.android.youbike.entity.PaymentList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class paymentActivity extends AppCompatActivity {
    private Values values;
    private Context context = this;
    private String cookieStr;
    private String url = "http://team8.byethost6.com/";
    private String account;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),navigationActivity.class));
            }
        });

        values = (Values) getApplication();
        cookieStr = values.getCookieStr();
        account = values.getAccount();

        if (account == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(paymentActivity.this);
            View view1 = LayoutInflater.from(paymentActivity.this).inflate(R.layout.error_dialog, null);
            TextView title = view1.findViewById(R.id.error_title);
            title.setText("請先登入");

            builder.setView(view1).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setCancelable(false).show();
        }else {

            if (cookieStr != null) {
                select(account);
            }

        }

    }
    private void select(String id) {
        try {
            String r = PaymentPhp.paymentPhp(id, cookieStr, url);
            Log.e("error", r);
            if ((!r.trim().equals("null")) || (!r.equals("錯誤"))) {
                JSONArray jsonArray = new JSONArray(r);
                ArrayList<PaymentList> list = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    list.add(new PaymentList(jsonData.getString("id"),
                            jsonData.getString("start_time"),
                            jsonData.getString("end_time"),
                            jsonData.getString("bike_num"),
                            jsonData.getString("start_site"),
                            jsonData.getString("end_site"),
                            jsonData.getString("money")));
                }
                PaymentAdapter adapter = new PaymentAdapter(context, list);
                recyclerView = (RecyclerView) findViewById(R.id.paymentRecycle);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
                AlertDialog.Builder builder = new AlertDialog.Builder(paymentActivity.this);
                View view = LayoutInflater.from(paymentActivity.this).inflate(R.layout.notification_dialog, null);
                TextView msg = view.findViewById(R.id.notification_msg);
                msg.setText("如需補繳款項，請準備需補繳的卡片\n至各站點車柱進行刷卡補繳");
                builder.setView(view).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false).show();
            }
        } catch (Exception e) {
            Log.e("log_tag=", e.toString());
        }
    }

}