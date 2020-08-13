package com.example.kdotdi.app.view.splash

import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.kdotdi.app.R
import com.example.kdotdi.presenter.base.PresenterFactory
import com.example.kdotdi.presenter.splash.SplashPresenter
import com.example.kdotdi.common.view.splash.SplashFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import javax.inject.Provider

class SplashFragment : SplashFragment() {

    override fun displayCv() {
        findNavController().navigate(R.id.action_splashFragment_to_cvFragment)
    }

    //region BasePresenterFragment
    @Inject
    lateinit var personalSplashPresenterProvider: Provider<SplashPresenter>

    override fun prepareFactory(): PresenterFactory {
        return object : PresenterFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> createPresenter(presenterClass: Class<T>): T {
                return personalSplashPresenterProvider.get() as T
            }
        }
    }

    override fun injectDependencies() {
        AndroidSupportInjection.inject(this)
    }
    //endregion
}