package com.android.youbike;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CardNewTwoActivity extends AppCompatActivity {
    private Values values;
    private Context context = this;
    private String cookieStr;
    private String url = "http://team8.byethost6.com/";
    private String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_new_two);

        values = (Values) getApplication();
        cookieStr = values.getCookieStr();
        account = values.getAccount();

        findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),CardNewActivity.class));
            }
        });

        findViewById(R.id.bt3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText card = (EditText)findViewById(R.id.easyCard);
                TextView error = (TextView) findViewById(R.id.easycard_tv);
                error.setText("");
                if(card.getText().toString().trim().length()==0)
                    error.setText("必填");
                else if(!card.getText().toString().trim().matches("^[0-9]{10}") && !card.getText().toString().trim().matches("^[0-9]{16}"))
                    error.setText("格式錯誤");
                else {
                    String r =CardNewPhp.cardNewPhp(card.getText().toString().trim(), account, cookieStr, url);
                    if(r.equals("新增成功")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(CardNewTwoActivity.this);
                        View view1 = LayoutInflater.from(CardNewTwoActivity.this).inflate(R.layout.success_dialog, null);
                        TextView title = view1.findViewById(R.id.success_title);
                        title.setText(r);
                        card.setText("");
                        builder.setView(view1).setPositiveButton("確定", (dialog, which) -> {

                        }).setCancelable(false).show();
                        startActivity(new Intent(getApplicationContext(),CardManageActivity.class));
                    }else if(r.equals("該卡片已被綁定")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(CardNewTwoActivity.this);
                        View view1 = LayoutInflater.from(CardNewTwoActivity.this).inflate(R.layout.error_dialog, null);
                        TextView title = view1.findViewById(R.id.error_title);
                        title.setText(r);
                        builder.setView(view1).setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).setCancelable(false).show();
                    }
                }

            }
        });
    }
}