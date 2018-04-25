package com.dashi1314.ssds.ui.main;

import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.dashi1314.common.base.BaseActivity;
import com.dashi1314.ssds.R;
import com.dashi1314.ssds.presenter.main.MainContract;
import com.dashi1314.ssds.presenter.main.MainPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected void initInject() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        mTvTest.setText(ActivityUtils.getActivityList().size() + " 随身大师");
    }

    @OnClick(R.id.tv_test)
    public void onViewClicked() {
        AppUtils.exitApp();
    }

    @Override
    public void loading() {

    }

    @Override
    public void dismissLoading() {

    }
}
