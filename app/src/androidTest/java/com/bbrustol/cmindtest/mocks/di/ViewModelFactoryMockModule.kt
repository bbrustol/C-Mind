package br.com.userede.di

import android.arch.lifecycle.ViewModelProvider
import com.bbrustol.cmindtest.mocks.di.ViewModelFactoryMock
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryMockModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactoryMock): ViewModelProvider.Factory
}