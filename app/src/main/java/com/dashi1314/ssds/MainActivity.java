package com.dashi1314.ssds;

import android.os.Bundle;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dashi1314.common.base.SimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends SimpleActivity {

    @BindView(R.id.tv_test)
    TextView mTvTest;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        mTvTest.setText(ActivityUtils.getActivityList().size() + " 随身大师");
    }

    @OnClick(R.id.tv_test)
    public void onViewClicked() {
        AppUtils.exitApp();
    }
}
