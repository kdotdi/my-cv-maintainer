package com.example.kdotdi.domain.apiHandling.response

import com.example.kdotdi.data.model.error.ErrorObjectDTO
import com.example.kdotdi.data.network.error.MyCvMaintainerApiException
import com.example.kdotdi.data.network.error.toMyCvMaintainerError
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.Response

fun <A : Any> Response<A>.bodyOrException(moshi: Moshi): A {

    val body = body()
    return if (isSuccessful && body != null) {
        body
    } else {
        val adapter: JsonAdapter<ErrorObjectDTO> = moshi.adapter(
            ErrorObjectDTO::class.java
        )

        val errorBody = errorBody()?.let {
            if (it.contentLength() == 0L) return@let null
            it.string()
        }

        val errorDTO = errorBody?.let {
            parseError(adapter, it)
        }?.errors?.firstOrNull()

        throw MyCvMaintainerApiException(
            code(),
            raw().request().url().toString(),
            errorDTO
        )
    }
}

private fun parseError(
    adapter: JsonAdapter<ErrorObjectDTO>,
    it: String
): ErrorObjectDTO? = try {
    adapter.lenient().fromJson(it)
} catch (throwable: Throwable) {
    throw throwable.toMyCvMaintainerError()
}

