package com.android.youbike;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Fragment_other_report extends Fragment {
    private String[] items = new String[4];
    private String error = "";
    private String items_str = "";
    private Values values;
    private String cookieStr;
    private final String url = "http://team8.byethost6.com/";
    private String account;
    private Boolean success;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_other_report,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CheckBox cantrent = (CheckBox)getActivity().findViewById(R.id.cantrent);
        CheckBox cantreturn = (CheckBox)getActivity().findViewById(R.id.cantreturn);
        CheckBox cantpark = (CheckBox)getActivity().findViewById(R.id.cantpark);
        CheckBox others = (CheckBox)getActivity().findViewById(R.id.othersCheckbox);
        EditText poll = (EditText)getActivity().findViewById(R.id.others_poll);
        EditText bikenumber = (EditText)getActivity().findViewById(R.id.others_bikenumber);
        Spinner location = (Spinner) getActivity().findViewById(R.id.others_locationspinner);
        TextView repairnotes = (TextView)getActivity().findViewById(R.id.others_repairnotes);

        values = (Values)getActivity().getApplication();
        cookieStr = values.getCookieStr();
        account = values.getAccount();

        cantrent.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cantrent.isChecked())
                items[0] = "無法借車";
            else
                items[0] = null;
        });
        cantreturn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cantreturn.isChecked())
                items[1] = "無法還車";
            else
                items[1] = null;
        });
        cantpark.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cantpark.isChecked())
                items[2] = "無法臨停";
            else
                items[2] = null;
        });
        others.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (others.isChecked())
                items[3] = "其他";
            else
                items[3] = null;
        });



        Button send = (Button) getActivity().findViewById(R.id.otherReportSend);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                error="";
                items_str="";
                success = false;

                if(poll.getText().toString().trim().equals(""))
                    error = "停車柱編號不可為空\n";

                if(bikenumber.getText().toString().trim().equals(""))
                    error = error + "自行車車號不可為空\n";

                for (String item : items) {
                    if (item != null)
                        items_str = items_str + item + "、";
                }
                if (items_str.equals(""))
                    error = error + "維修項目至少勾選一項\n";
                else
                    items_str = items_str.substring(0, items_str.length()-1);

                if(error.equals("")) {
                    String r = BikeReportCheckPhp.bikeReportCheckPhp(poll.getText().toString().trim(), bikenumber.getText().toString().trim(), cookieStr, url);
                    switch (r) {
                        case "請輸入正確的車柱號碼":
                        case "請輸入正確的自行車車號":
                            error = r;
                            break;
                        case "錯誤":
                            error = "請輸入正確的車柱號碼\n請輸入正確的自行車車號";
                            break;
                        case "正確":
                            success = true;
                            break;
                    }
                }

                if (success){
                    @SuppressLint("SimpleDateFormat") String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    String[] values = new String[]{account, time, location.getSelectedItem().toString(), poll.getText().toString(), bikenumber.getText().toString(), repairnotes.getText().toString(), "自行車", items_str};
                    String r = BikeReportPhp.bikeReportPhp(values, cookieStr, url);
                    if (r.equals("成功")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.success_dialog, null);
                        TextView title = view1.findViewById(R.id.success_title);
                        title.setText("通報成功");

                        location.setSelection(0);
                        poll.setText("");
                        bikenumber.setText("");
                        cantrent.setChecked(false);
                        cantreturn.setChecked(false);
                        cantpark.setChecked(false);
                        others.setChecked(false);
                        repairnotes.setText("");
                        builder.setView(view1).setPositiveButton("確定", (dialog, which) -> {

                        }).setCancelable(false).show();
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.error_dialog, null);
                    TextView title = view1.findViewById(R.id.error_title);
                    title.setText("通報失敗");
                    TextView content = view1.findViewById(R.id.error_msg);
                    content.setText(error);
                    builder.setView(view1).setPositiveButton("確定", (dialog, which) -> {

                    }).setCancelable(false).show();
                }
            }
        });
    }
}