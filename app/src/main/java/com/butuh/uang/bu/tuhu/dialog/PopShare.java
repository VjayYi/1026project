package com.butuh.uang.bu.tuhu.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.util.DensityUtil;

public class PopShare extends PopupWindow {

    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PopShare(Context context) {
        super(context);
        setHeight(DensityUtil.dp2px(72));
        setWidth(DensityUtil.dp2px(130));
//        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOutsideTouchable(true);
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_item_share,
                null, false);
        setContentView(contentView);
        contentView.findViewById(R.id.ll_whatsapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.onShareWhatsApp();
            }
        });
        contentView.findViewById(R.id.ll_facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null)
                    listener.onShareFacebook();
            }
        });
    }

    public interface OnItemClickListener{
        void onShareWhatsApp();
        void onShareFacebook();
    }
}
