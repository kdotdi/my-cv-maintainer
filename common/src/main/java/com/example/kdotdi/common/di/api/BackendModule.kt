package com.example.kdotdi.common.di.api

import com.example.kdotdi.data.retrofit.MyCvMaintainerAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
object BackendModule {

    @Singleton
    @Provides
    fun provideBackendAPI(@Named(ApiModule.BACKEND) retrofit: Retrofit) =
        retrofit
            .create(MyCvMaintainerAPI::class.java)
}