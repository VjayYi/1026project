package com.butuh.uang.bu.tuhu.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.adapter.NumberAdapter;
import com.butuh.uang.bu.tuhu.util.DensityUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PopSelection extends PopupWindow {

    private BaseQuickAdapter.OnItemClickListener listener;
    private RecyclerView rvData;
    private NumberAdapter adapter;

    public void setListener(BaseQuickAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public PopSelection(Context context) {
        super(context);
        setWidth(DensityUtil.dp2px(120));
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_selection,
                null, false);
        setContentView(contentView);
        rvData=contentView.findViewById(R.id.rv_data);
        rvData.setLayoutManager(new LinearLayoutManager(context));

        adapter=new NumberAdapter();
        rvData.setAdapter(adapter);

        adapter.setOnItemClickListener(listener);
    }

    public void setData(List<String> data){
        if (rvData==null)
            return;
        if (adapter==null)
            return;
        adapter.setNewData(data);
    }

}