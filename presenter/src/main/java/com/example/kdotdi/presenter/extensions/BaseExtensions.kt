package com.example.kdotdi.presenter.extensions

import com.example.kdotdi.data.network.error.MyCvMaintainerError
import com.example.kdotdi.domain.apiHandling.response.MyCvMaintainerResult
import com.example.kdotdi.domain.apiHandling.response.doOnError
import com.example.kdotdi.domain.usecase.base.UseCaseHandler
import com.example.kdotdi.presenter.base.BasePresenter
import com.example.kdotdi.presenter.base.BaseView
import kotlinx.coroutines.launch

fun <P, T, VIEW : BaseView> BasePresenter<VIEW>.launchUseCase(
    useCase: UseCaseHandler<P, T>,
    useCaseData: P? = null,
    useCaseCall: suspend MyCvMaintainerResult<T>.() -> Unit
) = launch {
    val useCaseResult = if (useCaseData == null) useCase.execute()
    else useCase.execute(useCaseData)

    useCaseCall.invoke(useCaseResult)

    useCaseResult.doOnError { myCvMaintainerError ->
        when (myCvMaintainerError) {
            is MyCvMaintainerError.NoInternetConnection -> showSnackbarNoInternetConnection()
        }
    }
}
