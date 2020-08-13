package com.example.kdotdi.data.network.error

import com.example.kdotdi.data.model.error.ErrorDTO
import java.io.IOException

data class NoInternetException(override val cause: Throwable? = null) : IOException()

sealed class MyCvMaintainerError : MyCvMaintainerException() {
    data class ServerUnavailable(override val errorDTO: ErrorDTO? = null, override val originalException: Throwable? = null) : MyCvMaintainerError()
    data class NoInternetConnection(override val errorDTO: ErrorDTO? = null, override val originalException: Throwable? = null) : MyCvMaintainerError()
    data class SocketTimeout(override val errorDTO: ErrorDTO? = null, override val originalException: Throwable? = null) : MyCvMaintainerError()
    data class ProtocolException(override val errorDTO: ErrorDTO? = null, override val originalException: Throwable? = null) : MyCvMaintainerError()
}