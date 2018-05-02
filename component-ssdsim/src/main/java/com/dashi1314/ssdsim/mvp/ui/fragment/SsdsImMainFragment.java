package com.dashi1314.ssdsim.mvp.ui.fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dashi1314.common.base.BaseFragment;
import com.dashi1314.common.router.RouterConstants;
import com.dashi1314.ssdsim.R;
import com.dashi1314.ssdsim.di.component.DaggerFragmentComponent;
import com.dashi1314.ssdsim.di.module.FragmentModule;
import com.dashi1314.ssdsim.mvp.contract.SsdsImMainContract;
import com.dashi1314.ssdsim.mvp.presenter.SsdsImMainPresenter;

@Route(path = RouterConstants.PATH_SSDSIM_MAIN)
public class SsdsImMainFragment extends BaseFragment<SsdsImMainPresenter> implements SsdsImMainContract.View{
    @Override
    protected void initInject() {
        DaggerFragmentComponent.builder().fragmentModule(new FragmentModule()).build().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ssdsim_fragment_main;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void loading() {

    }

    @Override
    public void dismissLoading() {

    }
}
