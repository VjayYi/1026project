package com.butuh.uang.bu.tuhu.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.butuh.uang.bu.tuhu.R;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class ZhaoHuiDialog extends BaseDialog {

    @BindView(R.id.bg)
    ImageView bg;
    @BindView(R.id.iv_anniu1)
    RoundedImageView ivAnniu1;
    @BindView(R.id.anniu1)
    LinearLayout anniu1;
    @BindView(R.id.iv_anniu2)
    RoundedImageView ivAnniu2;
    @BindView(R.id.anniu2)
    LinearLayout anniu2;
    @BindView(R.id.iv_anniu3)
    RoundedImageView ivAnniu3;
    @BindView(R.id.anniu3)
    LinearLayout anniu3;

    public ZhaoHuiDialog(Context context) {
        super(context,R.style.dim_dialog);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_zhaohui;
    }

    @Override
    protected View getLayoutView() {
        return View.inflate(mContext, getLayoutId(), null);
    }

    @Override
    protected void findViews() {

    }

    @Override
    protected void setWindowParam() {

    }

    @OnClick({R.id.anniu1, R.id.anniu2, R.id.anniu3,R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.anniu1:
                //去下载
                break;
            case R.id.anniu2:
                break;
            case R.id.anniu3:
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }
}
