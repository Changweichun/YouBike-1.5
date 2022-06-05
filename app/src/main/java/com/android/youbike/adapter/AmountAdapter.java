package com.android.youbike.adapter;

import androidx.annotation.Nullable;

import com.android.youbike.R;
import com.android.youbike.entity.RecordBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;


public class AmountAdapter extends BaseQuickAdapter<RecordBean, BaseViewHolder> {
    public AmountAdapter(@Nullable List<RecordBean> data) {
        super(R.layout.res_amount_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordBean item) {
        helper.setText(R.id.tv1,"通報案號：" +item.getTitle())
                .setText(R.id.tv2,"通報類型："+item.getType())
                .setText(R.id.tv3,"通報內容："+item.getContent());
    }
}
