package com.bbrustol.cmindtest.mocks.di

import dagger.android.AndroidInjector

interface DaggerTestComponent<T> : AndroidInjector<DaggerTestInjector> {

    fun injectTest(test: T)

    abstract class Builder : AndroidInjector.Builder<DaggerTestInjector>()
}