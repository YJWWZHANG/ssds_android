package com.dashi1314.ssds.mvp.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.dashi1314.common.base.BaseActivity;
import com.dashi1314.common.base.NullFragment;
import com.dashi1314.ssds.R;
import com.dashi1314.ssds.di.component.DaggerActivityComponent;
import com.dashi1314.ssds.di.module.ActivityModule;
import com.dashi1314.ssds.mvp.contract.MainContract;
import com.dashi1314.ssds.mvp.presenter.MainPresenter;
import com.roughike.bottombar.BottomBar;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    private HashMap<String, Class<? extends SupportFragment>> mLoadFragments = new HashMap<>();

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        loadMultipleRootFragment(R.id.fl_content, 0, new NullFragment(), new NullFragment(), new NullFragment());

        mTvTitle.setText(R.string.app_name);
    }

    @Override
    public void onBackPressedSupport() {
        AppUtils.exitApp();
    }

    @Override
    public void loading() {

    }

    @Override
    public void dismissLoading() {

    }


    @Override
    public void setTest(String s) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
