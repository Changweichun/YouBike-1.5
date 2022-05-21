package com.android.youbike;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.StringUtils;

import java.text.ParseException;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends MyBaseActivity{
    String url = "http://team8.byethost6.com/";
    CookieString cookieString;
    CookieManager cookieManager;
    public String cookieStr;
    WebView webView;
    Context context = this;
    private EditText etPhone, etID, etBD, etPW, etCPW, etEmail;
    private TextView tvPhone, tvID, tvBD, tvPW, tvCPW;
    private Button signUpBtn;
    int year, month, day;
    Calendar calendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedClosableObjects().detectLeakedSqlLiteObjects().penaltyLog().build());

        cookieString = (CookieString)getApplication();
        cookieStr = cookieString.getCookieStr();

        if (cookieStr == null)
            Wcookie(context);

        etPhone = (EditText) findViewById(R.id.et_ph);
        etID = (EditText) findViewById(R.id.et_id);
        etPW = (EditText) findViewById(R.id.et_pw);
        etCPW = (EditText) findViewById(R.id.et_cpw);
        etEmail = (EditText) findViewById(R.id.et_em);
        etBD = (EditText) findViewById(R.id.et_bd);
        signUpBtn = (Button)findViewById(R.id.signup_button);
        tvPhone = (TextView)findViewById(R.id.tv_phrs);
        tvID = (TextView)findViewById(R.id.tv_idrs);
        tvBD = (TextView)findViewById(R.id.tv_bdrs);
        tvPW = (TextView)findViewById(R.id.tv_pwrs);
        tvCPW = (TextView)findViewById(R.id.tv_cpwrs);

        etBD.setInputType(InputType.TYPE_NULL);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        etBD.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    showDialog(777);
            }
        });
        etBD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(777);
            }
        });


        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPhone.setText("");
                tvBD.setText("");
                tvID.setText("");
                tvPW.setText("");
                tvCPW.setText("");
                int flag = 0;
                if (etPhone.getText().toString().trim().length()==0) {
                    flag = 1;
                    tvPhone.setText("必填");
                }
                else if (etPhone.getText().toString().trim().length() != 10){
                    flag = 1;
                    tvPhone.setText("手機格式不正確");
                }

                if (etID.getText().toString().length() == 0) {
                    flag = 1;
                    tvID.setText("必填");
                }else if (etID.getText().toString().length() != 10){
                    flag = 1;
                    tvID.setText("身分證格式不正確");
                }else if (!etID.getText().toString().matches("^[a-zA-Z]{1}[1-2]{1}[0-9]{8}$")) {
                    flag = 1;
                    tvID.setText("身分證格式不正確");
                }

                if (etBD.getText().toString().length() == 0){
                    flag = 1;
                    tvBD.setText("必填");
                }else{
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String BD = etBD.getText().toString();
                    try {
                        Date BDdate = sdf.parse(BD);
                        Date date = sdf.parse(timeStamp);
                        if (BDdate.compareTo(date) >= 0){
                            flag = 1;
                            tvBD.setText("生日必須為今日之前");
                        }
                    } catch (ParseException e) {
                        System.out.print("錯誤訊息: "+e.toString());
                    }

                }

                if (etPW.getText().toString().length() == 0 || etCPW.getText().toString().length() ==0){
                    if(etPW.getText().toString().length()==0){
                        flag = 1;
                        tvPW.setText("必填");
                    }
                    if (etCPW.getText().toString().length()==0){
                        flag = 1;
                        tvCPW.setText("必填");
                    }
                }else if(!etPW.getText().toString().equals(etCPW.getText().toString())){
                    flag = 1;
                    tvCPW.setText("密碼不相同");
                }

                if (flag == 0){
                    String r = SignUpCheckPhp.SignUpCheck(etPhone.getText().toString().trim(), cookieStr, url);
                    if (r.equals(etPhone.getText().toString().trim()))
                        tvPhone.setText("帳號已存在");
                    else{
                        String[] value = new String[]{etPhone.getText().toString().trim(), etID.getText().toString().trim(), etBD.getText().toString(), etPW.getText().toString().trim(), etEmail.getText().toString().trim()};
                        String result = SignUpPhp.signUp(value, cookieStr, url);
                        if (result.equals("成功")){
                            Intent intent= new Intent(SignUpActivity.this, MainActivity.class);
                            Toast.makeText(context, "註冊成功!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    }
                }
            }
        });

    }
    protected Dialog onCreateDialog(int id)
    {
        if(id == 777)
        {
            return new DatePickerDialog(SignUpActivity.this, datelistener, year, month, day);
        }
        return null;
    }

    DatePickerDialog.OnDateSetListener datelistener= new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            displayDate(i, i1+1, i2);

        }
    };

    private void displayDate(int year, int month, int day) {
        String str;
        str = Integer.toString(year) + '-' + Integer.toString(month) + '-' + Integer.toString(day);
        etBD.setText(str);
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
}
