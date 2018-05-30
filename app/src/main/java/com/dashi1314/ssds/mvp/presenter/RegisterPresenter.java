package com.dashi1314.ssds.mvp.presenter;

import com.dashi1314.common.base.RxPresenter;
import com.dashi1314.ssds.mvp.contract.RegisterContract;

import javax.inject.Inject;

public class RegisterPresenter extends RxPresenter<RegisterContract.View> implements RegisterContract.Presenter {

    @Inject
    public RegisterPresenter() {
    }

    @Override
    public void register(String account, String password) {

    }
}
