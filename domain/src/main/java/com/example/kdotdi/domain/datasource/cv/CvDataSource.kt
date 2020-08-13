package com.example.kdotdi.domain.datasource.cv

import com.example.kdotdi.domain.apiHandling.response.MyCvMaintainerResult
import com.example.kdotdi.domain.entity.*

interface CvDataSource {
    suspend fun getCvSummary(): MyCvMaintainerResult<CvSummary>
}