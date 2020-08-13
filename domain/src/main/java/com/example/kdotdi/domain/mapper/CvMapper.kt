package com.example.kdotdi.domain.mapper

import com.example.kdotdi.data.model.CvDTO
import com.example.kdotdi.domain.entity.Cv

fun CvDTO.toEntity() =
    Cv(
        summary = summary.toEntity(),
        positions = positions?.map { it.toEntity() }
    )

fun Cv.toDTO() =
    CvDTO(
        summary = summary.toDTO(),
        positions = positions?.map { it.toDTO() }
    )