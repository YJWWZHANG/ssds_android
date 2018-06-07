package com.dashi1314.ssds.app;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.Utils;
import com.mob.MobSDK;

import cn.jpush.android.api.JPushInterface;

public class App extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);     // 尽可能早，推荐在Application中初始化

        MobSDK.init(this);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JPushInterface.setAlias(this, 10010, "zqb1");
    }

    private boolean isDebug() {
        return true;
    }

}
