package com.example.kdotdi.app.view.cv

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView

class SwipePositionHandler(
    private val addPositionAdapter: AddPositionAdapter,
    private val onSwipeBackground: ColorDrawable,
    private val binIcon: Drawable?
) : SwipeToDeletePositionCallback() {
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        addPositionAdapter.onPositionRemove(viewHolder.layoutPosition)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ) = if (addPositionAdapter.data.size == 1 && viewHolder.layoutPosition == 0) 0
    else super.getMovementFlags(recyclerView, viewHolder)

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        with(viewHolder.itemView) {
            onSwipeBackground.setBounds(
                right + dX.toInt(),
                top,
                right,
                bottom
            )
        }
        onSwipeBackground.draw(c)
        binIcon?.let {
            with(viewHolder.itemView) {
                val binIconStartOffset =
                    (BIN_ICON_START_OFFSET_WIDTH_MULTIPLIER * it.intrinsicWidth).toInt()
                val binIconEndOffset = binIconStartOffset - it.intrinsicWidth
                val binIconHalfHeight = it.intrinsicHeight / 2
                val itemViewCenter = (top + bottom) / 2
                it.setBounds(
                    right - binIconStartOffset,
                    itemViewCenter - binIconHalfHeight,
                    right - binIconEndOffset,
                    itemViewCenter + binIconHalfHeight
                )
            }
            it.draw(c)
        }
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    companion object {
        private const val BIN_ICON_START_OFFSET_WIDTH_MULTIPLIER = 1.5
    }
}