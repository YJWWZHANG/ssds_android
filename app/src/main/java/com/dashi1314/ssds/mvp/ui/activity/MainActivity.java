package com.dashi1314.ssds.mvp.ui.activity;

import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.AppUtils;
import com.dashi1314.common.base.BaseActivity;
import com.dashi1314.common.base.NullFragment;
import com.dashi1314.common.router.RouterConstants;
import com.dashi1314.ssds.R;
import com.dashi1314.ssds.di.component.DaggerActivityComponent;
import com.dashi1314.ssds.di.module.ActivityModule;
import com.dashi1314.ssds.mvp.contract.MainContract;
import com.dashi1314.ssds.mvp.presenter.MainPresenter;
import com.dashi1314.ssdsim.mvp.ui.fragment.SsdsImMainFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.HashMap;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    private String mHideFragment = "/ssdsim/1";
    private String mShowFragment = "/ssdsim/1";

    private HashMap<String, Class<? extends SupportFragment>> mLoadFragments = new HashMap<>();

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        if (mLoadFragments.get("/ssdsim/1") == null) {
            loadMultipleRootFragment(R.id.fl_content, 0, getTargetFragment("/ssdsim/1"), getTargetFragment(RouterConstants.PATH_SSDSIM_MAIN), getTargetFragment("/ssdsim/3"));
        }

        mTvTitle.setText(R.string.app_name);
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                String title = "";
                switch (tabId) {
                    case R.id.nav_1:
                        mShowFragment = "/ssdsim/1";
                        title = "1";
                        break;
                    case R.id.nav_2:
                        mShowFragment = RouterConstants.PATH_SSDSIM_MAIN;
                        title = "2";
                        break;
                    case R.id.nav_3:
                        mShowFragment = "/ssdsim/3";
                        title = "3";
                        break;
                    default:
                        break;
                }
                showHideFragment(getTargetFragment(mShowFragment), getTargetFragment(mHideFragment));
                mTvTitle.setText(title);
                mHideFragment = mShowFragment;
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        AppUtils.exitApp();
    }

    private SupportFragment getTargetFragment(String path) {
        if (mLoadFragments.get(path) != null && findFragment(mLoadFragments.get(path)) != null) {
            return findFragment(mLoadFragments.get(path));
        } else {
            SupportFragment fragment = (SupportFragment) (ARouter.getInstance().build(path).navigation());
            if (fragment == null) {
                fragment = new NullFragment();
            }
            mLoadFragments.put(path, fragment.getClass());
            return fragment;
        }
    }

    @Override
    public void loading() {

    }

    @Override
    public void dismissLoading() {

    }


    @Override
    public void setTest(String s) {

    }

}
