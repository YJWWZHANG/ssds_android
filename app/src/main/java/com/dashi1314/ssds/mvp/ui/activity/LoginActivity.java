package com.dashi1314.ssds.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dashi1314.common.base.BaseActivity;
import com.dashi1314.ssds.R;
import com.dashi1314.ssds.di.component.DaggerActivityComponent;
import com.dashi1314.ssds.di.module.ActivityModule;
import com.dashi1314.ssds.mvp.contract.LoginContract;
import com.dashi1314.ssds.mvp.presenter.LoginPresenter;

import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View{

    private long mExitTime;

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void onBackPressedSupport() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showShort("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            AppUtils.exitApp();
        }
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mPresenter.login("", "");
                MainActivity.launch(this);
                break;
            case R.id.btn_register:
                RegisterActivity.launch(this);
                break;
        }
    }


    @Override
    public void loading() {

    }

    @Override
    public void dismissLoading() {

    }
}
