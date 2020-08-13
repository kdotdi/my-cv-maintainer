package com.example.kdotdi.domain.entity

import androidx.annotation.ColorRes
import androidx.annotation.StringRes

enum class SnackbarType {
    ERROR
}

private const val SNACKBAR_DEFAULT_LENGTH_DURATION_IN_MS = 3000

data class SnackbarData(
    @StringRes val message: Int = 0,
    val type: SnackbarType = SnackbarType.ERROR,
    val duration: Int = SNACKBAR_DEFAULT_LENGTH_DURATION_IN_MS,
    @ColorRes val backgroundColor: Int? = null
)