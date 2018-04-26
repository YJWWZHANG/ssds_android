package com.dashi1314.ssdsim;

import android.app.Activity;
import android.content.Intent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dashi1314.common.base.SimpleActivity;
import com.dashi1314.common.router.RouterConstants;

@Route(path = RouterConstants.PATH_TEST)
public class SsdsImTestActivity extends SimpleActivity {

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, SsdsImTestActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ssdsim_activity_test;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
