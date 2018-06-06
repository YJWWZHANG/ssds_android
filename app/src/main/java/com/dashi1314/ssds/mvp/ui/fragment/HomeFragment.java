package com.dashi1314.ssds.mvp.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.dashi1314.common.base.SimpleFragment;
import com.dashi1314.common.bean.PayResult;
import com.dashi1314.common.router.RouterConstants;
import com.dashi1314.ssds.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

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
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Route(path = RouterConstants.PATH_SSDSMASTER_FRAGMENT_HOME)
public class HomeFragment extends SimpleFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.btn_share, R.id.btn_wechatpay, R.id.btn_alipay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_share:
                showShare();
                break;
            case R.id.btn_wechatpay:
                PayReq req = new PayReq();
                //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                req.appId			= "123456789";
                req.partnerId		= "123456789";
                req.prepayId		= "123456789";
                req.nonceStr		= "123456789";
                req.timeStamp		= "123456789";
                req.packageValue	= "123456789";
                req.sign			= "123456789";
                req.extData			= "app data"; // optional
                Toast.makeText(getActivity(), "正常调起支付", Toast.LENGTH_SHORT).show();
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                WXAPIFactory.createWXAPI(Utils.getApp(), "wxd930ea5d5a258f4f").sendReq(req);
                break;
            case R.id.btn_alipay:
                Observable.create(new ObservableOnSubscribe<Map<String, String>>() {
                    @Override
                    public void subscribe(ObservableEmitter<Map<String, String>> e) throws Exception {
                        PayTask payTask = new PayTask(getActivity());
                        Map<String, String> result = payTask.payV2("appid=\"2088011085074233\"&biz_content={\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"0.01\",\"subject\":\"1\",\"body\":\"我是测试数据\",\"out_trade_no\":\"123456789\"&charset=\"utf-8\"&method=\"alipay.trade.app.pay\"&sign_type=\"RSA2\"&timestamp=\"2016-07-29 16:55:53\"&version=\"1.0\"", true);
                        e.onNext(result);
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Map<String, String>>() {
                            @Override
                            public void accept(Map<String, String> stringStringMap) throws Exception {
                                PayResult payResult = new PayResult(stringStringMap);
                                /**
                                 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                                 */
                                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                                String resultStatus = payResult.getResultStatus();
                                // 判断resultStatus 为9000则代表支付成功
                                if (TextUtils.equals(resultStatus, "9000")) {
                                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                                    ToastUtils.showLong("支付成功");
                                } else {
                                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                                    ToastUtils.showLong("支付失败");
                                }

                            }
                        });
                break;
            default:
                break;
        }
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
