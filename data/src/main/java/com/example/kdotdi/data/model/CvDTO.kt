package com.example.kdotdi.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CvDTO(
    val summary: CvSummaryDTO,
    val positions: List<CvPositionDTO>?
)