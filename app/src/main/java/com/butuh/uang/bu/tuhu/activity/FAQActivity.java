package com.butuh.uang.bu.tuhu.activity;

import android.os.Bundle;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.adapter.FAQAdapter;
import com.butuh.uang.bu.tuhu.base.BaseActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FAQActivity extends BaseActivity {

    @BindView(R.id.rv_data)
    RecyclerView rvData;

    private FAQAdapter adapter;

    @Override
    public void getIntentData() {

    }

    @Override
    public int layoutResource() {
        return R.layout.activity_f_a_q;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        rvData.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        adapter=new FAQAdapter();
        rvData.setAdapter(adapter);
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void requestOnCreate() {

    }

    @Override
    public void destroy() {

    }
}
