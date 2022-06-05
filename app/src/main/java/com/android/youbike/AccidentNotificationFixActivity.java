package com.android.youbike;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youbike.adapter.FixAdapter;
import com.android.youbike.entity.FixBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AccidentNotificationFixActivity extends MyBaseActivity {
    private Values values;
    private Context context = this;
    private String cookieStr;
    private CookieManager cookieManager;
    private String url = "http://team8.byethost6.com/";
    private String account;
    private int loginState;
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

        values = (Values) getApplication();
        account = values.getAccount();
        loginState = values.getLogin_state();
        if(loginState != 1){
            AlertDialog.Builder builder = new AlertDialog.Builder(AccidentNotificationFixActivity.this);
            View view1 = LayoutInflater.from(AccidentNotificationFixActivity.this).inflate(R.layout.error_dialog, null);
            TextView title = view1.findViewById(R.id.error_title);
            title.setText("請登入管理員帳號");

            builder.setView(view1).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            }).setCancelable(false).show();

        }
        else {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedClosableObjects().detectLeakedSqlLiteObjects().penaltyLog().build());

            cookieStr = values.getCookieStr();

            if (cookieStr != null) {
                select(null);
            } else {
                Wcookie(context);
                Handler myHandler = new Handler();
                myHandler.postDelayed(runTimerStop, 5000);
                if (cookieStr != null) {
                    myHandler.removeCallbacks(runTimerStop);
                }
            }
        }

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void Wcookie(Context context){
        WebView webView = new WebView(context);
        CookieSyncManager.createInstance(context);
        cookieManager= CookieManager.getInstance();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                cookieManager.setAcceptCookie(true);
                cookieStr=cookieManager.getCookie(url);
                values =(Values)getApplication();
                values.setCookieStr(cookieStr);
            }
        });
        webView.loadUrl(url);
        webView.clearCache(true);
        webView.clearHistory();

        cookieManager.removeAllCookie();
        cookieManager.removeSessionCookie();
    }

    private Runnable runTimerStop = new Runnable() {
        @Override
        public void run() {
            select(null);
        }
    };

    private void select(String id) {
        try {
            String r = AccidentNotificationFixPhp.AccidentNotificationFixPhp(id, cookieStr, url);
            if ((r.substring(0,2).equals("錯誤")) || (r.trim().equals("null")) ) {
                RecyclerView recyclerView = findViewById(R.id.recycle_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(null);
            }else{
                JSONArray jsonArray = new JSONArray(r);
                ArrayList<FixBean> list = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    list.add(new FixBean(jsonData.getString("id"),
                            jsonData.getString("time"),
                            jsonData.getString("category"),
                            jsonData.getString("area"),
                            jsonData.getString("bike_stand_num"),
                            jsonData.getString("bike_num"),
                            jsonData.getString("items"),
                            jsonData.getString("remark")));
                }
                FixAdapter resTypeAdapter = new FixAdapter(list);
                RecyclerView recyclerView = findViewById(R.id.recycle_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(resTypeAdapter);

                resTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                        //
                        AlertDialog alertDialog = new AlertDialog.Builder(AccidentNotificationFixActivity.this).create();
                        View view1 = LayoutInflater.from(AccidentNotificationFixActivity.this).inflate(R.layout.activity_fix_info, null);
                        alertDialog.setView(view1);
                        FixBean list1 = list.get(position);
                        TextView tv1 = view1.findViewById(R.id.tv1);
                        tv1.setText("通報案號："+list1.getTitle());
                        TextView tv2 = view1.findViewById(R.id.tv2);
                        tv2.setText("通報種類："+list1.getType());
                        TextView tv3 = view1.findViewById(R.id.tv3);
                        tv3.setText("通報時間："+list1.getTime());
                        TextView tv4 = view1.findViewById(R.id.tv4);
                        tv4.setText("所在地區："+list1.getArea());
                        TextView tv5 = view1.findViewById(R.id.tv5);
                        tv5.setText("車柱住號："+list1.getNumber());
                        TextView tv6 = view1.findViewById(R.id.tv6);
                        tv6.setText("自行車編號："+list1.getNumber2());
                        TextView tv7 = view1.findViewById(R.id.tv7);
                        tv7.setText("維修項目："+list1.getProject());
                        TextView tv8 = view1.findViewById(R.id.tv8);
                        tv8.setText("備註："+list1.getRemark());
                        alertDialog.setCancelable(false);
                        alertDialog.show();
                        Button cancel_btn = view1.findViewById(R.id.cancel_btn);
                        cancel_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                        Button finish_btn = view1.findViewById(R.id.finish_btn);
                        finish_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AccidentNotificationFixFinishPhp.accidentNotificationFixFinishPhp(list1.getTitle(), account, cookieStr, url);
                                select(null);
                                alertDialog.dismiss();
                            }
                        });

                        /*Intent intent = new Intent(getApplicationContext(), AccidentNotificationFixDetailActivity.class);
                        intent.putExtra("data", list.get(position));
                        startActivity(intent);*/
                    }
                });
            }
        } catch (Exception e) {
            Log.e("log_tag=", e.toString());
        }
    }



}
