package com.example.kdotdi.app

import android.app.Application
import com.example.kdotdi.app.di.DaggerApplicationComponent
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class MyCvMaintainerApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent.builder()
            .context(this)
            .build()
            .inject(this)

        val builder: Picasso.Builder = Picasso.Builder(baseContext)
            .indicatorsEnabled(false)
            .downloader(OkHttp3Downloader(baseContext))
        builder.listener { _, _, exception -> exception.printStackTrace() }

        val picasso: Picasso = builder.build()
        Picasso.setSingletonInstance(picasso)

        if (BuildConfig.DEBUG) {
            builder.loggingEnabled(true)
            Picasso.get().isLoggingEnabled = true
            Picasso.get().setIndicatorsEnabled(true)
        }

        Timber.plant(Timber.DebugTree())
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}
