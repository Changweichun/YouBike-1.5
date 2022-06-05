package com.android.youbike;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends MyBaseActivity{
    private Values values;
    Context context = this;
    EditText etPhone, etPw;
    TextView rsTV1, rsTV2;
    Button login_button;
    WebView webView;
    String url = "http://team8.byethost6.com/";
    CookieManager cookieManager;
    String cookieStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.signup_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TermOfUseActivity.class));
            }
        });

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedClosableObjects().detectLeakedSqlLiteObjects().penaltyLog().build());

        values = (Values)getApplication();
        cookieStr = values.getCookieStr();
        if (cookieStr==null)
            Wcookie(context);

        etPhone = (EditText)findViewById(R.id.etPhone);
        etPw = (EditText)findViewById(R.id.etPw);
        rsTV1 = (TextView)findViewById(R.id.rsTV1);
        rsTV2 = (TextView)findViewById(R.id.rsTV2);
        login_button = (Button)findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rsTV1.setText("");
                rsTV2.setText("");

                if (etPhone.getText().toString().length() == 0 || etPw.getText().toString().length() == 0 || etPhone.getText().toString().length() != 10) {
                    if(etPhone.getText().toString().length() == 0) rsTV1.setText("必填");
                    else if(etPhone.getText().toString().length() != 10) rsTV1.setText("手機格式不正確");
                    if(etPw.getText().toString().length() == 0) rsTV2.setText("必填");

                }
                else{
                     String r = LOGINphp.DBstring(etPhone.getText().toString(), etPw.getText().toString(), cookieStr, url);
                     if (!r.equals("帳號或密碼錯誤")) {
                         if (r.charAt(10) == '0') {
                             values.setLogin_state(0);
                             values.setAccount(etPhone.getText().toString());
                             Intent intent = new Intent();
                             intent.setClass(getApplicationContext(), MainActivity.class);
                             Toast.makeText(getApplicationContext(), "登入成功!", Toast.LENGTH_SHORT).show();
                             startActivity(intent);
                             etPhone.setText("");
                             etPw.setText("");
                         }
                         else if(r.charAt(10) == '1'){
                             values.setLogin_state(1);
                             values.setAccount(etPhone.getText().toString());
                             Intent intent = new Intent();
                             intent.setClass(getApplicationContext(), MainActivity.class);
                             Toast.makeText(getApplicationContext(), "登入成功!", Toast.LENGTH_SHORT).show();
                             startActivity(intent);
                             etPhone.setText("");
                             etPw.setText("");
                         }
                     } else {
                         rsTV1.setText(r);
                         rsTV2.setText(r);

                     }
                }
            }
        });

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

}