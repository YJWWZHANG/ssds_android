package com.dashi1314.ssdsim.di.component;

import com.dashi1314.ssdsim.di.module.FragmentModule;
import com.dashi1314.ssdsim.di.scope.FragmentScope;
import com.dashi1314.ssdsim.mvp.ui.fragment.SsdsImMainFragment;

import dagger.Component;

@FragmentScope
@Component(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(SsdsImMainFragment ssdsImMainFragment);
}
