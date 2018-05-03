package com.dashi1314.ssds.mvp.ui.fragment;

import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dashi1314.common.base.SimpleFragment;
import com.dashi1314.common.router.RouterConstants;
import com.dashi1314.ssds.R;

import butterknife.BindView;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

@Route(path = RouterConstants.PATH_SSDSHOME_FRAGMENT_HOME)
public class SsdsHomeMainFragment extends SimpleFragment {
    @BindView(R.id.et_code)
    EditText mEtCode;

    @Override
    protected int getLayoutId() {
        return R.layout.ssdshome_fragment_main;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.btn_send, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                sendCode("86", "15813368484");
                break;
            case R.id.btn_commit:
                String code = mEtCode.getText().toString();
                if (!StringUtils.isEmpty(code)) {
                    submitCode("86", "15813368484", code);
                }
                break;
        }
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
                } else{
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
                } else{
                    // TODO 处理错误的结果
                    ToastUtils.showLong("手机号验证失败");
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

}
