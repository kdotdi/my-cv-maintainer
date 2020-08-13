package com.example.kdotdi.domain.datasource.global

import com.example.kdotdi.data.interceptor.ConnectionChecker
import javax.inject.Inject

class GlobalDataSourceImpl @Inject constructor() : GlobalDataSource {

    override fun isInternetAvailable(): Boolean =
        ConnectionChecker.isInternetAvailableByDnsSocketConnect()
}