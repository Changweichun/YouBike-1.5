package com.android.youbike;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youbike.entity.FindBikeList;
import com.android.youbike.entity.FixBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class FindBikeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context context;
    private ArrayList<FindBikeList> items;
    private OnItemClickListener monitemClickListener = null;
    public static interface OnItemClickListener{
        void OnItemClick(View view, int i);
    }

    public FindBikeAdapter(Context context, ArrayList<FindBikeList> items) {
        this.context=context;
        this.items=items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View items= LayoutInflater.from(context).inflate(R.layout.find_bike_adapter, null);
        RecyclerView.ViewHolder holder= new MyHolder(items);
        items.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyHolder hold = (MyHolder) holder;
        FindBikeList data = items.get(position);
        hold.id.setText(data.getNum());
        hold.bikeNum.setText(data.getBikeNum());
        hold.phone.setText(data.getPhone());
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
        private TextView bikeNum;
        private TextView phone;

        public MyHolder(View itemView) {
            super(itemView);
            id= itemView.findViewById(R.id.num);
            bikeNum=itemView.findViewById(R.id.bikeNum);
            phone=itemView.findViewById(R.id.phone);
            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    Uri uri = Uri.parse(String.format("tel:%1$s", phone.getText().toString()));
                    intent.setData(uri);
                    context.startActivity(intent);
                }
            });
        }
    }
}

