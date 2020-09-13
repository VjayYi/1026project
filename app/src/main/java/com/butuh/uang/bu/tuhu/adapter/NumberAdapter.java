package com.butuh.uang.bu.tuhu.adapter;

import com.butuh.uang.bu.tuhu.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NumberAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public NumberAdapter() {
        super(R.layout.item_number,new ArrayList<>());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.text,item);
    }
}
