package com.butuh.uang.bu.tuhu.activity;

import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.base.BaseActivity;
import com.butuh.uang.bu.tuhu.fragment.HomeFragment;
import com.butuh.uang.bu.tuhu.fragment.MineFragment;
import com.butuh.uang.bu.tuhu.param.HeaderParam;
import com.butuh.uang.bu.tuhu.util.AppInfoUtil;
import com.butuh.uang.bu.tuhu.util.SHAUtil;
import com.butuh.uang.bu.tuhu.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_mine)
    RadioButton rbMine;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;

    private long exitTime;
    private FragmentManager fragmentManager;
    private List<Fragment> listFragment;
    private int selected = 0;

    @Override
    public void getIntentData() {

    }

    @Override
    public int layoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        listFragment = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        addFragment(savedInstanceState);
    }

    @Override
    public void addListeners() {
        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        setSelect(0);
                        break;
                    case R.id.rb_mine:
                        setSelect(1);
                        break;
                }
            }
        });
    }

    @Override
    public void requestOnCreate() {

    }

    @Override
    public void destroy() {

    }


    private void addFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (fragmentManager.findFragmentByTag("home") != null) {
                listFragment.add(fragmentManager.findFragmentByTag("home"));
            } else {
                listFragment.add(new HomeFragment());
            }
            if (fragmentManager.findFragmentByTag("mine") != null) {
                listFragment.add(fragmentManager.findFragmentByTag("mine"));
            } else {
                listFragment.add(new MineFragment());
            }

        } else {
            listFragment.add(new HomeFragment());
            listFragment.add(new MineFragment());
        }
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.frame, listFragment.get(0), "home")
                    .add(R.id.frame, listFragment.get(1), "mine")
                    .commit();
        } else {
            for (int i = 0, j = listFragment.size(); i < j; i++) {
                if (listFragment.get(i).isAdded() && i != selected) {
                    fragmentManager.beginTransaction().hide(listFragment.get(i)).commit();
                }
            }
        }
        fragmentManager.beginTransaction()
                .show(listFragment.get(0))
                .hide(listFragment.get(1))
                .commit();
        setSelect(selected);
    }

    private void setSelect(int position) {
        if (position==selected)
            return;
        if (listFragment.get(position).isAdded()&& null!=fragmentManager.findFragmentByTag(position==0?"home":"mine") && position != selected) {
            fragmentManager.beginTransaction().show(listFragment.get(position)).hide(listFragment.get(selected)).commit();
        }else{
            fragmentManager.beginTransaction().add(listFragment.get(position),position==0?"home":"mine").hide(listFragment.get(selected)).commit();
        }
        selected=position;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
            Process.killProcess(Process.myPid());
        }
    }

}
