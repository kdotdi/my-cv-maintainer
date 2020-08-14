package com.example.kdotdi.domain.usecase.cv

import com.example.kdotdi.domain.apiHandling.response.MyCvMaintainerResult
import com.example.kdotdi.domain.datasource.cv.CvDataSource
import com.example.kdotdi.domain.entity.CvPositions
import com.example.kdotdi.domain.entity.CvSummary
import com.example.kdotdi.domain.usecase.base.UseCaseHandler
import javax.inject.Inject

class GetCvPositionsUseCase @Inject constructor(
    private val cvDataSource: CvDataSource
) : UseCaseHandler<Nothing, CvPositions> {

    override suspend fun execute(): MyCvMaintainerResult<CvPositions> =
        cvDataSource.getCvPositions()
}