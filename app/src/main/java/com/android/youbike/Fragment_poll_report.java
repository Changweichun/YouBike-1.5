package com.android.youbike;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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

public class Fragment_poll_report extends Fragment {
    private String[] items = new String[5];
    private String error = "";
    private String items_str = "";
    private Values values;
    private String cookieStr;
    private String url = "http://team8.byethost6.com/";
    private String account;
    private Boolean success;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_poll_report,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CheckBox poll_uppercap = (CheckBox)getActivity().findViewById(R.id.poll_uppercap);
        CheckBox shaking = (CheckBox)getActivity().findViewById(R.id.shaking);
        CheckBox lockbroke = (CheckBox)getActivity().findViewById(R.id.lockbroke);
        CheckBox LOCATIONPILLAR = (CheckBox)getActivity().findViewById(R.id.LOCATIONPILLAR);
        CheckBox others = (CheckBox)getActivity().findViewById(R.id.poll_others);
        EditText poll = (EditText)getActivity().findViewById(R.id.et_poll);
        Spinner location = (Spinner) getActivity().findViewById(R.id.poll_locationspinner);
        TextView repairnotes = (TextView)getActivity().findViewById(R.id.poll_repairnotes);

        values = (Values)getActivity().getApplication();
        cookieStr = values.getCookieStr();
        account = values.getAccount();

        poll_uppercap.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (poll_uppercap.isChecked())
                items[0] = "車柱上蓋";
            else
                items[0] = null;
        });
        shaking.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (shaking.isChecked())
                items[1] = "車柱搖晃";
            else
                items[1] = null;
        });
        lockbroke.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (lockbroke.isChecked())
                items[2] = "鎖體鬆脫";
            else
                items[2] = null;
        });
        LOCATIONPILLAR.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (LOCATIONPILLAR.isChecked())
                items[3] = "導引座";
            else
                items[3] = null;
        });
        others.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (others.isChecked())
                items[4] = "其他";
            else
                items[4] = null;
        });

        Button send = (Button) getActivity().findViewById(R.id.pollReportSend);
        send.setOnClickListener(v -> {
            error="";
            items_str="";
            success = false;

            if(poll.getText().toString().trim().equals(""))
                error = "停車柱編號不可為空\n";

            for (String item : items) {
                if (item != null)
                    items_str = items_str + item + "、";
            }
            if (items_str.equals(""))
                error = error + "維修項目至少勾選一項\n";
            else
                items_str = items_str.substring(0, items_str.length()-1);

            if(error.equals("")) {
                String r = BikeReportPollCheckPhp.bikeReportPollCheckPhp(poll.getText().toString().trim(), cookieStr, url);
                switch (r) {
                    case "請輸入正確的車柱號碼":
                        error = r;
                        break;
                    case "正確":
                        success = true;
                        break;
                }
            }
            if (success){
                @SuppressLint("SimpleDateFormat") String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                String[] values = new String[]{account, time, location.getSelectedItem().toString(), poll.getText().toString(), "null", repairnotes.getText().toString(), "車柱", items_str};
                String r = BikeReportPhp.bikeReportPhp(values, cookieStr, url);
                if (r.equals("成功")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.success_dialog, null);
                    TextView title = view1.findViewById(R.id.success_title);
                    title.setText("通報成功");

                    location.setSelection(0);
                    poll.setText("");
                    poll_uppercap.setChecked(false);
                    shaking.setChecked(false);
                    lockbroke.setChecked(false);
                    LOCATIONPILLAR.setChecked(false);
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
        });
    }
}