package com.android.youbike;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ServiceCenterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mcontext;
    private OnItemClickListener mlistener;
    private ArrayList< ServiceCenter_list> items;
    String phone;
    public ServiceCenterAdapter(Context context, ArrayList< ServiceCenter_list> items, OnItemClickListener listener) {
        this.mcontext=context;
        this.items=items;
        this.mlistener = listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new MyHolder(LayoutInflater.from(mcontext).inflate(R.layout.acticity_service_center_adapter, parent, false));
        }
        else {
            return new MyHolder2(LayoutInflater.from(mcontext).inflate(R.layout.activity_service_center_adapter1, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (position % 2 ==0){
            MyHolder hold = (MyHolder) holder;
            ServiceCenter_list data = items.get(position);
            hold.Title.setText(data.getTitle());
            hold.Address.setText(data.getAddress());
            hold.Content.setText(data.getContent());
            hold.itemView.setTag(position);}
        else{
            MyHolder2 holder2 = (MyHolder2) holder;
            ServiceCenter_list data = items.get(position);
            holder2.Phone.setText(data.getPhone());
            holder2.itemView.setTag(position);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position % 2 != 0){
                    MyHolder2 holder2 = (MyHolder2) holder;
                    phone = holder2.Phone.getText().toString();
                    mlistener.onClick(position, phone);}
                /*else {
                    mlistener.onClick(position, null);
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null? 0:items.size();
    }

    @Override
    public void onClick(View view) {

    }
    public int getItemViewType(int position) {
        if (position % 2 ==0){
            return 0;
        }else {
            return 1;
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        private TextView Title, Address, Content;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.tv_serviceTitle);
            Address = (TextView) itemView.findViewById(R.id.tv_serviceAddress);
            Content = (TextView) itemView.findViewById(R.id.tv_serviceContent);
        }
    }

    private class MyHolder2 extends RecyclerView.ViewHolder{
        private TextView Phone;
        public MyHolder2(@NonNull View itemView) {
            super(itemView);
            Phone = (TextView) itemView.findViewById(R.id.tv_servicePhone);
        }
    }

    public interface OnItemClickListener{
        void onClick(int pos, String phone);
    }
}
