package com.example.kdotdi.app.di

import android.content.Context
import com.example.kdotdi.app.MyCvMaintainerApplication
import com.example.kdotdi.domain.di.DataSourceManagerModule
import com.example.kdotdi.common.di.*
import com.example.kdotdi.common.di.api.ApiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        (ApiModule::class),
        (DataSourceManagerModule::class),
        (AndroidInjectionModule::class),
        (ActivitiesModule::class),
        (FragmentsModule::class),
        (SnackbarModule::class)
    ]
)
interface ApplicationComponent {

    fun inject(myCvMaintainerApplication: MyCvMaintainerApplication)

    @Component.Builder
    interface Builder {
        fun build(): ApplicationComponent

        @BindsInstance
        fun context(context: Context): Builder
    }
}