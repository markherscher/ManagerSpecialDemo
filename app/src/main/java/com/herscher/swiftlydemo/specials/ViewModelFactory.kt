package com.herscher.swiftlydemo.specials

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.herscher.swiftlydemo.api.special.SpecialsManagerFactory
import com.herscher.swiftlydemo.util.StringResourceProvider

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SpecialsListViewModel::class.java)) {
            return SpecialsListViewModel(
                manager = SpecialsManagerFactory.create(),
                stringProvider = StringResourceProvider(context)
            ) as T
        }

        throw IllegalArgumentException("unhandled modelClass")
    }
}