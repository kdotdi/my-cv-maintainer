package com.example.kdotdi.data.network.error

import com.example.kdotdi.data.model.error.ErrorDTO

data class MyCvMaintainerApiException(
    val code: Int,
    val url: String,
    override val errorDTO: ErrorDTO?,
    override val originalException: Throwable? = null
) : MyCvMaintainerException()


