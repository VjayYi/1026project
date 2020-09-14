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

    private OnItemClickListener listener;
    private RecyclerView rvData;
    private NumberAdapter adapter;
    private boolean needFormat=false;

    public boolean isNeedFormat() {
        return needFormat;
    }

    public void setNeedFormat(boolean needFormat) {
        this.needFormat = needFormat;
    }

    public void setListener(OnItemClickListener listener) {
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

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                if (listener!=null){
                    listener.onItemClick(adapter.getItem(position));
                }
            }
        });
    }

    public void setData(List<String> data){
        if (rvData==null)
            return;
        if (adapter==null)
            return;
        adapter.setNeedFormat(needFormat);
        adapter.setNewData(data);
    }

    public interface OnItemClickListener{
        void onItemClick(String data);
    }
}
