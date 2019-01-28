package com.bbrustol.cmindtest.mocks.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactoryMock @Inject constructor() : ViewModelProvider.Factory {

    private val creators = mutableMapOf<Class<out ViewModel>, ViewModel>()
    override fun <T : ViewModel> create(viewModelClass: Class<T>): T {
        var creator: ViewModel? = creators[viewModelClass]

        if (creator == null) {
            for ((key, value) in creators) {
                if (viewModelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class $viewModelClass")
        }
        try {
            return viewModelClass.cast(creator)!!
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}