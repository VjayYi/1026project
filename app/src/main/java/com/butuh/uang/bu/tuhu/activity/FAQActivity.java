package com.butuh.uang.bu.tuhu.activity;

import android.os.Bundle;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.adapter.FAQAdapter;
import com.butuh.uang.bu.tuhu.base.BaseActivity;
import com.butuh.uang.bu.tuhu.bean.FAQBean;
import com.butuh.uang.bu.tuhu.bean.PageTableBean;
import com.butuh.uang.bu.tuhu.bean.PrivacyBean;
import com.butuh.uang.bu.tuhu.http.HttpCallback;
import com.butuh.uang.bu.tuhu.http.HttpUtil;
import com.butuh.uang.bu.tuhu.http.Interface;
import com.butuh.uang.bu.tuhu.result.BaseResult;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FAQActivity extends BaseActivity {

    @BindView(R.id.rv_data)
    RecyclerView rvData;

    private FAQAdapter adapter;
    private boolean isFirstLoad=true;

    @Override
    public void getIntentData() {

    }

    @Override
    public int layoutResource() {
        return R.layout.activity_f_a_q;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        pageName="page-faq";
        rvData.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        adapter=new FAQAdapter();
        rvData.setAdapter(adapter);
    }

    @Override
    public void addListeners() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
    }

    @Override
    public void requestOnCreate() {
        loadData();
    }

    @Override
    public void destroy() {

    }


    private void loadData(){
        if (isFirstLoad)
            showLoadingDialog(true);
        String data="[[\"411\",\"2\"],[\"412\",\"437\"],[\"413\",\"FAQ\"]]";
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), data);
        Observable<BaseResult<PageTableBean<PrivacyBean>>> observable= HttpUtil.createService(Interface.class).accessCatalog(body);
        HttpUtil.httpCallback(mBaseActivity, observable, new HttpCallback<PageTableBean<PrivacyBean>>() {
            @Override
            public void success(PageTableBean<PrivacyBean> result, String message) {
                if (isFirstLoad)
                    showLoadingDialog(false);
                else if(mRefreshLayout!=null)
                    mRefreshLayout.finishRefresh();
                if (result==null||result.getKuantitas()==null||result.getKuantitas().size()==0){
                    return;
                }
                if(result.getKuantitas().get(0)==null)
                    return;
                adapter.setNewData(handleData(result.getKuantitas().get(0).getDetails()));
            }

            @Override
            public void failure(String code, Throwable throwable) {
                if (isFirstLoad)
                    showLoadingDialog(false);
                else if(mRefreshLayout!=null)
                    mRefreshLayout.finishRefresh();
                showToast(throwable.getMessage());
            }
        });
    }

    private List<FAQBean> handleData(String d){
//        String data=d.substring(3,d.length()-4);
        List<String> list=new ArrayList<>();
        String[] datas=d.split("<p>&nbsp;</p>");
        for (String s:datas){
            if (s.contains("<p><br></p>")){
                String[] ds=s.split("<p><br></p>");
                for(String ss:ds){
                    list.add(ss);
                }
            }else{
                list.add(s);
            }
        }
        List<FAQBean> fliat=new ArrayList<>();
        for(String b:list){
            String[] fs=b.substring(3,b.length()-4).split("</p><p>");
            FAQBean bean=new FAQBean();
            bean.setQ(fs[0]);
            bean.setA(fs[1]);
            fliat.add(bean);
        }
        return fliat;
    }
}
