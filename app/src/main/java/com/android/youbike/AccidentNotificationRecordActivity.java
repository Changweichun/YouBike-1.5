package com.android.youbike;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import com.android.youbike.adapter.AmountAdapter;
import com.android.youbike.entity.RecordBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AccidentNotificationRecordActivity extends MyBaseActivity {
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
        setContentView(R.layout.activity_record_list);

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
            AlertDialog.Builder builder = new AlertDialog.Builder(AccidentNotificationRecordActivity.this);
            View view1 = LayoutInflater.from(AccidentNotificationRecordActivity.this).inflate(R.layout.error_dialog, null);
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

        /*List<RecordBean> list = new ArrayList<>();
        list.add(new RecordBean("案件一","行車意外","我在路上擦撞到路邊車輛","2022/3/28",
                "2022/3/28  13：16","台北市中正區市民大道三段8號","0988767431", "123456789"));
        list.add(new RecordBean("案件二","行車意外","被後方來車追撞，已經去往醫院", "2022/4/1",
                "2022/4/2  07：35","新北市新莊區文山路35巷2號","0912388530", "789456123"));
        list.add(new RecordBean("案件三","行車意外","被路上的坑洞絆倒，車頭歪斜", "2022/4/5",
                "2022/4/5  22：10","台北市信義區廣和路39號","0989760325", "741852963"));
        list.add(new RecordBean("案件四","行車意外","被路邊停的汽車開門撞倒", "2022/4/8",
                "2022/4/10  12：47","新北市板橋區國慶路134巷17號","0988767431", "963852741"));
        list.add(new RecordBean("案件五","行車意外","與機車發生擦撞", "2022/4/18",
                "2022/4/18  19：39","台北市萬華區龍門路213巷15弄7號","0988643675", "753951852"));
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
        });*/
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
            String r = AccidentNotificationRecordPhp.AccidentNotificationRecordPhp(id, cookieStr, url);
            if ((r.substring(0,2).equals("錯誤")) || (r.trim().equals("null")) ) {
                RecyclerView recyclerView = findViewById(R.id.recycle_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(null);
            }else{
                JSONArray jsonArray = new JSONArray(r);
                ArrayList<RecordBean> list = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    list.add(new RecordBean(jsonData.getString("id"),
                            jsonData.getString("category"),
                            jsonData.getString("detail"),
                            jsonData.getString("date"),
                            jsonData.getString("notification_time"),
                            jsonData.getString("address"),
                            jsonData.getString("name"),
                            jsonData.getString("phone"),
                            jsonData.getString("card_num")));
                }
                AmountAdapter resTypeAdapter = new AmountAdapter(list);
                RecyclerView recyclerView = findViewById(R.id.recycle_view);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(resTypeAdapter);

                resTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                        //
                        AlertDialog alertDialog = new AlertDialog.Builder(AccidentNotificationRecordActivity.this).create();
                        View view1 = LayoutInflater.from(AccidentNotificationRecordActivity.this).inflate(R.layout.activity_record_info, null);
                        alertDialog.setView(view1);
                        RecordBean list1 = list.get(position);
                        TextView tv1 = view1.findViewById(R.id.tv1);
                        tv1.setText("通報案號："+list1.getTitle());
                        TextView tv2 = view1.findViewById(R.id.tv2);
                        tv2.setText("通報類型："+list1.getType());
                        TextView tv3 = view1.findViewById(R.id.tv3);
                        tv3.setText("通報內容："+list1.getContent());
                        TextView tv4 = view1.findViewById(R.id.tv4);
                        tv4.setText("發生日期："+list1.getDate());
                        TextView tv5 = view1.findViewById(R.id.tv5);
                        tv5.setText("通報時間："+list1.getTime());
                        TextView tv6 = view1.findViewById(R.id.tv6);
                        tv6.setText("通報地點："+list1.getAddress());
                        TextView tv7 = view1.findViewById(R.id.tv7);
                        tv7.setText("連絡電話：");
                        TextView tv8 = view1.findViewById(R.id.tv8);
                        tv8.setText("卡片代碼："+list1.getCard());
                        TextView tv9 = view1.findViewById(R.id.tv9);
                        tv9.setText("通報人姓名："+list1.getName());
                        TextView phone = view1.findViewById(R.id.phone);
                        phone.setText(list1.getPhone());
                        phone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                Uri uri = Uri.parse(String.format("tel:"+ phone.getText().toString()));
                                intent.setData(uri);
                                startActivity(intent);
                            }
                        });
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
                                AccidentNotificationRecordFinishPhp.accidentNotificationRecordFinishPhp(list1.getTitle(), account, cookieStr, url);
                                select(null);
                                alertDialog.dismiss();
                            }
                        });
                    }
                });
            }
        } catch (Exception e) {
            Log.e("log_tag=", e.toString());
        }
    }

}
