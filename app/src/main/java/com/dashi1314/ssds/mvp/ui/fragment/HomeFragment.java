package com.dashi1314.ssds.mvp.ui.fragment;

import android.view.View;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.dashi1314.common.base.SimpleFragment;
import com.dashi1314.common.router.RouterConstants;
import com.dashi1314.ssds.R;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

@Route(path = RouterConstants.PATH_SSDSMASTER_FRAGMENT_HOME)
public class HomeFragment extends SimpleFragment {
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.et_phone)
    EditText mEtPhone;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.btn_send, R.id.btn_commit, R.id.btn_share, R.id.btn_qq_login, R.id.btn_wechat_login, R.id.btn_sina_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                String phone = mEtPhone.getText().toString();
                if (!StringUtils.isSpace(phone)) {
                    sendCode("86", phone);
                } else {
                    ToastUtils.showShort("请输入手机号");
                }
                break;
            case R.id.btn_commit:
                String code = mEtCode.getText().toString();
                if (!StringUtils.isSpace(code)) {
                    submitCode("86", "15813368484", code);
                } else {
                    ToastUtils.showShort("请输入验证码");
                }
                break;
            case R.id.btn_share:
                showShare();
                break;
            case R.id.btn_wechat_login:
                Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
                wechat.SSOSetting(false);  //设置false表示使用SSO授权方式
                wechat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        ToastUtils.showLong("onComplete");
                        ToastUtils.showLong(platform.getDb().exportData());
                        LogUtils.w(platform.getDb().exportData());
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
            case R.id.btn_qq_login:
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.SSOSetting(false);  //设置false表示使用SSO授权方式
                qq.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                }); // 设置分享事件回调
                qq.authorize();//单独授权
                qq.showUser(null);//授权并获取用户信息
                break;
            case R.id.btn_sina_login:
                Platform sinaWiebo = ShareSDK.getPlatform(SinaWeibo.NAME);
                sinaWiebo.SSOSetting(false);  //设置false表示使用SSO授权方式
                sinaWiebo.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                }); // 设置分享事件回调
                sinaWiebo.authorize();//单独授权
                sinaWiebo.showUser(null);//授权并获取用户信息
                break;
            default:
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
                } else {
                    // TODO 处理错误的结果
                    ToastUtils.showLong("手机号验证失败");
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle("随身大师");
        // titleUrl QQ和QQ空间跳转链接
        oks.setTitleUrl("https://www.dashi1314.com/");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("命理真综，面相恒真，八极灵数、风水堪舆、易经命理、玄学预测");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImageUrl("https://www.dashi1314.com/images/banner02.jpg");
//        oks.setImagePath("/sdcard/test.png");//确保SDcard下面存在此张图片
//        oks.setImagePath("file:///android_asset/test.png");//确保SDcard下面存在此张图片
        // url在微信、微博，Facebook等平台中使用
        oks.setUrl("https://www.dashi1314.com/");
        // comment是我对这条分享的评论，仅在人人网使用
        oks.setComment("欢迎来到随身大师");
        // 启动分享GUI
        oks.show(Utils.getApp());
    }

}
