package com.example.kdotdi.domain.usecase.base

import com.example.kdotdi.domain.apiHandling.response.MyCvMaintainerResult

interface UseCaseHandler<P, T> {

    suspend fun execute(useCaseData: P): MyCvMaintainerResult<T> = MyCvMaintainerResult.Any
    suspend fun execute(): MyCvMaintainerResult<T> = MyCvMaintainerResult.Any
}
