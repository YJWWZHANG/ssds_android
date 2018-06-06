package com.dashi1314.ssds.mvp.presenter;

import com.dashi1314.common.base.RxPresenter;
import com.dashi1314.ssds.mvp.contract.LoginContract;
import com.dashi1314.ssds.mvp.ui.activity.LoginActivity;

import java.util.HashMap;

import javax.inject.Inject;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter{

    @Inject
    public LoginPresenter() {
    }

    @Override
    public void login(String account, String password) {

    }

    @Override
    public void sendCode(String country, String phone) {
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(final int event, final int result, final Object data) {
                ((LoginActivity)mView).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.verificationCodeResult(event, result, data);
                    }
                });
            }
        });
        SMSSDK.getVerificationCode(country, phone);
    }

    @Override
    public void submitCode(String country, String phone, String code) {
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(final int event, final int result, final Object data) {
                ((LoginActivity)mView).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.verificationCodeResult(event, result, data);
                    }
                });
            }
        });
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    @Override
    public void platformAuthorize(String platformName) {
        Platform platform = ShareSDK.getPlatform(platformName);
        platform.SSOSetting(false);  //设置false表示使用SSO授权方式
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                mView.authorizeComplete(platform, i, hashMap);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                mView.authorizeError(platform, i, throwable);
            }

            @Override
            public void onCancel(Platform platform, int i) {
                mView.authorizeCancel(platform, i);
            }
        }); // 设置分享事件回调
        platform.authorize();//单独授权
        platform.showUser(null);//授权并获取用户信息
    }
}
