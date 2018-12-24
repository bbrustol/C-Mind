package com.bbrustol.cmindtest.mocks.di

import android.support.test.InstrumentationRegistry

class ViewModelFactoryMockRule(test: BaseTest) :
    DaggerTestRule<BaseTest, ViewModelFactoryMockComponent>(test, {
        val app =
            InstrumentationRegistry.getTargetContext().applicationContext as DaggerTestApplication
        DaggerViewModelFactoryMockComponent.builder().application(app).create(it)
    })