package com.example.kdotdi.domain.mapper

import com.example.kdotdi.data.model.CvSummaryDTO
import com.example.kdotdi.domain.entity.CvSummary

fun CvSummaryDTO.toEntity() =
    CvSummary(
        imageUri = imageUri,
        name = name,
        summary = summary
    )

fun CvSummary.toDTO() =
    CvSummaryDTO(
        imageUri = imageUri,
        name = name,
        summary = summary
    )
