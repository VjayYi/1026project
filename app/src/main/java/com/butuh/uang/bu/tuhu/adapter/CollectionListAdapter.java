package com.butuh.uang.bu.tuhu.adapter;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.bean.ProductBean;
import com.butuh.uang.bu.tuhu.util.FormatUtil;
import com.butuh.uang.bu.tuhu.util.GlideUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class CollectionListAdapter extends BaseQuickAdapter<ProductBean, BaseViewHolder> {

    public CollectionListAdapter() {
        super(R.layout.item_collection, new ArrayList());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProductBean item) {
        if (item == null)
            return;
        GlideUtil.loadImage(mContext, item.getCharacteristic(), helper.getView(R.id.logo));
        helper.getView(R.id.iv_collection).setSelected(item.getFavorited() == 1);
        helper.addOnClickListener(R.id.iv_collection)
                .setText(R.id.tv_name, item.getDesignation())
                .setText(R.id.tv_money, FormatUtil.formatMoney(item.getDisplayLargestAmountOfMoney()))
                .setText(R.id.tv_rate, "Acuan Bunga " + FormatUtil.formatRate(item.getShowLoanCycle()));
    }
}
