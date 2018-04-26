package com.dashi1314.ssdsim;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.dashi1314.common.base.SimpleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SsdsImMainActivity extends SimpleActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.layout_load_fragment)
    FrameLayout mLayoutLoadFragment;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    public static void launch(Activity activity) {
        activity.startActivity(new Intent(activity, SsdsImMainActivity.class));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_runalone;
    }

    @Override
    protected void initEventAndData() {
        setSupportActionBar(mToolbar);
        mTvTitle.setText("随身大师-IM模块");
    }

}
