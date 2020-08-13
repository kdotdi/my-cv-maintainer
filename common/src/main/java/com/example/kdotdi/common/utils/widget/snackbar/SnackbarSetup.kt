package com.example.kdotdi.common.utils.widget.snackbar

import android.view.View
import com.example.kdotdi.domain.entity.SnackbarData
import javax.inject.Inject

class SnackbarSetup @Inject constructor(
    private val snackbarManager: SnackbarManager
) : SnackbarRecipe {

    override fun addSnackbar(snackbarData: SnackbarData, view: View) {
        snackbarManager.addSnackbar(snackbarData, view)
    }
}