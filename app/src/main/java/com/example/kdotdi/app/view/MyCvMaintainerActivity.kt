package com.example.kdotdi.app.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.example.kdotdi.app.R
import com.example.kdotdi.presenter.base.PresenterFactory
import com.example.kdotdi.presenter.main.MyCvMaintainerMainPresenter
import com.example.kdotdi.presenter.main.MyCvMaintainerMainView
import com.example.kdotdi.common.base.BasePresenterActivity
import com.example.kdotdi.common.extensions.fullScreenWithStatusBarTransparent
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Provider

class MyCvMaintainerActivity :
    BasePresenterActivity<MyCvMaintainerMainPresenter, MyCvMaintainerMainView>(),
    NavController.OnDestinationChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fullScreenWithStatusBarTransparent()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        presenter.onDestinationChanged()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        findNavController(R.id.mainNavHostFragment).addOnDestinationChangedListener(this)
    }

    override fun onDestroy() {
        findNavController(R.id.mainNavHostFragment).removeOnDestinationChangedListener(this)
        super.onDestroy()
    }

    override fun provideSnackbarAnchorView(): View = navHostContainer

    //region BasePresenterActivity
    @Inject
    lateinit var myCvMaintainerMainPresenter: Provider<MyCvMaintainerMainPresenter>

    override fun injectDependencies() = AndroidInjection.inject(this)

    override fun onPresenterPrepared(fromStorage: Boolean) = Unit

    override fun presenterClass(): Class<MyCvMaintainerMainPresenter> = MyCvMaintainerMainPresenter::class.java

    @Suppress("UNCHECKED_CAST")
    override fun prepareFactory(): PresenterFactory {
        return object : PresenterFactory() {
            override fun <T : ViewModel> createPresenter(presenterClass: Class<T>): T {
                return myCvMaintainerMainPresenter.get() as T
            }
        }
    }
    //endregion


}
