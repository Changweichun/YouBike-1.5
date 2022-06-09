package com.android.youbike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.youbike.entity.FindBikeList;
import com.android.youbike.entity.LostAndFoundList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LostAndFoundActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Values values;
    private Context context = this;
    private String cookieStr;
    private CookieManager cookieManager;
    private String url = "http://team8.byethost6.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_and_found);
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
//        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(),LostAndFoundTypeActivity.class));
//            }
//        });
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
            String r = LostAndFoundPhp.lostAndFoundPhp(id, cookieStr, url);
            Log.e("error", r);
            if ((!r.trim().equals("null")) || (!r.equals("錯誤"))) {
                JSONArray jsonArray = new JSONArray(r);
                ArrayList<LostAndFoundList> list = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);
                    list.add(new LostAndFoundList(jsonData.getString("id"),
                            jsonData.getString("name"),
                            jsonData.getString("date"),
                            jsonData.getString("site_name"),
                            jsonData.getString("category")));
                }
                LostAndFoundAdapter adapter = new LostAndFoundAdapter(context, list);
                recyclerView = (RecyclerView) findViewById(R.id.lostRecycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }
        } catch (Exception e) {
            Log.e("log_tag=", e.toString());
        }
    }
}