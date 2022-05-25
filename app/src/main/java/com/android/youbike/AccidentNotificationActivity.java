package com.android.youbike;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AccidentNotificationActivity extends MyBaseActivity {
    String url = "http://team8.byethost6.com/";
    CookieString cookieString;
    CookieManager cookieManager;
    public String cookieStr;
    WebView webView;
    Context context = this;
    int year, month, day;
    Calendar calendar = Calendar.getInstance();
    private Spinner category;
    private EditText address, name, phone, date, cardNum, detail;
    private TextView tv_address, tv_name, tv_phone, tv_date, tv_cardNum, tv_detail;
    private Button btn;
    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident_notification);

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

        address = (EditText) findViewById(R.id.an_address);
        name = (EditText) findViewById(R.id.an_name);
        phone = (EditText) findViewById(R.id.an_phone);
        date = (EditText) findViewById(R.id.an_date);
        cardNum = (EditText) findViewById(R.id.an_cardNum);
        category = (Spinner) findViewById(R.id.an_category);
        detail = (EditText) findViewById(R.id.an_detail);
        btn = (Button) findViewById(R.id.an_btn);
        tv_address = (TextView)findViewById(R.id.tv_add);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_phone = (TextView)findViewById(R.id.tv_phone);
        tv_date = (TextView)findViewById(R.id.tv_date);
        tv_cardNum = (TextView)findViewById(R.id.tv_cardNum);
        tv_detail = (TextView)findViewById(R.id.tv_detail);

        date.setInputType(InputType.TYPE_NULL);
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b)
                    showDialog(777);
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(777);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                tv_address.setText("");
                tv_name.setText("");
                tv_phone.setText("");
                tv_date.setText("");
                tv_cardNum.setText("");
                tv_detail.setText("");
                if(address.getText().toString().trim().length() == 0){
                    flag = true;
                    tv_address.setText("必填");
                }
                if(name.getText().toString().trim().length() == 0){
                    flag = true;
                    tv_name.setText("必填");
                }
                if(phone.getText().toString().trim().length() == 0){
                    flag = true;
                    tv_phone.setText("必填");
                }else if(phone.getText().toString().trim().length() != 10){
                    flag = true;
                    tv_phone.setText("手機格式不正確");
                }
                if(date.getText().toString().trim().length() == 0){
                    flag = true;
                    tv_date.setText("必填");
                }else{
                    String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String Date = date.getText().toString();
                    try {
                        Date accDate = sdf.parse(Date);
                        Date date = sdf.parse(timeStamp);
                        if (accDate.compareTo(date) > 0){
                            flag = true;
                            tv_date.setText("意外發生日不能為今日之後");
                        }
                    } catch (ParseException e) {
                        System.out.print("錯誤訊息: "+e.toString());
                    }
                }
                if (cardNum.getText().toString().trim().length() == 0){
                    flag = true;
                    tv_cardNum.setText("必填");
                }
                if(flag == false){
                    String r = AccidentNotificationCheckPhp.AccidentNotificationCheckPhp(cardNum.getText().toString(), cookieStr, url);
                    if (cardNum.getText().toString().trim().equals(r)){
                        String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                        String[] values = new String[]{name.getText().toString().trim(), category.getSelectedItem().toString(), address.getText().toString().trim(), date.getText().toString(), phone.getText().toString().trim(), cardNum.getText().toString().trim(), detail.getText().toString().trim(), nowDate};
                        String result = AccidentNotificationPhp.AccidentNotification(values, cookieStr, url);
                        if (result.equals("成功")){
                            address.setText("");
                            name.setText("");
                            phone.setText("");
                            date.setText("");
                            cardNum.setText("");
                            detail.setText("");
                            Toast.makeText(AccidentNotificationActivity.this, "通報成功!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        tv_cardNum.setText(r);
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
                cookieString=(CookieString)getApplication();
                cookieString.setCookieStr(cookieStr);
            }
        });
        webView.loadUrl(url);
        webView.clearCache(true);
        webView.clearHistory();

        cookieManager.removeAllCookie();
        cookieManager.removeSessionCookie();
    }
    protected Dialog onCreateDialog(int id)
    {
        if(id == 777)
        {
            return new DatePickerDialog(AccidentNotificationActivity.this, datelistener, year, month, day);
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
        date.setText(str);
    }
}