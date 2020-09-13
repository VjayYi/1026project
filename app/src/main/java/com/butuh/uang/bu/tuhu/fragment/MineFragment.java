package com.butuh.uang.bu.tuhu.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.activity.AboutActivity;
import com.butuh.uang.bu.tuhu.activity.CollectionListActivity;
import com.butuh.uang.bu.tuhu.activity.DownloadHistoryActivity;
import com.butuh.uang.bu.tuhu.activity.FAQActivity;
import com.butuh.uang.bu.tuhu.activity.FeedbackActivity;
import com.butuh.uang.bu.tuhu.activity.LoginActivity;
import com.butuh.uang.bu.tuhu.activity.PrivacyActivity;
import com.butuh.uang.bu.tuhu.adapter.CollectionListAdapter;
import com.butuh.uang.bu.tuhu.app.ProjectApplication;
import com.butuh.uang.bu.tuhu.base.BaseFragment;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @BindView(R.id.iv_head)
    RoundedImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_to_login)
    TextView tvToLogin;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.rl_download)
    RelativeLayout rlDownload;
    @BindView(R.id.rl_collection)
    RelativeLayout rlCollection;
    @BindView(R.id.rl_faq)
    RelativeLayout rlFaq;
    @BindView(R.id.rl_comment)
    RelativeLayout rlComment;
    @BindView(R.id.rl_privacy)
    RelativeLayout rlPrivacy;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;

    @Override
    protected int layoutResource() {
        return R.layout.fragment_mine;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void initViews(View view) {

    }

    @Override
    public void addListeners() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (ProjectApplication.getInstance().isUserLogin()){
            ivHead.setImageResource(R.mipmap.my_touxiang_yes);
            tvToLogin.setText(ProjectApplication.getInstance().getPhone());
        }else{
            ivHead.setImageResource(R.mipmap.my_touxiang_no);
            tvToLogin.setText("klik disini untuk login");
        }
    }

    @Override
    public void requestOnViewCreated() {

    }

    @Override
    public void destroy() {

    }

    @OnClick({R.id.iv_head, R.id.tv_name, R.id.tv_to_login, R.id.iv_share, R.id.rl_download, R.id.rl_collection, R.id.rl_faq, R.id.rl_comment, R.id.rl_privacy, R.id.rl_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head:
            case R.id.tv_name:
            case R.id.tv_to_login:
                if (!ProjectApplication.getInstance().isUserLogin()){
                    startActivity(new Intent(mBaseActivity, LoginActivity.class));
                }
                break;
            case R.id.iv_share:
                showPopShare(ivShare);
                break;
            case R.id.rl_download:
                startActivity(new Intent(mBaseActivity, DownloadHistoryActivity.class));
                break;
            case R.id.rl_collection:
                if (!ProjectApplication.getInstance().isUserLogin()){
                    startActivity(new Intent(mBaseActivity, LoginActivity.class));
                }else{
                    //进入收藏页
                    startActivity(new Intent(mBaseActivity, CollectionListActivity.class));
                }
                break;
            case R.id.rl_faq:
                startActivity(new Intent(mBaseActivity, FAQActivity.class));
                break;
            case R.id.rl_comment:
                startActivity(new Intent(mBaseActivity, FeedbackActivity.class));
                break;
            case R.id.rl_privacy:
                startActivity(new Intent(mBaseActivity, PrivacyActivity.class));
                break;
            case R.id.rl_about:
                startActivity(new Intent(mBaseActivity, AboutActivity.class));
                break;
        }
    }
}
