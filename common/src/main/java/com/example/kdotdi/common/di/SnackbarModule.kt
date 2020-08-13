package com.example.kdotdi.common.di

import com.example.kdotdi.common.utils.widget.snackbar.SnackbarSetup
import com.example.kdotdi.common.utils.widget.snackbar.SnackbarManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object SnackbarModule {

    @Singleton
    @Provides
    fun provideSnackbar(snackbarManager: SnackbarManager) = SnackbarSetup(snackbarManager)
}