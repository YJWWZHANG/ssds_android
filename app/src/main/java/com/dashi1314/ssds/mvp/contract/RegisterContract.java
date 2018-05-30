package com.dashi1314.ssds.mvp.contract;

import com.dashi1314.common.base.BasePresenter;
import com.dashi1314.common.base.BaseView;

public interface RegisterContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<RegisterContract.View> {
        void register(String account, String password);
    }
}
