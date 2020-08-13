package com.example.kdotdi.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CvSummaryDTO(
    val imageUri: String,
    val name: String,
    val summary: String
)