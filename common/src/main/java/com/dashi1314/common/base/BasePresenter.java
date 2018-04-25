package com.dashi1314.common.base;

public interface BasePresenter<T extends BaseView> {

    void attachView(T view);
    void detachView();
}
