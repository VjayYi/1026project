package com.butuh.uang.bu.tuhu.adapter;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.bean.ProductBean;
import com.butuh.uang.bu.tuhu.util.AppInfoUtil;
import com.butuh.uang.bu.tuhu.util.FormatUtil;
import com.butuh.uang.bu.tuhu.util.GlideUtil;
import com.butuh.uang.bu.tuhu.util.TimeUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class DownloadListAdapter extends BaseQuickAdapter<ProductBean,BaseViewHolder> {

    public DownloadListAdapter() {
        super(R.layout.item_download,new ArrayList());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProductBean item) {
        if (item==null)
            return;
        GlideUtil.loadImage(mContext,item.getCharacteristic(),helper.getView(R.id.logo));
        helper.addOnClickListener(R.id.tv_open)
                .setText(R.id.tv_name,item.getDesignation())
                .setText(R.id.tv_money, FormatUtil.formatMoney(item.getDisplayLargestAmountOfMoney()))
                .setText(R.id.tv_time, TimeUtil.format("HH:mm dd/MM/yyyy",item.getHistoryTime()))
                .setText(R.id.tv_open, AppInfoUtil.checkAppInstalled(item.getPacket())?"Buka":"Segera Pinjam");
    }
}
