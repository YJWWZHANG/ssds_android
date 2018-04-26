package com.dashi1314.ssds.mvp.presenter;

import com.dashi1314.common.base.RxPresenter;
import com.dashi1314.ssds.mvp.contract.MainContract;

import javax.inject.Inject;

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private int i = 0;

    @Inject
    public MainPresenter() {
    }

    @Override
    public void loadTest() {
        mView.setTest("随身大师" + i++);
    }
}
