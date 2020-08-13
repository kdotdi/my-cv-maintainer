package com.example.kdotdi.data.network.error

import com.example.kdotdi.data.model.error.ErrorDTO

abstract class MyCvMaintainerException : Exception() {
    abstract val originalException: Throwable?
    abstract val errorDTO: ErrorDTO?
}