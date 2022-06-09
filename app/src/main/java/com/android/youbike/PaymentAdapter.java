package com.android.youbike;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youbike.adapter.ClickListener;
import com.android.youbike.entity.LostAndFoundList;
import com.android.youbike.entity.PaymentList;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private ArrayList<PaymentList> items;



    public PaymentAdapter(Context context, ArrayList<PaymentList> items) {
        this.context=context;
        this.items=items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View items= LayoutInflater.from(context).inflate(R.layout.payment_adapter, null);
        RecyclerView.ViewHolder holder= new MyHolder(items);
        items.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder hold = (MyHolder) holder;
        PaymentList data = items.get(position);
        hold.id.setText(data.getNum());
        hold.start_time.setText(data.getStart_time());
        hold.end_time.setText(data.getEnd_time());
        hold.bike_num.setText(data.getBike_num());
        hold.start_site.setText(data.getStart_site());
        hold.end_site.setText(data.getEnd_site());
        hold.money.setText(data.getMoney());
        hold.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return items == null? 0:items.size();
    }

    @Override
    public void onClick(View view) {

    }


    private class MyHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView start_time;
        private TextView end_time;
        private TextView bike_num;
        private TextView start_site;
        private TextView end_site;
        private TextView money;

        public MyHolder(View itemView) {
            super(itemView);
            id= itemView.findViewById(R.id.card_num);
            start_time = itemView.findViewById(R.id.startTime);
            end_time=itemView.findViewById(R.id.endTime);
            bike_num=itemView.findViewById(R.id.bikeNum);
            start_site = itemView.findViewById(R.id.startSite);
            end_site=itemView.findViewById(R.id.endSite);
            money = itemView.findViewById(R.id.money);

        }
    }
}

