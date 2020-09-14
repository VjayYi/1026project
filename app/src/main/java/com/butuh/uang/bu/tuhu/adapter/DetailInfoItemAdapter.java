package com.butuh.uang.bu.tuhu.adapter;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.bean.AdditionalBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DetailInfoItemAdapter extends BaseQuickAdapter<AdditionalBean.ParticularsBean, BaseViewHolder> {

    public DetailInfoItemAdapter() {
        super(R.layout.item_detail_info,new ArrayList<>());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AdditionalBean.ParticularsBean item) {
        helper.setText(R.id.tv_title,item.getJudul())
                .setText(R.id.tv_subtitle,item.getDetails());
    }
}
