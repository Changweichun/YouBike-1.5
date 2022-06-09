package com.android.youbike;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.youbike.adapter.ClickListener;
import com.android.youbike.entity.CardList;
import com.android.youbike.entity.FindBikeList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardManageActivity extends AppCompatActivity {
    private Values values;
    private Context context = this;
    private String cookieStr;
    private String url = "http://team8.byethost6.com/";
    private String account;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage);


        values = (Values) getApplication();
        cookieStr = values.getCookieStr();
        account = values.getAccount();

        findViewById(R.id.newCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String r = CardCheckPhp.cardCheck(account, cookieStr, url);
                if (r.equals("可新增")){
                    startActivity(new Intent(getApplicationContext(),CardNewActivity.class));
                }else if(r.equals("錯誤")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(CardManageActivity.this);
                    View view1 = LayoutInflater.from(CardManageActivity.this).inflate(R.layout.error_dialog, null);
                    TextView title = view1.findViewById(R.id.error_title);
                    title.setText("您已綁定五張電子票券");

                    builder.setView(view1).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).setCancelable(false).show();
                }
            }
        });

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),navigationActivity.class));
            }
        });

        if (account == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(CardManageActivity.this);
            View view1 = LayoutInflater.from(CardManageActivity.this).inflate(R.layout.error_dialog, null);
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

            AlertDialog.Builder builder = new AlertDialog.Builder(CardManageActivity.this);
            View view1 = LayoutInflater.from(CardManageActivity.this).inflate(R.layout.notification_dialog, null);

            builder.setView(view1).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).setCancelable(false).show();
        }
    }


    private void select(String id) {
        try {
            String r = easyCardPhp.easyCardPhp(id, cookieStr, url);
            if ((r.substring(0,2).equals("錯誤")) || (r.trim().equals("null")) ) {
                RecyclerView recyclerView = findViewById(R.id.card_recycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(null);
            }else{
                JSONArray jsonArray = new JSONArray(r);
                ArrayList<CardList> list = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    list.add(new CardList(jsonData.getString("id"), jsonData.getString("cardType")));
                }
                CardManageAdapter adapter = new CardManageAdapter(context, list, new ClickListener() {
                    @Override
                    public void onSelectClicked(View v, int position, String id, String type) {
                        Intent intent = new Intent();
                        intent.setClass(CardManageActivity.this, CardTransactionActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("card", id);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }

                    @Override
                    public void onDeleteClicked(View v, int position, String id, String type) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(CardManageActivity.this);
                        View view1 = LayoutInflater.from(CardManageActivity.this).inflate(R.layout.notification_dialog, null);
                        TextView title = view1.findViewById(R.id.notification_title);
                        title.setText("刪除卡片");
                        TextView content = view1.findViewById(R.id.notification_msg);
                        content.setText("是否要刪除這張卡片?");
                        TextView cardNum = view1.findViewById(R.id.cardnum);
                        cardNum.setText(id);
                        builder.setView(view1).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CardsDeletePhp.cardsDeletePhp(id, account, type, cookieStr, url);
                                select(account);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setCancelable(false).show();
                    }
                });
                recyclerView = (RecyclerView) findViewById(R.id.card_recycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            Log.e("log_tag=", e.toString());
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 ) {
            select(account);

        }
    }
}