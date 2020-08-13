package com.example.kdotdi.domain.datasource.cv

import com.example.kdotdi.data.retrofit.MyCvMaintainerAPI
import com.example.kdotdi.domain.apiHandling.response.MyCvMaintainerResult
import com.example.kdotdi.domain.apiHandling.response.bodyOrException
import com.example.kdotdi.domain.apiHandling.response.safeCall
import com.example.kdotdi.domain.entity.*
import com.example.kdotdi.domain.mapper.*
import com.squareup.moshi.Moshi
import javax.inject.Inject

class CvDataSourceImpl @Inject constructor(
    private val myCvMaintainerAPI: MyCvMaintainerAPI,
    private val moshi: Moshi
) : CvDataSource {

    override suspend fun getCvSummary(): MyCvMaintainerResult<CvSummary> =
        safeCall {
            myCvMaintainerAPI.getCvSummary().bodyOrException(moshi).toEntity()
        }
}