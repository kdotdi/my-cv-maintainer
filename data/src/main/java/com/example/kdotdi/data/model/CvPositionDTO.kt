package com.example.kdotdi.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CvPositionDTO(
    val title: String,
    val description: String
)
