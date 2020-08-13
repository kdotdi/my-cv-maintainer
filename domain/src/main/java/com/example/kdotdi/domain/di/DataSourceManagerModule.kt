package com.example.kdotdi.domain.di

import com.example.kdotdi.domain.datasource.cv.CvDataSource
import com.example.kdotdi.domain.datasource.cv.CvDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class DataSourceManagerModule {
    @Binds
    abstract fun bindCvDataSourceImpl(cvDataSourceImpl: CvDataSourceImpl): CvDataSource
}