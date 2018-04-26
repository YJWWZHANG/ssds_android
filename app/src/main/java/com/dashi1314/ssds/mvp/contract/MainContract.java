package com.dashi1314.ssds.mvp.contract;

import com.dashi1314.common.base.BasePresenter;
import com.dashi1314.common.base.BaseView;

public interface MainContract{

    interface View extends BaseView {
        void setTest(String s);
    }

    interface Presenter extends BasePresenter<View> {
        void loadTest();
    }
}
