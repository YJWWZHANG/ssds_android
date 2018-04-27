package com.dashi1314.ssds.mvp.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.dashi1314.common.base.BaseActivity;
import com.dashi1314.common.router.RouterConstants;
import com.dashi1314.ssds.R;
import com.dashi1314.ssds.di.component.DaggerActivityComponent;
import com.dashi1314.ssds.di.module.ActivityModule;
import com.dashi1314.ssds.mvp.contract.MainContract;
import com.dashi1314.ssds.mvp.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tv_test)
    TextView mTvTest;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

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
        mTvTitle.setText(R.string.app_name);
    }

    @Override
    public void onBackPressed() {
        AppUtils.exitApp();
    }

    @Override
    public void loading() {

    }

    @Override
    public void dismissLoading() {

    }

    @OnClick({R.id.btn_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test:
                ARouter.getInstance().build(RouterConstants.PATH_TEST).withString("key", "zqb").navigation();
                mPresenter.loadTest();
                break;
        }
    }

    @Override
    public void setTest(String s) {
        mTvTest.setText(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
