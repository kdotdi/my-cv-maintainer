package com.example.kdotdi.app.di

import com.example.kdotdi.app.view.MyCvMaintainerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun bindMyCvMaintainerActivity(): MyCvMaintainerActivity
}