package com.example.kdotdi.app.di


import com.example.kdotdi.app.view.cv.CvFragment
import com.example.kdotdi.app.view.splash.SplashFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsModule {
    @ContributesAndroidInjector
    abstract fun bindSplashFragment(): SplashFragment

    @ContributesAndroidInjector
    abstract fun bindCvFragment(): CvFragment
}
