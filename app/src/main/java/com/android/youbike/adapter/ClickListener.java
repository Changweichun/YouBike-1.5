package com.android.youbike.adapter;

import android.view.View;

import com.android.youbike.entity.CardList;

public interface ClickListener {
    void onSelectClicked(View v, int position, String id, String cardType);

    void onDeleteClicked(View v, int position, String id, String cardType);
}
