package com.example.kdotdi.presenter.cv

import com.example.kdotdi.domain.entity.CvSummary
import com.example.kdotdi.presenter.base.BaseView

interface CvView : BaseView {
    fun loadPersonImage(uri: String)
    fun fillCvSummary(cvSummary: CvSummary)
    fun displayAddPosition()
    fun displayAddPositionAt(position: Int)
    fun hideAddPosition()
    fun showPositionListFrontInsteadOfInitialEmptyEntry()
    fun showInitialEmptyEntryInsteadOfPositionListFront()
    fun updateFrontPositionList()

    fun addEmptyPositionAtTheEnd()
    fun onPositionSelected(position: Int)
    fun onPositionSwiped(newActivePosition: Int, removedPosition: Int)
    fun enableAddPosition()
    fun disableAddPosition()
    fun enableSavePosition()
    fun disableSavePosition()
    fun clearPositionList()
    fun setPositionTitleClearButtonVisibility(decision: Boolean)
    fun setPositionDescriptionClearButtonVisibility(decision: Boolean)
    fun clearPositionTitle()
    fun clearPositionDescription()

    fun swapNoPositions()
    fun restoreNoPositions()

    fun displayPrevious()
}