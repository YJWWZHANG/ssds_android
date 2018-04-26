package com.dashi1314.ssds.di.component;

import com.dashi1314.ssds.di.module.ActivityModule;
import com.dashi1314.ssds.di.scope.ActivityScope;
import com.dashi1314.ssds.mvp.ui.activity.MainActivity;

import dagger.Component;

@ActivityScope
@Component(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
}
