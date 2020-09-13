package com.butuh.uang.bu.tuhu.adapter;

import com.butuh.uang.bu.tuhu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class FAQAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    public FAQAdapter() {
        super(R.layout.item_faq,new ArrayList<>());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Object item) {
        if (item==null)
            return;

    }
}
