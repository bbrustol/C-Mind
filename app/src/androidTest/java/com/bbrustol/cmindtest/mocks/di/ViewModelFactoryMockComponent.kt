package com.bbrustol.cmindtest.mocks.di

import android.app.Application
import com.bbrustol.cmindtest.di.AppModule
import com.bbrustol.cmindtest.di.ViewModelFactoryModule
import com.bbrustol.cmindtest.di.ViewModelModule
import com.bbrustol.cmindtest.mocks.di.components.TestFragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
            AppModule::class,
            TestFragmentModule::class,
            ViewModelModule::class,
            ViewModelFactoryModule::class
    ]
)

interface ViewModelFactoryMockComponent : DaggerTestComponent<BaseTest> {
    @Component.Builder
    abstract class Builder : DaggerTestComponent.Builder() {
        @BindsInstance
        abstract fun application(app: Application): Builder
    }
}