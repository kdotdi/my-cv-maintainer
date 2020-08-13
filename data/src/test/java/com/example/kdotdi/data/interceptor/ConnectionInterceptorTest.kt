package com.example.kdotdi.data.interceptor

import com.example.kdotdi.data.network.error.NoInternetException
import io.mockk.*
import io.mockk.impl.annotations.MockK
import okhttp3.Request
import okhttp3.Response
import okhttp3.internal.http.RealInterceptorChain
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ConnectionInterceptorTest {
    lateinit var connectionInterceptor: ConnectionInterceptor

    @MockK
    lateinit var chain: RealInterceptorChain
    @MockK
    lateinit var request: Request
    @MockK
    lateinit var response: Response

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        connectionInterceptor = spyk(
            ConnectionInterceptor()
        )

        mockkObject(ConnectionChecker)
    }

    @Test
    fun interceptPositive() {
        every { ConnectionChecker.isInternetAvailableByDnsSocketConnect() } returns true
        every { chain.request() } returns request
        every { chain.proceed(request) } returns response

        connectionInterceptor.intercept(chain)

        verify(exactly = 1) { ConnectionChecker.isInternetAvailableByDnsSocketConnect() }
        verify(exactly = 1) { connectionInterceptor.intercept(chain) }
    }

    @Test
    fun interceptNegative() {
        every { ConnectionChecker.isInternetAvailableByDnsSocketConnect() } returns false
        every { chain.request() } returns request
        every { chain.proceed(request) } returns response

        try {
            connectionInterceptor.intercept(chain)
        } catch (e: Exception) {
            verify(exactly = 1) { ConnectionChecker.isInternetAvailableByDnsSocketConnect() }
            verify(exactly = 1) { connectionInterceptor.intercept(chain) }
            assertTrue(e is NoInternetException)
        }
    }
}