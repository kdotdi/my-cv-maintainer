package com.example.kdotdi.common.utils.widget.snackbar

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.example.kdotdi.domain.entity.SnackbarData
import com.example.kdotdi.domain.entity.SnackbarType
import com.example.kdotdi.common.R
import javax.inject.Inject

class SnackbarManager @Inject constructor() {

    private var currentSnackbar: Snackbar? = null

    fun addSnackbar(
        snackbarData: SnackbarData,
        view: View
    ) {
        show(snackbarData, view)
    }

    private fun show(
        data: SnackbarData,
        view: View?,
        addDismissAction: Boolean = true,
        action: Pair<String, View.OnClickListener>? = null
    ) {
        if (view == null) return

        var iconId = 0
        var backgroundId = 0

        when (data.type) {
            SnackbarType.ERROR -> {
                backgroundId = R.color.global_red
                iconId = 0
            }
        }
        currentSnackbar = SnackbarUtil.showCustomSnackbar(
            view,
            data.message,
            action = action,
            duration = data.duration,
            addDismissAction = addDismissAction,
            textColor = R.color.global_white,
            backgroundColor = backgroundId,
            actionColor = R.color.global_white,
            icon = iconId,
            iconPadding = R.dimen.margin_12dp
        )
    }
}