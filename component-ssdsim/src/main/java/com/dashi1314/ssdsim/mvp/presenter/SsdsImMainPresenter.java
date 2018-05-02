package com.dashi1314.ssdsim.mvp.presenter;

import com.dashi1314.common.base.RxPresenter;
import com.dashi1314.ssdsim.mvp.contract.SsdsImMainContract;

import javax.inject.Inject;

public class SsdsImMainPresenter extends RxPresenter<SsdsImMainContract.View> implements SsdsImMainContract.Presenter {

    @Inject
    public SsdsImMainPresenter() {
    }
}
