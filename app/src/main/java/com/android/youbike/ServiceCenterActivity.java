package com.android.youbike;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceCenterActivity extends MyBaseActivity {
    private CookieString cookieString;
    private RecyclerView recyclerView;
    Context context = this;
    String url = "http://team8.byethost6.com/";
    CookieManager cookieManager;
    public String cookieStr;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_center);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AccidentNotificationActivity.class));
            }
        });

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),navigationActivity.class));
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedClosableObjects().detectLeakedSqlLiteObjects().penaltyLog().build());

        cookieString = (CookieString)getApplication();
        cookieStr = cookieString.getCookieStr();
        if (cookieStr!=null){
            select(null);}
        else {
            Wcookie(context);

            Handler myHandler = new Handler();
            myHandler.postDelayed(runTimerStop, 5000);
            if (cookieStr != null) {
                myHandler.removeCallbacks(runTimerStop);
            }
        }

    }

    private void Wcookie(Context context){
        webView = new WebView(context);
        CookieSyncManager.createInstance(context);
        cookieManager=CookieManager.getInstance();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                super.onPageFinished(view, url);
                cookieManager.setAcceptCookie(true);
                cookieStr=cookieManager.getCookie(url);
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

    public void select(String id) {
        try {
            String r = ServiceCenterPhp.ServiceCenterPhp(id, cookieStr, url);
            if (!r.trim().equals("null")){
                JSONArray jsonArray = new JSONArray(r);
                ArrayList<ServiceCenter_list> items = new ArrayList<ServiceCenter_list>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    items.add(new ServiceCenter_list(jsonData.getString("name"), jsonData.getString("address"), jsonData.getString("detail"), jsonData.getString("phone")));
                }
                ServiceCenterAdapter SA = new ServiceCenterAdapter(context, items, new ServiceCenterAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int pos, String phone) {

                        if (pos % 2 != 0){
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri uri = Uri.parse(String.format("tel:%1$s", phone));
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }
                });
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(SA);
            }
        } catch (Exception e) {
            Log.e("log_tag=", e.toString());
        }
    }
}