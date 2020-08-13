package com.example.kdotdi.presenter.main

import com.example.kdotdi.presenter.common.CommonUseCasePresenter
import timber.log.Timber
import javax.inject.Inject

class MyCvMaintainerMainPresenter @Inject constructor(
) : CommonUseCasePresenter<MyCvMaintainerMainView>() {
    fun onDestinationChanged() {
        Timber.i("Destination changed.")
    }
}