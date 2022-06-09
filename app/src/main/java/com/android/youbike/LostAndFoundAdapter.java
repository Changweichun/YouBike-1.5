package com.android.youbike;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youbike.entity.FindBikeList;
import com.android.youbike.entity.LostAndFoundList;

import java.util.ArrayList;

public class LostAndFoundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private ArrayList<LostAndFoundList> items;
    private OnItemClickListener monitemClickListener = null;
    public static interface OnItemClickListener{
        void OnItemClick(View view, int i);
    }

    public LostAndFoundAdapter(Context context, ArrayList<LostAndFoundList> items) {
        this.context=context;
        this.items=items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View items= LayoutInflater.from(context).inflate(R.layout.lost_and_found_adapter, null);
        RecyclerView.ViewHolder holder= new MyHolder(items);
        items.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder hold = (MyHolder) holder;
        LostAndFoundList data = items.get(position);
        hold.id.setText(data.getNum());
        hold.name.setText(data.getName());
        hold.category.setText(data.getCategory());
        hold.address.setText(data.getSite_name());
        hold.time.setText(data.getDate());
        hold.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return items == null? 0:items.size();
    }

    @Override
    public void onClick(View view) {
        if (monitemClickListener != null){
            monitemClickListener.OnItemClick(view, (int)view.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener monitemClickListener){
        this.monitemClickListener=monitemClickListener;
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        private TextView id;
        private TextView name;
        private TextView category;
        private TextView address;
        private TextView time;

        public MyHolder(View itemView) {
            super(itemView);
            id= itemView.findViewById(R.id.lost_num);
            name = itemView.findViewById(R.id.name);
            category=itemView.findViewById(R.id.lost_category);
            address=itemView.findViewById(R.id.address);
            time = itemView.findViewById(R.id.time);
        }
    }
}

