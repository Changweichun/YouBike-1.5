package com.android.youbike;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends MyBaseActivity {
    private Values values;
    private Context context=this;
    private WebView webView;
    private String url = "http://team8.byethost6.com/";
    private CookieManager cookieManager;
    private String cookieStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ServiceCenterActivity.class));
            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),InstructionsActivity.class));
            }
        });

        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        findViewById(R.id.bt4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),paymentActivity.class));
            }
        });

        findViewById(R.id.bt5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),navigationActivity.class));
            }
        });

        findViewById(R.id.bt6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),NoticeActivity.class));
            }
        });

        findViewById(R.id.bt7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),BikeReportSysActivity.class));
            }
        });

        findViewById(R.id.bt8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CardNewTwoActivity.class));
            }
        });





        values =(Values)getApplication();
        cookieStr = values.getCookieStr();
        if(cookieStr==null)
            Wcookie(context);
    }
    @SuppressLint("SetJavaScriptEnabled")
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
                values.setCookieStr(cookieStr);
            }
        });
        webView.loadUrl(url);
        webView.clearCache(true);
        webView.clearHistory();

        cookieManager.removeAllCookie();
        cookieManager.removeSessionCookie();
    }
}