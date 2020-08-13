package com.example.kdotdi.domain.entity

data class Cv(
    val summary: CvSummary,
    val positions: List<CvPosition>?
)