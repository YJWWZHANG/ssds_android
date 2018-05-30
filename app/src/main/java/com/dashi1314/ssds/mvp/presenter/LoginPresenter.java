package com.dashi1314.ssds.mvp.presenter;

import com.dashi1314.common.base.RxPresenter;
import com.dashi1314.ssds.mvp.contract.LoginContract;

import javax.inject.Inject;

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter{

    @Inject
    public LoginPresenter() {
    }

    @Override
    public void login(String account, String password) {

    }
}
