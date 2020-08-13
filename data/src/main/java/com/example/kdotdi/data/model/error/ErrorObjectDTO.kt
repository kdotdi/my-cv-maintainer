package com.example.kdotdi.data.model.error

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = false)
data class ErrorObjectDTO(
    val errors: Array<ErrorDTO>?
)

@JsonClass(generateAdapter = false)
data class ErrorDTO(
    val code: String,
    val title: String,
    val detail: String
)