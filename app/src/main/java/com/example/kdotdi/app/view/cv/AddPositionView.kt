package com.example.kdotdi.app.view.cv

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.example.kdotdi.domain.entity.CvPosition
import com.example.kdotdi.presenter.cv.CvPresenter
import com.example.kdotdi.common.extensions.*
import com.example.kdotdi.common.utils.widget.NoTouchBottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_add_positions.view.*

sealed class AddPositionAction {
    object CancelAction : AddPositionAction()
    object SaveAction : AddPositionAction()
    object AddAction : AddPositionAction()
}

class AddPositionView {

    private lateinit var viewGroup: ViewGroup

    private val addPositionAdapter =
        AddPositionAdapter()
    private val displayPositionAdapter =
        DisplayPositionAdapter()
    private lateinit var addPositionBottomSheetBehavior: NoTouchBottomSheetBehavior<ViewGroup>
    private lateinit var onSwipeBackground: ColorDrawable

    private val onStateChanged = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                addPositionBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    fun attach(
        viewGroup: ViewGroup,
        mirroringRecycler: RecyclerView,
        positionList: MutableList<CvPosition>,
        swipeBackgroundColor: Int,
        binIcon: Drawable?,
        onAction: (addPositionAction: AddPositionAction) -> Unit
    ) {
        this.viewGroup = viewGroup
        this.onSwipeBackground = ColorDrawable(swipeBackgroundColor)
        addPositionBottomSheetBehavior =
            NoTouchBottomSheetBehavior.from(viewGroup.containerBottomSheetPosition)
        addPositionBottomSheetBehavior.apply {
            state = BottomSheetBehavior.STATE_HIDDEN
            setBottomSheetCallback(onStateChanged)
        }
        viewGroup.apply {
            imageAddPositionClose.setOnClickListener {
                onAction.invoke(AddPositionAction.CancelAction)
            }
            buttonCvPositionsClear.setOnClickListener {
                onAction.invoke(AddPositionAction.CancelAction)
            }
            buttonCvPositionsSave.setOnClickListener {
                onAction.invoke(AddPositionAction.SaveAction)
            }
            buttonCvPositionAdd.setOnClickListener {
                onAction.invoke(AddPositionAction.AddAction)
            }
        }
        addPositionAdapter.data = positionList
        with(viewGroup.recyclerCvPosition) {
            applyDivider(viewGroup.context, Rect())
            adapter = addPositionAdapter
        }
        displayPositionAdapter.data = addPositionAdapter.data
        with(mirroringRecycler) {
            applyDivider(mirroringRecycler.context, Rect())
            adapter = displayPositionAdapter
        }
        val swipePositionHandler = SwipePositionHandler(addPositionAdapter, onSwipeBackground, binIcon)
        val itemTouchHelper = ItemTouchHelper(swipePositionHandler)
        itemTouchHelper.attachToRecyclerView(viewGroup.recyclerCvPosition)
    }

    fun attachPresenter(presenter: CvPresenter) {
        addPositionAdapter.presenter = presenter
        displayPositionAdapter.presenter = presenter
    }

    fun show() {
        viewGroup.viewAddPositionsDim.setVisible(true)
        changeBottomSheetState(BottomSheetBehavior.STATE_EXPANDED)
    }

    fun hide() {
        viewGroup.viewAddPositionsDim.setVisible(false)
        changeBottomSheetState(BottomSheetBehavior.STATE_HIDDEN)
    }

    fun updateActivePosition(position: Int) {
        addPositionAdapter.onPositionSelected(position)
    }

    private fun changeBottomSheetState(state: Int) {
        addPositionBottomSheetBehavior.state = state
    }

    fun updateFrontPositionList() {
        displayPositionAdapter.synchronizeStateWithAddPositionAdapter()
    }

    fun addEmptyPositionAtTheEnd() {
        addPositionAdapter.addEmptyPositionAtTheEnd()
    }

    fun onPositionSelected(position: Int) {
        addPositionAdapter.onPositionSelected(position)
    }

    fun onPositionSwiped(newActivePosition: Int, removedPosition: Int) {
        addPositionAdapter.synchronizeState(newActivePosition, removedPosition)
    }

    fun enableAddPosition() {
        viewGroup.buttonCvPositionAdd.enable()
    }

    fun disableAddPosition() {
        viewGroup.buttonCvPositionAdd.disable()
    }

    fun enableSave() {
        viewGroup.buttonCvPositionsSave.enable()
    }

    fun disableSave() {
        viewGroup.buttonCvPositionsSave.disable()
    }

    fun clearPositionList() {
        addPositionAdapter.clearPositionList()
    }

    fun setPositionTitleClearButtonVisibility(decision: Boolean) {
        addPositionAdapter.setPositionTitleClearButtonVisibility(decision)
    }

    fun setPositionDescriptionClearButtonVisibility(decision: Boolean) {
        addPositionAdapter.setPositionDescriptionClearButtonVisibility(decision)
    }

    fun clearPositionTitle() {
        addPositionAdapter.clearPositionTitle()
    }

    fun clearPositionDescription() {
        addPositionAdapter.clearPositionDescription()
    }

    companion object {
        private const val BIN_ICON_START_OFFSET_WIDTH_MULTIPLIER = 1.5
    }
}