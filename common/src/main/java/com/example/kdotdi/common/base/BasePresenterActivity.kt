package com.example.kdotdi.common.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.kdotdi.presenter.base.BasePresenter
import com.example.kdotdi.presenter.base.BaseView
import com.example.kdotdi.presenter.base.PresenterFactory

abstract class BasePresenterActivity<P, V> :
    BaseActivity() where V : BaseView, P : BasePresenter<V> {

    lateinit var presenter: P

    private lateinit var presenterFactory: PresenterFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
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

    abstract fun onPresenterPrepared(fromStorage: Boolean)

    abstract fun presenterClass(): Class<P>

    abstract fun prepareFactory(): PresenterFactory
    
    abstract fun injectDependencies()
}