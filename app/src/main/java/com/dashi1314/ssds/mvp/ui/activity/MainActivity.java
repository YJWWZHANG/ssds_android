package com.dashi1314.ssds.mvp.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.dashi1314.common.base.BaseActivity;
import com.dashi1314.ssds.R;
import com.dashi1314.ssds.di.component.DaggerActivityComponent;
import com.dashi1314.ssds.di.module.ActivityModule;
import com.dashi1314.ssds.mvp.contract.MainContract;
import com.dashi1314.ssds.mvp.presenter.MainPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tv_test)
    TextView mTvTest;

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
                mPresenter.loadTest();
                break;
        }
    }

    @Override
    public void setTest(String s) {
        mTvTest.setText(s);
    }
}
