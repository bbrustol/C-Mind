package com.bbrustol.cmindtest.di

import android.app.Application
import com.bbrustol.cmindtest.AppConfiguration
import com.bbrustol.cmindtest.di.provides.ProvidesFactory
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilder::class, ProvidesFactory::class]
)
interface AppComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: AppConfiguration)

    override fun inject(instance: DaggerApplication)
}
