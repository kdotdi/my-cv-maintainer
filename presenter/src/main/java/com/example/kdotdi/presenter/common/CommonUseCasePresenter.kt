package com.example.kdotdi.presenter.common

import androidx.annotation.VisibleForTesting
import com.example.kdotdi.presenter.base.BasePresenter
import com.example.kdotdi.presenter.base.BaseView
import com.example.kdotdi.presenter.BuildConfig

abstract class CommonUseCasePresenter<VIEW : BaseView>(
) : BasePresenter<VIEW>() {

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun isDevBuild() = BuildConfig.FLAVOR == DEV_ENV

    companion object{
        private const val DEV_ENV = "dev"
    }
}