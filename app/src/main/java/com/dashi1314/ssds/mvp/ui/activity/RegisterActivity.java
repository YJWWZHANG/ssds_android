package com.dashi1314.ssds.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;

import com.dashi1314.common.base.BaseActivity;
import com.dashi1314.ssds.R;
import com.dashi1314.ssds.di.component.DaggerActivityComponent;
import com.dashi1314.ssds.di.module.ActivityModule;
import com.dashi1314.ssds.mvp.contract.RegisterContract;
import com.dashi1314.ssds.mvp.presenter.RegisterPresenter;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View{

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, RegisterActivity.class));
    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void onBackPressedSupport() {
        finish();
    }

    @Override
    public void loading() {

    }

    @Override
    public void dismissLoading() {

    }
}
