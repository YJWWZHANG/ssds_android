package com.dashi1314.ssds.di.component;

import com.dashi1314.ssds.ui.main.MainActivity;

import dagger.Component;

@Component
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
}
