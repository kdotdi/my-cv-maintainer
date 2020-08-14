package com.example.kdotdi.presenter.cv

import androidx.annotation.VisibleForTesting
import com.example.kdotdi.data.extensions.empty
import com.example.kdotdi.domain.apiHandling.response.doOnError
import com.example.kdotdi.domain.apiHandling.response.doOnSuccess
import com.example.kdotdi.domain.entity.*
import com.example.kdotdi.domain.usecase.cv.GetCvPositionsUseCase
import com.example.kdotdi.domain.usecase.cv.GetCvSummaryUseCase
import com.example.kdotdi.presenter.common.CommonUseCasePresenter
import com.example.kdotdi.presenter.extensions.launchUseCase
import timber.log.Timber
import javax.inject.Inject

class CvPresenter @Inject constructor(
    private val getCvSummaryUseCase: GetCvSummaryUseCase,
    private val getCvPositionsUseCase: GetCvPositionsUseCase
) : CommonUseCasePresenter<CvView>(),
    AddPositionItemPresenter,
    DisplayPositionItemPresenter {

    val positionList = mutableListOf<CvPosition>()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var currentlyActivePositionIndex = -1

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var nextEnabled = false

    override fun onFirstBind() {
        super.onFirstBind()

        getCvSummary()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun addEmptyRecordToPositionList() {
        positionList.add(
            CvPosition(
                title = String.empty,
                description = String.empty
            )
        )
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun clearPositionList() {
        Timber.i("Clearing position list")
        positionList.clear()
        currentlyActivePositionIndex = 0
        present { clearPositionList() }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun addEmptyPositionAtTheEndOfPositionList() {
        Timber.i("Adding empty position at the end of the position list")
        addEmptyRecordToPositionList()
        makeActivePositionAt(positionList.size - 1)
        present { addEmptyPositionAtTheEnd() }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun removePosition(position: Int) {
        Timber.i("Removing position at $position")
        positionList.removeAt(position)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun makeActivePositionAt(position: Int) {
        Timber.i("Making active position at $position")
        currentlyActivePositionIndex = position
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun positionIsValid(position: CvPosition) =
        position.description.isNotEmpty() && position.title.isNotEmpty()

    //region UI
    fun onAddPositions() {
        Timber.i("Add positions selected")
        if (positionList.isEmpty()) {
            addEmptyPositionAtTheEndOfPositionList()
        }
        present { displayAddPosition() }
    }

    fun nextSelected() {
        if (nextEnabled) {
            Timber.i("Next selected")
        } else {
            Timber.i("No positions selected")
        }
    }

    fun backSelected() {
        Timber.i("Back selected")
        present { displayPrevious() }
    }

    override fun onPositionFrontSelected(position: Int) {
        Timber.i("Position front selected")
        makeActivePositionAt(position)
        present { displayAddPositionAt(position) }
    }

    override fun onPositionSelected(position: Int) {
        Timber.i("Position at $position selected")
        if (positionIsValid(positionList[currentlyActivePositionIndex]).not()) {
            Timber.i("Currently active position is not valid, not switching active position")
            return
        }
        if (position != currentlyActivePositionIndex) {
            makeActivePositionAt(position)
            present { onPositionSelected(position) }
        } else {
            Timber.i("Already active position selected")
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun canDropDownActivePositionWithinBoundariesAfterSwiping(
        swipedPosition: Int,
        lastPositionBeforeSwiping: Int
    ) = currentlyActivePositionIndex > 0 &&
            (currentlyActivePositionIndex > swipedPosition || currentlyActivePositionIndex == lastPositionBeforeSwiping)

    override fun onPositionSwiped(swipedPosition: Int) {
        val lastPositionBeforeSwiping = positionList.size - 1
        Timber.i("Position at $swipedPosition swiped, currently active position at $currentlyActivePositionIndex, last position before swiping at $lastPositionBeforeSwiping")
        removePosition(swipedPosition)
        if (canDropDownActivePositionWithinBoundariesAfterSwiping(
                swipedPosition,
                lastPositionBeforeSwiping
            )
        ) {
            --currentlyActivePositionIndex
            Timber.i("Currently active position changed to $currentlyActivePositionIndex")
        }
        present { onPositionSwiped(currentlyActivePositionIndex, swipedPosition) }
        enableAddPositionBasedOnPositionListLimit()
    }

    fun onAddPositionClicked() {
        Timber.i("Add Another One position selected")
        if (positionIsValid(positionList[currentlyActivePositionIndex])) {
            addEmptyPositionAtTheEndOfPositionList()
            disableAddPositionBasedOnPositionListLimit()
        } else {
            Timber.i("Currently active position is not valid, not adding new position")
        }
    }

    fun onClearPositionClicked() {
        Timber.i("Cancel position selected")
        clearPositionList()
        present {
            updateFrontPositionList()
            hideAddPosition()
            nextEnabled = false
            restoreNoPositions()
            showInitialEmptyEntryInsteadOfPositionListFront()
        }
    }

    fun onSavePositions() {
        Timber.i("Save positions selected")
        present {
            updateFrontPositionList()
            hideAddPosition()
            showPositionListFrontInsteadOfInitialEmptyEntry()
            swapNoPositions()
            nextEnabled = true
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun enableAddPositionBasedOnPositionListLimit() {
        if (positionList.size < MAX_NUMBER_OF_POSITIONS) {
            Timber.i("Enabling add")
            present { enableAddPosition() }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun disableAddPositionBasedOnPositionListLimit() {
        if (positionList.size == MAX_NUMBER_OF_POSITIONS) {
            Timber.i("Disabling add")
            present { disableAddPosition() }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun setSavePositionListStateBasedOnActivePosition(activePosition: Int) {
        if (positionIsValid(positionList[activePosition])) {
            Timber.i("Position valid, enabling save button and maybe add")
            present { enableSavePosition() }
            enableAddPositionBasedOnPositionListLimit()
        } else {
            Timber.i("Position invalid, disabling save button and add")
            present {
                disableSavePosition()
                disableAddPosition()
            }
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun decideOnClearButtonVisibility(relatedFieldText: String, fieldIsFocused: Boolean = true) =
        relatedFieldText.isNotEmpty() && fieldIsFocused

    override fun onPositionTitleEdited(position: Int, positionTitleEditedText: String, positionTitleIsFocused: Boolean) {
        Timber.i("Position title at position $position edited with: $positionTitleEditedText, is focused: $positionTitleIsFocused")
        positionList[position].title = positionTitleEditedText
        setSavePositionListStateBasedOnActivePosition(position)
        present { setPositionTitleClearButtonVisibility(decideOnClearButtonVisibility(positionTitleEditedText, positionTitleIsFocused)) }
    }

    override fun onPositionDescriptionEdited(position: Int, positionDescriptionEditedText: String, positionDescriptionIsFocused: Boolean) {
        Timber.i("Position name at position $position edited with: $positionDescriptionEditedText, is focused: $positionDescriptionIsFocused")
        positionList[position].description = positionDescriptionEditedText
        setSavePositionListStateBasedOnActivePosition(position)
        present { setPositionDescriptionClearButtonVisibility(decideOnClearButtonVisibility(positionDescriptionEditedText, positionDescriptionIsFocused)) }
    }

    override fun onPositionTitleFocusChange(
        positionTitleEditedText: String,
        positionTitleIsFocused: Boolean
    ) {
        if (positionTitleIsFocused) {
            Timber.i("Setting positon title clear button visibility based on position title text")
            present { setPositionTitleClearButtonVisibility(decideOnClearButtonVisibility(positionTitleEditedText)) }
        } else {
            Timber.i("Hiding position title clear button")
            present { setPositionTitleClearButtonVisibility(false) }
        }
    }

    override fun onPositionDescriptionFocusChange(
        positionDescriptionEditedText: String,
        positionDescriptionIsFocused: Boolean
    ) {
        if (positionDescriptionIsFocused) {
            Timber.i("Setting position description clear button visibility based on position description text")
            present { setPositionDescriptionClearButtonVisibility(decideOnClearButtonVisibility(positionDescriptionEditedText)) }
        } else {
            Timber.i("Hiding position description clear button")
            present { setPositionDescriptionClearButtonVisibility(false) }
        }
    }

    override fun onPositionTitleClearButtonSelected() {
        Timber.i("Position title clear button selected")
        present { clearPositionTitle() }
    }

    override fun onPositionDescriptionClearButtonSelected() {
        Timber.i("Position description clear button selected")
        present { clearPositionDescription() }
    }
    //endregion

    //region getCvSummaryUseCase
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getCvSummary() =
        launchUseCase(
            getCvSummaryUseCase
        ) {
            Timber.i("Trying to get cv summary")
            doOnSuccess {
                Timber.i("Cv summary get successful")
                present {
                    loadPersonImage(it.imageUri)
                    fillCvSummary(it)
                    getCvPositions()
                }
            }
            doOnError {
                Timber.i("Failed to get cv summary")
            }
        }
    //endregion

    //region getCvPositionsUseCase
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getCvPositions() =
        launchUseCase(
            getCvPositionsUseCase
        ) {
            Timber.i("Trying to get cv positions")
            doOnSuccess {
                Timber.i("Cv positions get successful")
                present {
                    it.positions?.let {
                        if (it.isNotEmpty()) this@CvPresenter.positionList.addAll(it)
                        onSavePositions()
                    }
                }
            }
            doOnError {
                Timber.i("Failed to get cv summary")
            }
        }
    //endregion

    companion object {
        const val MAX_NUMBER_OF_POSITIONS = 10
    }
}