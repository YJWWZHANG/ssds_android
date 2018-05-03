package com.dashi1314.ssds.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.dashi1314.ssds.R;
import com.dashi1314.ssds.service.PushMessageIntentService;
import com.mob.MobSDK;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);     // 尽可能早，推荐在Application中初始化

        initCloudChannel(this);

        MobSDK.init(this);
    }

    private boolean isDebug() {
        return true;
    }

    /**
     * 初始化云推送通道
     * @param
     * @param app
     */
    public void initCloudChannel(Application app) {
        PushServiceFactory.init(app);
        final CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.setPushIntentService(PushMessageIntentService.class);
        pushService.setNotificationLargeIcon(ImageUtils.getBitmap(R.mipmap.ic_launcher_round));
        pushService.setNotificationSmallIcon(R.mipmap.ic_launcher_round);
        pushService.register(app, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                LogUtils.w("init cloudchannel success");
                ToastUtils.showLong("" + pushService.getDeviceId());
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                LogUtils.w("init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
        pushService.bindAccount("734057028", new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                LogUtils.w(s);
            }

            @Override
            public void onFailed(String s, String s1) {
                LogUtils.w(s, s1);
            }
        });
    }

}
