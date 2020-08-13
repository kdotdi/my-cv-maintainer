package com.example.kdotdi.common.extensions

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


fun Fragment.drawable(id: Int) = ContextCompat.getDrawable(requireContext(), id)

fun Fragment.string(
    id: Int,
    vararg params: Any = emptyArray()
) = if (params.isNullOrEmpty()) {
    getString(id)
} else {
    getString(id, *params)
}

fun Fragment.color(id: Int) = ContextCompat.getColor(requireContext(), id)
