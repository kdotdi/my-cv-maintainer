package com.example.kdotdi.presenter.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class PresenterFactory : ViewModelProvider.Factory {

    var isNewCreated = false
        private set

    override fun <T : ViewModel> create(presenterClass: Class<T>): T {
        try {
            val presenter = createPresenter(presenterClass)
            isNewCreated = true
            return presenter
        } catch (throwable: Throwable) {
            throw IllegalArgumentException("Failed to create presenter: ${presenterClass.name}, exception: ${throwable.message}")
        }
    }

    abstract fun <T : ViewModel> createPresenter(presenterClass: Class<T>): T
}
