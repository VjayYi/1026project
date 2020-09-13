package com.butuh.uang.bu.tuhu.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.butuh.uang.bu.tuhu.dialog.LoadingDialog;
import com.butuh.uang.bu.tuhu.dialog.PopShare;
import com.butuh.uang.bu.tuhu.util.DensityUtil;
import com.trello.rxlifecycle2.components.support.RxFragment;

import androidx.annotation.NonNull;
import butterknife.ButterKnife;
import butterknife.Unbinder;

// 常规的BaseFragment
public abstract class BaseFragment extends RxFragment {

    public BaseActivity mBaseActivity;
    private Unbinder unbinder;
    private LoadingDialog loadingDialog;

    public PopShare popShare;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(layoutResource(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBaseActivity = (BaseActivity) getActivity();
        getIntentData();
        initViews(view);
        addListeners();
        requestOnViewCreated();
    }

    @Override
    public void onDestroyView() {
        destroy();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
        loadingDialog = null;
        unbinder.unbind();
        super.onDestroyView();
    }

    // 获取布局文件
    protected abstract int layoutResource();

    /**
     * 传值
     */
    public abstract void getIntentData();

    /**
     * 初始化本地数据
     */
    public abstract void initViews(View view);

    /**
     * 添加监听器
     */
    public abstract void addListeners();

    /**
     * 在onCreate中请求服务
     */
    public abstract void requestOnViewCreated();

    public abstract void destroy();

    public void showLoadingDialog(boolean show) {
        if (!(mBaseActivity == null || mBaseActivity.isDestroyed() || mBaseActivity.isFinishing())) {
            if (show) {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(mBaseActivity);
                }
                loadingDialog.show();
            } else {
                if (loadingDialog != null) {
                    loadingDialog.cancel();
                }
            }
        }
    }



    protected void showPopShare(View view){
        if (popShare==null){
            popShare=new PopShare(mBaseActivity);
        }
        popShare.setListener(new PopShare.OnItemClickListener() {
            @Override
            public void onShareWhatsApp() {
                popShare.dismiss();
            }

            @Override
            public void onShareFacebook() {
                popShare.dismiss();
            }
        });
        if (popShare.isShowing()) {
            popShare.dismiss();
        } else {
            popShare.showAsDropDown(view, -DensityUtil.dp2px(85), -DensityUtil.dp2px(12));
        }
    }

}