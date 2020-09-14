package com.butuh.uang.bu.tuhu.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.bean.ProductBean;
import com.butuh.uang.bu.tuhu.util.GlideUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

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

    private List<ProductBean> datas;
    private OnItemClickListener listener;

    public OnItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

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
                if (listener!=null){
                    listener.onClick(datas.get(0));
                }
                break;
            case R.id.anniu2:
                if (listener!=null){
                    listener.onClick(datas.get(1));
                }
                break;
            case R.id.anniu3:
                if (listener!=null){
                    listener.onClick(datas.get(2));
                }
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }

    public void setData(List<ProductBean> datas){
        this.datas=datas;
        switch (datas.size()) {
            case 1:
                anniu1.setVisibility(View.VISIBLE);
                anniu2.setVisibility(View.GONE);
                anniu3.setVisibility(View.GONE);
                GlideUtil.loadImage(mContext,datas.get(0).getCharacteristic(),ivAnniu1);
                break;
            case 2:
                anniu1.setVisibility(View.VISIBLE);
                anniu2.setVisibility(View.VISIBLE);
                anniu3.setVisibility(View.GONE);
                GlideUtil.loadImage(mContext,datas.get(0).getCharacteristic(),ivAnniu1);
                GlideUtil.loadImage(mContext,datas.get(1).getCharacteristic(),ivAnniu2);
                break;
            case 3:
                anniu1.setVisibility(View.VISIBLE);
                anniu2.setVisibility(View.VISIBLE);
                anniu3.setVisibility(View.VISIBLE);
                GlideUtil.loadImage(mContext,datas.get(0).getCharacteristic(),ivAnniu1);
                GlideUtil.loadImage(mContext,datas.get(1).getCharacteristic(),ivAnniu2);
                GlideUtil.loadImage(mContext,datas.get(2).getCharacteristic(),ivAnniu3);
                break;
        }
    }

    public interface OnItemClickListener{
        void onClick(ProductBean p);
    }
}
