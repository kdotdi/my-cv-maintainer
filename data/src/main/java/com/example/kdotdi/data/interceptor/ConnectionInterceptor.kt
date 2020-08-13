package com.example.kdotdi.data.interceptor

import com.example.kdotdi.data.network.error.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectionInterceptor : Interceptor {
    @Throws(NoInternetException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!ConnectionChecker.isInternetAvailableByDnsSocketConnect()) {
            throw NoInternetException()
        }
        return chain.proceed(chain.request())
    }
}