package com.example.kdotdi.common.base

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.example.kdotdi.domain.entity.SnackbarData
import com.example.kdotdi.presenter.base.BasePresenter
import com.example.kdotdi.presenter.base.BaseView
import com.example.kdotdi.presenter.base.PresenterFactory
import com.example.kdotdi.common.R
import com.example.kdotdi.common.extensions.hideKeyboard
import com.example.kdotdi.common.utils.widget.snackbar.SnackbarSetup
import com.example.kdotdi.common.utils.widget.snackbar.SnackbarAccess
import javax.inject.Inject

abstract class BasePresenterFragment<P, V> :
    BaseFragment(),
    BaseView where V : BaseView, P : BasePresenter<V> {

    @Inject
    lateinit var snackbarApi: SnackbarSetup

    lateinit var presenter: P

    private lateinit var presenterFactory: PresenterFactory

    override fun onAttach(context: Context) {
        injectDependencies()
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenterFactory = prepareFactory()

        presenter = ViewModelProvider(this, presenterFactory).get(presenterClass())
        onPresenterPrepared(fromStorage = !presenterFactory.isNewCreated)

    }

    override fun onStart() {
        super.onStart()

        if (::presenter.isInitialized) {
            presenter.bind(this as V)
        }
    }

    override fun onStop() {
        if (::presenter.isInitialized) {
            presenter.unbind()
        }

        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        view?.hideKeyboard()
    }

    override fun showSnackbarNoInternetConnection() {
        val snackbarData = SnackbarData(
            R.string.general_nointernet_error_label,
            duration = Snackbar.LENGTH_LONG
        )

        val snackbarAnchorView = (activity as? SnackbarAccess)?.provideSnackbarAnchorView()
        snackbarAnchorView?.let { snackbarApi.addSnackbar(snackbarData, it) }
    }

    abstract fun onPresenterPrepared(fromStorage: Boolean)

    abstract fun presenterClass(): Class<P>

    abstract fun prepareFactory(): PresenterFactory

    abstract fun injectDependencies()
}