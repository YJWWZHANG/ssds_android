package com.dashi1314.ssds.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.KeyboardUtils;
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
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
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
    private boolean mIsValidNumber = false;

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
        mVerificationCodeInput.setEnabled(false);
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
                mIsValidNumber = isValidNumber;
                if (isValidNumber) {
                    mCountDownButton.setEnabled(true);
                    KeyboardUtils.hideSoftInput(LoginActivity.this);
                } else {
                    mCountDownButton.setEnabled(false);
                }
            }
        });
        mVerificationCodeInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String content) {
                mCode = content;
                if (mIsValidNumber) {
                    mBtnLogin.setEnabled(true);
                }
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

    @OnClick({R.id.btn_login, R.id.ibtn_wechat, R.id.ibtn_qq, R.id.ibtn_weibo, R.id.count_down_button, R.id.tv_temp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                mPresenter.submitCode(mCountryCodePicker.getSelectedCountryCode(), mCountryCodePicker.getFullNumber().replaceFirst(mCountryCodePicker.getSelectedCountryCode(), ""), mCode);
                break;
            case R.id.ibtn_wechat:
                mPresenter.platformAuthorize(Wechat.NAME);
                break;
            case R.id.ibtn_qq:
                mPresenter.platformAuthorize(QQ.NAME);
                break;
            case R.id.ibtn_weibo:
                mPresenter.platformAuthorize(SinaWeibo.NAME);
                break;
            case R.id.count_down_button:
                LogUtils.wTag(Constants.LOG_TAG, mCountryCodePicker.getSelectedCountryCode(), mCountryCodePicker.getFullNumber().replaceFirst(mCountryCodePicker.getSelectedCountryCode(), ""));
                mPresenter.sendCode(mCountryCodePicker.getSelectedCountryCode(), mCountryCodePicker.getFullNumber().replaceFirst(mCountryCodePicker.getSelectedCountryCode(), ""));
                break;
            case R.id.tv_temp:
                MainActivity.launch(this);
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

    @Override
    public void verificationCodeResult(int event, int result, Object data) {
        switch (event) {
            case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                if (result == SMSSDK.RESULT_COMPLETE) {
                    boolean smart = (Boolean)data;
                    if(smart) {
                        //通过Mob云验证
//                        ToastUtils.showLong("当前设备当天已验证过，直接通过云验证");
                    } else {
                        //依然走短信验证
                        ToastUtils.showLong("验证码发送成功");
                        mVerificationCodeInput.setEnabled(true);
                        mEtPhone.setFocusable(false);
                        mEtPhone.setFocusableInTouchMode(false);
                        mVerificationCodeInput.setFocusable(true);
                        mVerificationCodeInput.setFocusableInTouchMode(true);
                    }
                } else {
                    ToastUtils.showLong("验证码发送失败，当天验证次数10条限额已用完");
                    LogUtils.wTag(Constants.LOG_TAG, ((Throwable)data).getMessage());
                    mCountDownButton.removeCountDown();
                }
                break;
            case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                if (result == SMSSDK.RESULT_COMPLETE) {
                    MainActivity.launch(LoginActivity.this);
                } else {
                    ToastUtils.showLong("手机号验证失败");
                    LogUtils.wTag(Constants.LOG_TAG, ((Throwable)data).getMessage());
                    mVerificationCodeInput.setEnabled(true);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void submitCodeResult(int result) {
        if (result == SMSSDK.RESULT_COMPLETE) {
            ToastUtils.showLong("手机号验证成功");
            MainActivity.launch(LoginActivity.this);
        } else {
            ToastUtils.showLong("手机号验证失败");
            mVerificationCodeInput.setEnabled(true);
        }
    }

    @Override
    public void authorizeComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        ToastUtils.showLong(platform.getName() + " 授权成功");
        LogUtils.wTag(Constants.LOG_TAG, platform.getDb().exportData());
    }

    @Override
    public void authorizeError(Platform platform, int i, Throwable throwable) {
        ToastUtils.showLong(platform.getName() + " 授权失败");
    }

    @Override
    public void authorizeCancel(Platform platform, int i) {
        ToastUtils.showLong(platform.getName() + " 授权已取消");
    }

}
