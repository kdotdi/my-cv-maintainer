package com.example.kdotdi.common.utils.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class NoTouchBottomSheetBehavior<V : View>(ctx: Context, attrs: AttributeSet) :
    BottomSheetBehavior<V>(ctx, attrs) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <V : View> from(view: V): NoTouchBottomSheetBehavior<V> {
            val params = view.layoutParams as? CoordinatorLayout.LayoutParams
                ?: throw IllegalArgumentException(VIEW_NOT_CHILD_COORDINATOR_EXCEPTION_DESCRIPTION)
            val behavior = params.behavior as? NoTouchBottomSheetBehavior<*>
                ?: throw IllegalArgumentException(VIEW_NOT_ASSOCIATED_EXCEPTION_DESCRIPTION)
            return behavior as NoTouchBottomSheetBehavior<V>
        }

        private const val VIEW_NOT_CHILD_COORDINATOR_EXCEPTION_DESCRIPTION =
            "The view is not a child of CoordinatorLayout"
        private const val VIEW_NOT_ASSOCIATED_EXCEPTION_DESCRIPTION =
            "The view is not associated with BottomSheetBehavior"
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: V,
        event: MotionEvent
    ): Boolean = false

    override fun onTouchEvent(
        parent: CoordinatorLayout,
        child: V, event: MotionEvent
    ): Boolean = false

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean = false

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) = Unit

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View,
        type: Int
    ) = Unit

    override fun onNestedPreFling(
        coordinatorLayout: CoordinatorLayout,
        child: V,
        target: View,
        velocityX: Float,
        velocityY: Float
    ): Boolean = false
}