package com.example.kdotdi.domain.usecase.util

import com.example.kdotdi.domain.datasource.global.GlobalDataSource
import javax.inject.Inject

class IsConnectionAvailableUseCase @Inject constructor(
    private val globalDataSource: GlobalDataSource
) {
    fun execute(): Boolean = globalDataSource.isInternetAvailable()
}