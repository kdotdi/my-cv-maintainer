package com.example.kdotdi.presenter.splash

import com.example.kdotdi.presenter.common.CommonUseCasePresenter
import timber.log.Timber
import javax.inject.Inject

class SplashPresenter @Inject constructor(
) : CommonUseCasePresenter<SplashView>() {

    fun animationEnded() {
        Timber.i("Animation ended")
        displayCv()
    }

    fun displayCv() {
        Timber.i("Displaying CV")
        present { displayCv() }
    }
}