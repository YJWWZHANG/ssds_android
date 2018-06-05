package com.dashi1314.ssds.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dalimao.corelibrary.VerificationCodeInput;
import com.dashi1314.common.Constants;
import com.dashi1314.common.base.BaseActivity;
import com.dashi1314.ssds.R;
import com.dashi1314.ssds.di.component.DaggerActivityComponent;
import com.dashi1314.ssds.di.module.ActivityModule;
import com.dashi1314.ssds.mvp.contract.LoginContract;
import com.dashi1314.ssds.mvp.presenter.LoginPresenter;
import com.hbb20.CountryCodePicker;
import com.white.countdownbutton.CountDownButton;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.country_code_picker)
    CountryCodePicker mCountryCodePicker;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.count_down_button)
    CountDownButton mCountDownButton;
    @BindView(R.id.verification_code_input)
    VerificationCodeInput mVerificationCodeInput;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    private long mExitTime;
    private String mCode;

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, LoginActivity.class));
        activity.finish();
    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        mCountryCodePicker.registerCarrierNumberEditText(mEtPhone);
        mCountryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                ToastUtils.showShort(mCountryCodePicker.getSelectedCountryName());
            }
        });
        mCountryCodePicker.setPhoneNumberValidityChangeListener(new CountryCodePicker.PhoneNumberValidityChangeListener() {
            @Override
            public void onValidityChanged(boolean isValidNumber) {
                // your code
                LogUtils.wTag(Constants.LOG_TAG, "效度改变" + isValidNumber);
                if (isValidNumber) {
                    mCountDownButton.setEnabled(true);
                } else {
                    mCountDownButton.setEnabled(false);
                }
            }
        });
        mVerificationCodeInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String content) {
                mCode = content;
                LogUtils.wTag(Constants.LOG_TAG, content);
                mBtnLogin.setEnabled(true);
            }
        });

    }

    @Override
    public void onBackPressedSupport() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtils.showShort("再按一次退出程序");
            mExitTime = System.currentTimeMillis();
        } else {
            AppUtils.exitApp();
        }
    }

    @OnClick({R.id.btn_login, R.id.ibtn_wechat, R.id.ibtn_qq, R.id.ibtn_weibo, R.id.count_down_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
//                mPresenter.login("", "");
//                MainActivity.launch(this);
                mVerificationCodeInput.setEnabled(true);
//                submitCode(mCountryCodePicker.getSelectedCountryCode(), mCountryCodePicker.getFullNumber().replaceFirst(mCountryCodePicker.getSelectedCountryCode(), ""), mCode);
                break;
            case R.id.ibtn_wechat:
                ToastUtils.showShort("微信登录");
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                wechat.SSOSetting(false);  //设置false表示使用SSO授权方式
                wechat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        ToastUtils.showLong("onComplete");
                        ToastUtils.showLong(platform.getDb().exportData());
                        LogUtils.wTag(Constants.LOG_TAG, platform.getDb().exportData());
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        ToastUtils.showLong("onError");
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        ToastUtils.showLong("onCancel");
                    }
                }); // 设置分享事件回调
                wechat.authorize();//单独授权
                wechat.showUser(null);//授权并获取用户信息
                break;
            case R.id.ibtn_qq:
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.SSOSetting(false);  //设置false表示使用SSO授权方式
                qq.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        ToastUtils.showLong("onComplete");
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        ToastUtils.showLong("onError");
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        ToastUtils.showLong("onCancel");
                    }
                }); // 设置分享事件回调
                qq.authorize();//单独授权
                qq.showUser(null);//授权并获取用户信息
                break;
            case R.id.ibtn_weibo:
                Platform sinaWiebo = ShareSDK.getPlatform(SinaWeibo.NAME);
                sinaWiebo.SSOSetting(false);  //设置false表示使用SSO授权方式
                sinaWiebo.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        ToastUtils.showLong("onComplete");
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        ToastUtils.showLong("onError");
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                        ToastUtils.showLong("onCancel");
                    }
                }); // 设置分享事件回调
                sinaWiebo.authorize();//单独授权
                sinaWiebo.showUser(null);//授权并获取用户信息
                break;
            case R.id.count_down_button:
                sendCode(mCountryCodePicker.getSelectedCountryCode(), mCountryCodePicker.getFullNumber().replaceFirst(mCountryCodePicker.getSelectedCountryCode(), ""));
                break;
            default:
                break;
        }
    }


    @Override
    public void loading() {

    }

    @Override
    public void dismissLoading() {

    }

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    ToastUtils.showLong("验证码发送成功");
                } else {
                    // TODO 处理错误的结果
                    ToastUtils.showLong("验证码发送失败");
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    ToastUtils.showLong("手机号验证成功");
                    MainActivity.launch(LoginActivity.this);
                } else {
                    // TODO 处理错误的结果
                    ToastUtils.showLong("手机号验证失败");
                    mVerificationCodeInput.setEnabled(true);
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

}
