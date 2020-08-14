package com.example.kdotdi.data.retrofit

import com.example.kdotdi.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface MyCvMaintainerAPI {

    @GET("bef1dbf3d035d7ae38f01f9943ec41b0/raw/61d592a9ec16613ea7a73663b9ef3e739342620d/CvSummary.json")
    suspend fun getCvSummary(): Response<CvSummaryDTO>

    @GET("d0a6e23ab6523d51a81ce2703616e0b3/raw/e04bf1c5b0045a18f6a06c867b4adc649b860004/CvPositions.json")
    suspend fun getCvPositions(): Response<CvPositionsDTO>
}