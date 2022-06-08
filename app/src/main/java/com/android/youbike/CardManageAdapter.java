package com.android.youbike;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.youbike.adapter.ClickListener;
import com.android.youbike.entity.CardList;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class CardManageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private Context context;
    private ArrayList<CardList> items;
    private int cardType ;
    private ClickListener listener;
    private WeakReference<ClickListener> listenerRef;


    public CardManageAdapter(Context context, ArrayList<CardList> items, ClickListener listener) {
        this.context=context;
        this.items=items;
        this.listener=listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View items= LayoutInflater.from(context).inflate(R.layout.card_manage_adapter, null);
        RecyclerView.ViewHolder holder= new MyHolder(items, listener);
        items.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position ) {
        MyHolder hold = (MyHolder) holder;
        CardList data = items.get(position);
        hold.id.setText(data.getEasyCardNum());
        cardType = Integer.parseInt(data.getCardType());
        if(cardType==0){
            hold.img.setImageResource(R.mipmap.card_1);
        }
        if(cardType==1){
            hold.img.setImageResource(R.mipmap.card_2);}
        hold.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return items == null? 0:items.size();
    }

    @Override
    public void onClick(View v) {

    }


    private class MyHolder extends RecyclerView.ViewHolder  {
        private TextView id;
        private ImageView img;
        private Button select;
        private Button delete;

        public MyHolder(View itemView, ClickListener listener) {
            super(itemView);
            id= itemView.findViewById(R.id.easyCardNum);
            img = (ImageView) itemView.findViewById(R.id.cardImage);
            select = itemView.findViewById(R.id.selectBtn);
            delete = itemView.findViewById(R.id.deleteBtn);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClicked(itemView, getAdapterPosition(), id.getText().toString(), items.get(getAdapterPosition()).getCardType());
                }
            });
            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onSelectClicked(itemView, getAdapterPosition(), id.getText().toString(), items.get(getAdapterPosition()).getCardType());
                }
            });
        }

    }
}

