package com.butuh.uang.bu.tuhu.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.app.ProjectActivityManager;
import com.butuh.uang.bu.tuhu.dialog.LoadingDialog;
import com.butuh.uang.bu.tuhu.dialog.PopShare;
import com.butuh.uang.bu.tuhu.util.DensityUtil;
import com.butuh.uang.bu.tuhu.view.MyToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

// 常规的BaseActivity
public abstract class BaseActivity extends RxAppCompatActivity {

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @Nullable
    @BindView(R.id.smart)
    protected SmartRefreshLayout mRefreshLayout;

    public BaseActivity mBaseActivity;
    private LoadingDialog loadingDialog;
    private Unbinder unbinder;
    public PopShare popShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layoutResource());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mBaseActivity = this;

        ProjectActivityManager.getInstance().addActivity(this);
        translucentStatusBar();

        unbinder = ButterKnife.bind(mBaseActivity);

        initToolbar();
        initRefreshLayout();

        getIntentData();

        initViews(savedInstanceState);

        addListeners();
        requestOnCreate();
    }

    @Override
    protected void onDestroy() {
        destroy();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.cancel();
        }
        loadingDialog = null;
        ProjectActivityManager.getInstance().removeActivity(this);
        unbinder.unbind();
        super.onDestroy();
    }

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

    /**
     * 获取界面传递数据
     */
    public abstract void getIntentData();

    /**
     * 初始化布局中的空间，首先要调用setContentView
     */
    public abstract int layoutResource();

    /**
     * 初始化本地数据
     */
    public abstract void initViews(Bundle savedInstanceState);

    /**
     * 添加点击事件
     */
    public abstract void addListeners();

    /**
     * 在onCreate中请求服务
     */
    public abstract void requestOnCreate();

    public abstract void destroy();


    /**
     * 初始化Toolbar
     * 不显示标题
     */
    public void initToolbar() {
        try {
            if (mToolbar == null) {
                return;
            }
            setSupportActionBar(mToolbar);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
            mToolbar.setPadding(0, getStatusHeight(), 0, 0);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(false);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化SmartRefreshLayout
     * 默认配置：
     * 禁止加载更多
     * 允许自动加载更多
     * 允许下拉刷新
     * 允许子视图响应滑动
     * 允许加载时的列表滑动操作
     */
    private void initRefreshLayout() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setBackgroundColor(Color.TRANSPARENT);
            mRefreshLayout.setEnableLoadMore(false);
            mRefreshLayout.setEnableAutoLoadMore(true);
            mRefreshLayout.setEnableScrollContentWhenRefreshed(true);
            mRefreshLayout.setNestedScrollingEnabled(true);
        }
    }

    protected int getStatusHeight() {
        int result = 0;
        int resId = getResources().getIdentifier("status_bar_height",
                "dimen",
                "android");
        if (resId > 0) {
            result = getResources().getDimensionPixelSize(resId);
        }
        if (result == 0) {
            result = DensityUtil.dp2px(24);
        }
        return result;
    }


    /**
     * 透明状态栏设置
     */
    public void translucentStatusBar() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        View decorView = window.getDecorView();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_VISIBLE);
        } else {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    | View.SYSTEM_UI_FLAG_VISIBLE);
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }


    protected void showToast(String msg){
        MyToast toast = new MyToast.Builder(this)
                .setDuration(Toast.LENGTH_LONG)
                .setMessage(msg)
                .build();
        toast.show();
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