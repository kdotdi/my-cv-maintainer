package com.example.kdotdi.domain.apiHandling.response

import com.example.kdotdi.data.network.error.MyCvMaintainerError
import com.example.kdotdi.data.network.error.toMyCvMaintainerError

sealed class MyCvMaintainerResult<out T> {

    data class Success<out T>(val data: T) : MyCvMaintainerResult<T>()
    data class Failure(val error: MyCvMaintainerError) : MyCvMaintainerResult<Nothing>()
    object Any : MyCvMaintainerResult<Nothing>()
}

inline infix fun <T, R> MyCvMaintainerResult<T>.map(apply: (T) -> R): MyCvMaintainerResult<R> = when (this) {
    is MyCvMaintainerResult.Failure -> this
    is MyCvMaintainerResult.Success -> MyCvMaintainerResult.Success(apply(data))
    is MyCvMaintainerResult.Any -> this
}

inline infix fun <T> MyCvMaintainerResult<T>.doOnSuccess(apply: (T) -> Unit): MyCvMaintainerResult<T> {
    if (this is MyCvMaintainerResult.Success) {
        apply(data)
    }
    return this
}

inline infix fun <T> MyCvMaintainerResult<T>.doOnError(apply: (MyCvMaintainerError) -> Unit): MyCvMaintainerResult<T> {
    if (this is MyCvMaintainerResult.Failure) {
        apply(error)
    }
    return this
}

infix fun <T, R> MyCvMaintainerResult<(T) -> R>.apply(apply: MyCvMaintainerResult<T>): MyCvMaintainerResult<R> = when (this) {
    is MyCvMaintainerResult.Failure -> this
    is MyCvMaintainerResult.Success -> apply.map(this.data)
    is MyCvMaintainerResult.Any -> this
}

fun <T> success(data: T) = MyCvMaintainerResult.Success(data)

fun failure(myCvMaintainerError: MyCvMaintainerError) = MyCvMaintainerResult.Failure(myCvMaintainerError)

inline fun <T> safeCall(call: () -> T): MyCvMaintainerResult<T> = try {
    MyCvMaintainerResult.Success(call.invoke())
} catch (exception: Exception) {
    val myCvMaintainerException = exception.toMyCvMaintainerError()

    MyCvMaintainerResult.Failure(myCvMaintainerException)
}