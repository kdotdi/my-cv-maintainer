package com.example.kdotdi.common.utils.widget.snackbar

import android.view.View
import com.example.kdotdi.domain.entity.SnackbarData

interface SnackbarRecipe {

    fun addSnackbar(snackbarData: SnackbarData, view: View)
}