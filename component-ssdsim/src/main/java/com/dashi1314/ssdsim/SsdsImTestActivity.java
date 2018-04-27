package com.dashi1314.ssdsim;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.ToastUtils;
import com.dashi1314.common.base.SimpleActivity;
import com.dashi1314.common.router.RouterConstants;

@Route(path = RouterConstants.PATH_TEST)
public class SsdsImTestActivity extends SimpleActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.ssdsim_activity_test;
    }

    @Override
    protected void initEventAndData() {
        String key = getIntent().getStringExtra("key");
        ToastUtils.showLong(key);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
