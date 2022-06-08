package com.android.youbike;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youbike.adapter.AmountAdapter;
import com.android.youbike.entity.RecordBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.android.youbike.entity.FindBikeList;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FindBikeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Values values;
    private Context context = this;
    private String cookieStr;
    private CookieManager cookieManager;
    private String url = "http://team8.byethost6.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_bike);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), navigationActivity.class));
            }
        });

        values = (Values) getApplication();
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
            String r = FindBikePhp.findBikePhp(id, cookieStr, url);
            if ((!r.trim().equals("null")) || (!r.equals("錯誤"))) {
                JSONArray jsonArray = new JSONArray(r);
                ArrayList<FindBikeList> list = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    list.add(new FindBikeList(jsonData.getString("id"),
                            jsonData.getString("bike_num"),
                            jsonData.getString("phone")));
                }
                FindBikeAdapter adapter = new FindBikeAdapter(context, list);
                recyclerView = (RecyclerView) findViewById(R.id.recycle);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            Log.e("log_tag=", e.toString());
        }
    }
}