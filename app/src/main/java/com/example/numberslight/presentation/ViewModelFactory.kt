package com.example.numberslight.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory @Inject constructor(
    numbersListViewModelProvider: Provider<NumbersListViewModel>,
    numberDetailsViewModelProvider: Provider<NumberDetailsViewModel>,
) : ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        NumbersListViewModel::class.java to numbersListViewModelProvider,
        NumberDetailsViewModel::class.java to numberDetailsViewModelProvider
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return providers[modelClass]!!.get() as T
    }
}