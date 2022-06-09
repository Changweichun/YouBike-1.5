package com.android.youbike;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youbike.adapter.PhoneClickListener;

import java.util.ArrayList;

public class ServiceCenterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private Context mcontext;
    private PhoneClickListener listener;
    private ArrayList< ServiceCenter_list> items;

    public ServiceCenterAdapter(Context context, ArrayList< ServiceCenter_list> items, PhoneClickListener listener) {
        this.mcontext=context;
        this.items=items;
        this.listener=listener;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(mcontext).inflate(R.layout.acticity_service_center_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        MyHolder hold = (MyHolder) holder;
        ServiceCenter_list data = items.get(position);
        hold.Title.setText(data.getTitle());
        hold.Address.setText(data.getAddress());
        hold.Content.setText(data.getContent());
        hold.itemView.setTag(position);;
        hold.Phone.setText(data.getPhone());

    }

    @Override
    public int getItemCount() {
        return items == null? 0:items.size();
    }

    @Override
    public void onClick(View view) {

    }

    private class MyHolder extends RecyclerView.ViewHolder {
        private TextView Title, Address, Content, Phone;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.tv_serviceTitle);
            Address = (TextView) itemView.findViewById(R.id.tv_serviceAddress);
            Content = (TextView) itemView.findViewById(R.id.tv_serviceContent);
            Phone = (TextView) itemView.findViewById(R.id.tv_servicePhone);
            itemView.findViewById(R.id.phoneview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onOhoneClicked(itemView, getAdapterPosition(), Phone.getText().toString());
                }
            });
        }
    }


    public interface OnItemClickListener{
        void onClick(int pos, String phone);
    }
}
