package com.butuh.uang.bu.tuhu.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;

/**
 * 对话框基类
 *
 * @author leiyx
 * Created on 2018/11/6 21:48.
 */
public abstract class BaseDialog extends Dialog {
    public Context mContext;
    /**
     * 确定按钮点击监听
     */
    protected OnPositiveClickListener mPositiveListener;

    /**
     * 取消按钮监听
     */
    protected OnNegativeClickListener mNegativeListener;

    public BaseDialog(Context context) {
        super(context);
        mContext = context;
        if (getLayoutView()==null)
            setContentView(getLayoutId());
        else {
            View view=getLayoutView();
            setContentView(view);
            ButterKnife.bind(this,view);
        }
        findViews();
    }

    /**
     * 设置dialog主题
     *
     * @param context
     * @param theme
     */
    public BaseDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        if (getLayoutView()==null)
            setContentView(getLayoutId());
        else{
            View view=getLayoutView();
            setContentView(view);
            ButterKnife.bind(this,view);
        }
        findViews();
    }

    /**
     * @param context
     * @param cancelable     返回键是否可用
     * @param cancelListener
     */
    public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext = context;
        // 最好在初始化的时候就去加载布局，在onCreate里面做会有卡顿
        if (getLayoutView()==null)
            setContentView(getLayoutId());
        else{
            View view=getLayoutView();
            setContentView(view);
            ButterKnife.bind(this,view);
        }
//        findViews();
    }

    /**
     * @param context
     */
    public BaseDialog(Context context, int theme, int layout) {
        super(context, theme);
        mContext = context;
        // 最好在初始化的时候就去加载布局，在onCreate里面做会有卡顿
        if (getLayoutView()==null)
            setContentView(layout);
        else{
            View view=getLayoutView();
            setContentView(view);
            ButterKnife.bind(this,view);
        }
//        findViews();
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 第一次show的时候会调用该方法
        setWindowParam();
        findViews();
        // 将AddListener 移动到了findViews下方
        addListener();
    }

    protected View getLayoutView(){
        return null;
    }


    /**
     * 添加监听
     */
    protected abstract void addListener();

    /**
     * 1.获得布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 2.初始化布局中的view
     */
    protected abstract void findViews();

    /**
     * 3.设置window属性
     */
    protected abstract void setWindowParam();

    protected void setPositiveButtonListener(OnPositiveClickListener listener) {
        mPositiveListener = listener;
    }

    protected void setNegativeButtonListener(OnNegativeClickListener listener) {
        mNegativeListener = listener;
    }

    public interface OnPositiveClickListener {
        void onPositiveButtonClick();
    }

    public interface OnNegativeClickListener {
        void onPositiveButtonClick();
    }

    /**
     * @param
     * @param gravity
     */
    public void setWindowParams(int gravity) {
        setWindowParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, gravity);
    }

    /**
     * 宽是match,高自己定义
     *
     * @param height
     * @param gravity
     */
    public void setWindowParams(int height, int gravity) {
        setWindowParams(ViewGroup.LayoutParams.MATCH_PARENT, height, gravity);
    }

    /**
     * 在设置 设置dialog的一些属性
     *
     * @param width   一般布局和代码这里都设置match,要设置边距的直接布局里调好
     * @param height  一般布局height设置为wrap，这样可以调整dialog的上中下位置，要固定(非上中下)位置的直接在布局中调整， 设置match后，软键盘不会挤压布局
     * @param gravity 设置match后，此属性无用
     */
    public void setWindowParams(int width, int height, int gravity) {
        setWindowParams(width,height,gravity,1f);
    }

    /**
     * 在设置 设置dialog的一些属性
     *
     * @param width   一般布局和代码这里都设置match,要设置边距的直接布局里调好
     * @param height  一般布局height设置为wrap，这样可以调整dialog的上中下位置，要固定(非上中下)位置的直接在布局中调整， 设置match后，软键盘不会挤压布局
     * @param gravity 设置match后，此属性无用
     */
    public void setWindowParams(int width, int height, int gravity,float dim) {
        // setCancelable(cancelable);
        // setCanceledOnTouchOutside(cancel);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        // setContentView设置布局的透明度，0为透明，1为实际颜色,该透明度会使layout里的所有空间都有透明度，不仅仅是布局最底层的view
        // params.alpha = 1f;
        // 窗口的背景，0为透明，1为全黑
        // params.dimAmount = 0f;
        params.width = width;
        params.height = height;
        params.gravity = gravity;
        params.alpha = dim;
        window.setAttributes(params);
    }
}
