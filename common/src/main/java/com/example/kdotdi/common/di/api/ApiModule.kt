package com.example.kdotdi.common.di.api

import com.example.kdotdi.data.BuildConfig
import com.example.kdotdi.data.interceptor.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.Collections.singletonList
import javax.inject.Named
import javax.inject.Singleton

@Module(
    includes = [
        BackendModule::class
    ]
)
object ApiModule {
    const val BACKEND = "backend"

    @Provides
    @Named(BACKEND)
    fun provideBackendApiPath(): String =
        BuildConfig.API_PATH

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Timber.i(it)
        }).setLevel(HttpLoggingInterceptor.Level.BODY)

    @Singleton
    @Provides
    fun provideConnectionInterceptor(): ConnectionInterceptor = ConnectionInterceptor()

    @Singleton
    @Provides
    @Named(BACKEND)
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        connectionInterceptor: ConnectionInterceptor
    ) =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(connectionInterceptor)
            .protocols(singletonList(Protocol.HTTP_1_1))
            .build()

    @Singleton
    @Provides
    @Named(BACKEND)
    fun provideRetrofit(
        @Named(BACKEND) okHttpClient: OkHttpClient,
        moshi: Moshi,
        @Named(BACKEND) apiPath: String
    ): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(apiPath)
            .build()
}