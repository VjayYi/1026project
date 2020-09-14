package com.butuh.uang.bu.tuhu.adapter;

import android.view.View;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.bean.FAQBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class FAQAdapter extends BaseQuickAdapter<FAQBean, BaseViewHolder> {

    public FAQAdapter() {
        super(R.layout.item_faq,new ArrayList<>());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FAQBean item) {
        if (item==null)
            return;
        helper.setText(R.id.tv_f,item.getQ())
                .setText(R.id.tv_a,item.getA())
                .setImageResource(R.id.iv_expand,item.isExpand()?R.mipmap.faq_guanbi:R.mipmap.faq_zhankai)
                .setGone(R.id.expand_view,item.isExpand());
        helper.getView(R.id.iv_expand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.setExpand(!item.isExpand());
                notifyItemChanged(helper.getAdapterPosition());
            }
        });
    }
}
