package com.example.kdotdi.data.network.error

import java.io.IOException
import java.net.ProtocolException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.toMyCvMaintainerError(): MyCvMaintainerError = when (this) {
    is IOException -> toMyCvMaintainerError()
    else -> throw this
}

internal fun IOException.toMyCvMaintainerError(): MyCvMaintainerError = when (this) {
    is UnknownHostException -> MyCvMaintainerError.ServerUnavailable(originalException = this)
    is NoInternetException -> MyCvMaintainerError.NoInternetConnection(originalException = this)
    is SocketTimeoutException -> MyCvMaintainerError.SocketTimeout(originalException = this)
    is ProtocolException -> MyCvMaintainerError.ProtocolException(originalException = this)
    else -> throw this
}

