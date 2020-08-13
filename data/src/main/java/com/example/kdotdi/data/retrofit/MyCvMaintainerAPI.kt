package com.example.kdotdi.data.retrofit

import com.example.kdotdi.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface MyCvMaintainerAPI {

    @GET("bef1dbf3d035d7ae38f01f9943ec41b0/raw/cf869d84b0c6018e5f7f450ee4398d4cdc36861f/CvSummary.json")
    suspend fun getCvSummary(): Response<CvSummaryDTO>
}