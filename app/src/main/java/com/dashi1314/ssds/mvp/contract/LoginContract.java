package com.dashi1314.ssds.mvp.contract;

import com.dashi1314.common.base.BasePresenter;
import com.dashi1314.common.base.BaseView;

public interface LoginContract {

    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter<LoginContract.View> {
        void login(String account, String password);
    }

}
