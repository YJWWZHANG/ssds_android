package com.dashi1314.ssds.mvp.ui.activity;

import android.os.Handler;

import com.dashi1314.common.base.SimpleActivity;
import com.dashi1314.ssds.R;

public class WelcomeActivity extends SimpleActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initEventAndData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LoginActivity.launch(WelcomeActivity.this);
            }
        }, 3000);
    }
}
