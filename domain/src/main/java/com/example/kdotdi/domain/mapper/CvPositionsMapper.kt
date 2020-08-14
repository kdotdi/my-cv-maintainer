package com.example.kdotdi.domain.mapper

import com.example.kdotdi.data.model.CvPositionsDTO
import com.example.kdotdi.domain.entity.CvPositions

fun CvPositionsDTO.toEntity() =
    CvPositions(
        positions = positions?.map { it.toEntity() }
    )

fun CvPositions.toDTO() =
    CvPositionsDTO(
        positions = positions?.map { it.toDTO() }
    )