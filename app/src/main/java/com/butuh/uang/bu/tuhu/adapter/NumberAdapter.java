package com.butuh.uang.bu.tuhu.adapter;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.util.FormatUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NumberAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private boolean needFormat=false;

    public boolean isNeedFormat() {
        return needFormat;
    }

    public void setNeedFormat(boolean needFormat) {
        this.needFormat = needFormat;
    }
    public NumberAdapter() {
        super(R.layout.item_number,new ArrayList<>());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {
        helper.setText(R.id.text,needFormat? FormatUtil.formatMoneyNoUnit(item):item);
    }
}
