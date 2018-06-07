package com.dashi1314.ssds.mvp.contract;

import com.dashi1314.common.base.BasePresenter;
import com.dashi1314.common.base.BaseView;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;

public interface LoginContract {

    interface View extends BaseView {
        void verificationCodeResult(int event, int result, Object data);
        void authorizeComplete(Platform platform, int i, HashMap<String, Object> hashMap);
        void authorizeError(Platform platform, int i, Throwable throwable);
        void authorizeCancel(Platform platform, int i);
    }

    interface Presenter extends BasePresenter<LoginContract.View> {
        void login(String account, String password);
        void sendCode(String country, String phone);
        void submitCode(String country, String phone, String code);
        void platformAuthorize(String platformName);
    }

}
