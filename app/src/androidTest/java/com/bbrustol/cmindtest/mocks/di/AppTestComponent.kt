package com.bbrustol.cmindtest.di

import android.app.Application
import com.bbrustol.cmindtest.AppConfiguration
import com.bbrustol.cmindtest.di.provides.ProvidesFactory
import com.bbrustol.cmindtest.mocks.di.components.TestFragmentModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class,
    TestFragmentModule::class,
    ViewModelModule::class,
    ProvidesFactory::class,
    ViewModelFactoryModule::class
]
)
interface AppTestComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppTestComponent
    }

    fun inject(app: AppConfiguration)

    override fun inject(instance: DaggerApplication)
}