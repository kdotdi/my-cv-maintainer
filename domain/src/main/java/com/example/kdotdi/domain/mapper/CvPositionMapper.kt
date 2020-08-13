package com.example.kdotdi.domain.mapper

import com.example.kdotdi.data.model.CvPositionDTO
import com.example.kdotdi.domain.entity.CvPosition

fun CvPositionDTO.toEntity() =
    CvPosition(
        title = title,
        description = description
    )

fun CvPosition.toDTO() =
    CvPositionDTO(
        title = title,
        description = description
    )
