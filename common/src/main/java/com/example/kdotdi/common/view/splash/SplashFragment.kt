package com.example.kdotdi.common.view.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kdotdi.presenter.splash.SplashPresenter
import com.example.kdotdi.presenter.splash.SplashView
import com.example.kdotdi.common.R
import com.example.kdotdi.common.base.BasePresenterFragment
import kotlinx.android.synthetic.main.fragment_splash.*
import pl.droidsonroids.gif.GifDrawable

abstract class SplashFragment : BasePresenterFragment<SplashPresenter, SplashView>(), SplashView {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_splash,
        container,
        false
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareGifImage()
    }

    private fun prepareGifImage() {
        val gifFromResource = GifDrawable(resources, R.drawable.splash_animation)
        gifFromResource.addAnimationListener {
            gifFromResource.stop()
            presenter.animationEnded()
        }
        gifImageView.setImageDrawable(gifFromResource)
    }

    //region BasePresenterFragment
    override fun onPresenterPrepared(fromStorage: Boolean) = Unit

    override fun presenterClass(): Class<SplashPresenter> {
        return SplashPresenter::class.java
    }
    //endregion
}
