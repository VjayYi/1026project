package com.butuh.uang.bu.tuhu.adapter;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.bean.ProductBean;
import com.butuh.uang.bu.tuhu.util.AppInfoUtil;
import com.butuh.uang.bu.tuhu.util.FormatUtil;
import com.butuh.uang.bu.tuhu.util.GlideUtil;
import com.butuh.uang.bu.tuhu.util.NumberUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ProductListAdapter extends BaseQuickAdapter<ProductBean,BaseViewHolder> {

    public ProductListAdapter() {
        super(R.layout.item_product,new ArrayList());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ProductBean item) {
        if (item==null)
            return;
        GlideUtil.loadImage(mContext,item.getCharacteristic(),helper.getView(R.id.logo));
        helper.getView(R.id.iv_collection).setSelected(item.getFavorited()==1);
        helper.addOnClickListener(R.id.iv_collection,R.id.quick_loan)
                .setText(R.id.tv_name,item.getDesignation())
                .setText(R.id.tv_money, FormatUtil.formatMoney(item.getDisplayLargestAmountOfMoney()))
                .setText(R.id.tv_rate,"Acuan Bunga "+FormatUtil.formatRate(item.getShowLoanCycle()))
                .setText(R.id.quick_loan, isAppInstalled(item)?"Buka":"Segera Pinjam")
                .setText(R.id.tv_period,"Proses "+item.getDisplayMaximumCycle()+" hari");
    }

    private boolean isAppInstalled(ProductBean item){
        boolean b=AppInfoUtil.checkAppInstalled(item.getPacket());
        item.setInstalled(b);
        return b;
    }
}
