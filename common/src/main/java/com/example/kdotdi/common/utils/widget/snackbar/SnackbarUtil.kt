package com.example.kdotdi.common.utils.widget.snackbar

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.example.kdotdi.common.R

object SnackbarUtil {

    @JvmStatic
    fun showCustomSnackbar(
        view: View,
        @StringRes message: Int,
        action: Pair<String, View.OnClickListener>? = null,
        addDismissAction: Boolean = false,
        duration: Int,
        @ColorRes textColor: Int = 0,
        @ColorRes actionColor: Int = 0,
        @ColorRes backgroundColor: Int = 0,
        @DrawableRes icon: Int = 0,
        @DimenRes iconPadding: Int = 0
    ): Snackbar {

        val context = view.context

        val finalMessage = when {
            message == 0 -> context.resources.getString(R.string.general_error_label)
            else -> context.resources.getString(message)
        }

        return showCustomSnackbar(
            view,
            finalMessage,
            action,
            addDismissAction,
            duration,
            textColor,
            actionColor,
            backgroundColor,
            icon,
            iconPadding
        )
    }

    @JvmStatic
    fun showCustomSnackbar(
        view: View,
        notificationMessage: String,
        action: Pair<String, View.OnClickListener>? = null,
        addDismissAction: Boolean = false,
        duration: Int,
        @ColorRes textColor: Int = 0,
        @ColorRes actionColor: Int = 0,
        @ColorRes backgroundColor: Int = 0,
        @DrawableRes icon: Int = 0,
        @DimenRes iconPadding: Int = 0
    ): Snackbar {
        val context = view.context
        val snackbar = Snackbar.make(view, notificationMessage, duration)

        if (action != null) snackbar.setAction(action.first, action.second)

        if (addDismissAction) snackbar.setAction(R.string.general_ok) { snackbar.dismiss() }

        val root = snackbar.view
        val textView: TextView = root.findViewById(com.google.android.material.R.id.snackbar_text)
        textView.isSingleLine = false
        textView.gravity = Gravity.CENTER_VERTICAL

        if (backgroundColor != 0) root.setBackgroundResource(backgroundColor)

        context?.let {
            if (textColor != 0) {
                val color = ContextCompat.getColor(context, textColor)
                textView.setTextColor(color)
            }

            if (actionColor != 0) {
                val color = ContextCompat.getColor(context, actionColor)
                snackbar.setActionTextColor(color)
            }
        }

        if (icon != 0) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(icon, 0, 0, 0)

            if (iconPadding != 0 && context != null) {
                textView.compoundDrawablePadding = context.resources.getDimensionPixelOffset(iconPadding)
            }
        }

        snackbar.show()
        return snackbar
    }
}