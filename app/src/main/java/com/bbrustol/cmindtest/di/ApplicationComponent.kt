package com.bbrustol.cmindtest.di

import com.bbrustol.cmindtest.presentation.news.di.NewsActivityModule
import com.bbrustol.cmindtest.ApplicationConfiguration
import com.bbrustol.cmindtest.business.NewsBusiness
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,
                        AndroidSupportInjectionModule::class,
                        ViewModelFactoryModule::class,
                        RepositoryModule::class,
                        NewsBusiness::class,
                        UseCasesModule::class,
                        ViewModelModule::class,
                        NewsActivityModule::class])

interface ApplicationComponent {
    fun inject(app: ApplicationConfiguration)
}