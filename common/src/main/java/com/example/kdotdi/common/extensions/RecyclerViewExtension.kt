package com.example.kdotdi.common.extensions

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kdotdi.common.R

fun RecyclerView.applyDivider(context: Context, rect: Rect) {
    ContextCompat
        .getDrawable(context, R.drawable.drawable_separator)
        ?.let {
            val insetDivider = createInsetDivider(it, rect)
            val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            divider.setDrawable(insetDivider)
            this.addItemDecoration(divider)
        }
}

fun createInsetDivider(it: Drawable, rect: Rect): InsetDrawable =
    InsetDrawable(it, rect.left, rect.top, rect.right, rect.bottom)