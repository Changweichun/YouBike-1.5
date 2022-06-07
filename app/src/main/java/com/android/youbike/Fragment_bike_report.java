package com.android.youbike;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Date;


public class Fragment_bike_report extends Fragment {
    private String[] items = new String[12];
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
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_bike_report,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CheckBox front_wheel = (CheckBox)getActivity().findViewById(R.id.front_wheel);
        CheckBox back_wheel = (CheckBox)getActivity().findViewById(R.id.back_wheel);
        CheckBox front_light = (CheckBox)getActivity().findViewById(R.id.front_light);
        CheckBox back_light = (CheckBox)getActivity().findViewById(R.id.back_light);
        CheckBox bell = (CheckBox)getActivity().findViewById(R.id.bell);
        CheckBox breaker = (CheckBox)getActivity().findViewById(R.id.breaker);
        CheckBox cushion = (CheckBox)getActivity().findViewById(R.id.cushion);
        CheckBox transmission = (CheckBox)getActivity().findViewById(R.id.transmission);
        CheckBox basket = (CheckBox)getActivity().findViewById(R.id.basket);
        CheckBox wheelchain = (CheckBox)getActivity().findViewById(R.id.wheelchain);
        CheckBox vehicle = (CheckBox)getActivity().findViewById(R.id.vehicle);
        CheckBox others = (CheckBox)getActivity().findViewById(R.id.others);
        EditText poll = (EditText)getActivity().findViewById(R.id.poll);
        EditText bikenumber = (EditText)getActivity().findViewById(R.id.bikenumber);
        Spinner location = (Spinner) getActivity().findViewById(R.id.locationspinner);
        TextView repairnotes = (TextView)getActivity().findViewById(R.id.repairnotes);

        values = (Values)getActivity().getApplication();
        cookieStr = values.getCookieStr();
        account = values.getAccount();

        front_wheel.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (front_wheel.isChecked())
                items[0] = "前輪";
            else
                items[0] = null;
        });
        back_wheel.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (back_wheel.isChecked())
                items[1] = "後輪";
            else
                items[1] = null;
        });
        front_light.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (front_light.isChecked())
                items[2] = "前燈";
            else
                items[2] = null;
        });
        back_light.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (back_light.isChecked())
                items[3] = "後燈";
            else
                items[3] = null;
        });
        bell.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (bell.isChecked())
                items[4] = "鈴鐺";
            else
                items[4] = null;
        });
        breaker.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (breaker.isChecked())
                items[5] = "煞車";
            else
                items[5] = null;
        });
        cushion.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (cushion.isChecked())
                items[6] = "坐墊";
            else
                items[6] = null;
        });
        transmission.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (transmission.isChecked())
                items[7] = "變速器";
            else
                items[7] = null;
        });
        basket.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (basket.isChecked())
                items[8] = "置物籃";
            else
                items[8] = null;
        });
        wheelchain.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (wheelchain.isChecked())
                items[9] = "鍊條";
            else
                items[9] = null;
        });
        vehicle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (vehicle.isChecked())
                items[10] = "車機";
            else
                items[10] = null;
        });
        others.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (others.isChecked())
                items[11] = "其他";
            else
                items[11] = null;
        });

        Button send = (Button) getActivity().findViewById(R.id.bikeReportSend);
        send.setOnClickListener(v -> {
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
                    front_wheel.setChecked(false);
                    back_wheel.setChecked(false);
                    front_light.setChecked(false);
                    back_light.setChecked(false);
                    bell.setChecked(false);
                    breaker.setChecked(false);
                    cushion.setChecked(false);
                    transmission.setChecked(false);
                    basket.setChecked(false);
                    wheelchain.setChecked(false);
                    vehicle.setChecked(false);
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