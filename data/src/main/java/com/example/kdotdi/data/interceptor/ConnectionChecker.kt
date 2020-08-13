package com.example.kdotdi.data.interceptor

import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.UnknownHostException

object ConnectionChecker {
    private const val HOSTNAME_TO_CHECK_AGAINST = "dns.google"
    private const val TIMEOUT_IN_MS = 10000
    private const val PORT = 53 // DNS port 53 is used because we are connecting to a DNS server

    fun isInternetAvailableByDnsSocketConnect(): Boolean {
        return try {
            val socket = Socket()
            val address = InetSocketAddress(HOSTNAME_TO_CHECK_AGAINST, PORT)
            socket.connect(address, TIMEOUT_IN_MS)
            socket.close()
            true
        } catch (unknownHost: UnknownHostException) {
            // no IP address for the host could be found - usually user's DNS server failure
            false
        } catch (ioException: IOException) {
            // socket operations' failure
            false
        }
    }
}