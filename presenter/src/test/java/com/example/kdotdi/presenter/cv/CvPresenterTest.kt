package com.example.kdotdi.presenter.cv

import com.example.kdotdi.data.extensions.empty
import com.example.kdotdi.data.util.CoroutineTestRule
import com.example.kdotdi.domain.entity.*
import com.example.kdotdi.domain.usecase.cv.GetCvPositionsUseCase
import com.example.kdotdi.domain.usecase.cv.GetCvSummaryUseCase
import com.example.kdotdi.presenter.cv.CvPresenter.Companion.MAX_NUMBER_OF_POSITIONS
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import timber.log.Timber

@ExperimentalCoroutinesApi
class CvPresenterTest {
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @MockK
    lateinit var getCvSummaryUseCase: GetCvSummaryUseCase

    @MockK
    lateinit var getCvPositionsUseCase: GetCvPositionsUseCase

    @MockK(relaxUnitFun = true)
    lateinit var view: CvView

    lateinit var presenter: CvPresenter

    private val testPosition = CvPosition(
        title = "Title",
        description = "Description"
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        presenter = spyk(
            CvPresenter(
                getCvSummaryUseCase = getCvSummaryUseCase,
                getCvPositionsUseCase = getCvPositionsUseCase
            )
        )
        presenter.view = view

        mockkStatic(Timber::class)

        every { presenter.coroutineContext } returns coroutineTestRule.testDispatcher
    }


    @Test
    fun noPositionSelected() = runBlockingTest {
        presenter.nextEnabled = false

        presenter.nextSelected()

        verify(exactly = 1) {
            Timber.i(any<String>())
        }
    }

    @Test
    fun backSelected() = runBlockingTest {
        presenter.backSelected()

        verify(exactly = 1) {
            Timber.i(any<String>())
            view.displayPrevious()
        }
    }

    @Test
    fun addEmptyPositionAtTheEndOfPositionListForEmptyList() {
        presenter.addEmptyPositionAtTheEndOfPositionList()

        verify(exactly = 1) {
            presenter.addEmptyRecordToPositionList()
            presenter.makeActivePositionAt(0)
            view.addEmptyPositionAtTheEnd()
        }
        verify(exactly = 2) {
            Timber.i(any<String>())
        }
    }

    @Test
    fun onAddPositionsNoPositions() {
        presenter.onAddPositions()

        verify(exactly = 1) {
            presenter.addEmptyRecordToPositionList()
            presenter.makeActivePositionAt(0)
            view.addEmptyPositionAtTheEnd()
            view.displayAddPosition()
        }
        verify(exactly = 3) {
            Timber.i(any<String>())
        }
    }

    @Test
    fun onPositionSwipedWithTwoPositions() {
        presenter.positionList.add(testPosition)
        presenter.positionList.add(testPosition)
        presenter.currentlyActivePositionIndex = 1
        presenter.onPositionSwiped(1)

        verify(exactly = 1) {
            presenter.removePosition(1)
            presenter.canDropDownActivePositionWithinBoundariesAfterSwiping(1, 1)
            presenter.enableAddPositionBasedOnPositionListLimit()
            view.onPositionSwiped(0, 1)
        }
        Assert.assertEquals(0, presenter.currentlyActivePositionIndex)
        verify(exactly = 4) {
            Timber.i(any<String>())
        }
        presenter.positionList.clear()
    }

    @Test
    fun onPositionFrontSelected() {
        presenter.onPositionFrontSelected(0)

        verify(exactly = 1) {
            presenter.makeActivePositionAt(0)
            view.displayAddPositionAt(0)
        }
        verify(exactly = 2) {
            Timber.i(any<String>())
        }
    }

    @Test
    fun onInactiveValidPositionSelected() {
        presenter.positionList.add(testPosition)
        presenter.positionList.add(testPosition)
        presenter.currentlyActivePositionIndex = 1
        presenter.onPositionSelected(0)

        verify(exactly = 1) {
            presenter.positionIsValid(any())
            presenter.makeActivePositionAt(0)
            view.onPositionSelected(0)
        }
        verify(exactly = 2) {
            Timber.i(any<String>())
        }
        presenter.positionList.clear()
    }

    @Test
    fun onActiveValidPositionSelected() {
        presenter.positionList.add(testPosition)
        presenter.positionList.add(testPosition)
        presenter.currentlyActivePositionIndex = 1
        presenter.onPositionSelected(1)

        verify(exactly = 1) {
            presenter.positionIsValid(any())
        }
        verify(exactly = 0) {
            presenter.makeActivePositionAt(any())
        }
        verify(exactly = 2) {
            Timber.i(any<String>())
        }
        presenter.positionList.clear()
    }

    @Test
    fun onInvalidPositionSelected() {
        presenter.positionList.add(testPosition)
        presenter.positionList.add(CvPosition(String.empty, String.empty))
        presenter.currentlyActivePositionIndex = 1
        presenter.onPositionSelected(1)

        verify(exactly = 1) {
            presenter.positionIsValid(any())
        }
        verify(exactly = 0) {
            presenter.makeActivePositionAt(any())
            presenter.makeActivePositionAt(any())
            view.onPositionSelected(any())
        }
        verify(exactly = 2) {
            Timber.i(any<String>())
        }
        presenter.positionList.clear()
    }

    @Test
    fun onAddPositionClickedPositionIsValid() {
        presenter.positionList.add(testPosition)
        presenter.currentlyActivePositionIndex = 0
        presenter.onAddPositionClicked()

        verify(exactly = 1) {
            presenter.positionIsValid(any())
            presenter.addEmptyPositionAtTheEndOfPositionList()
            presenter.disableAddPositionBasedOnPositionListLimit()
        }
        verify(exactly = 3) {
            Timber.i(any<String>())
        }
        presenter.positionList.clear()
    }

    @Test
    fun disableAddPositionBasedOnPositionListLimit() {
        for (i in 1..MAX_NUMBER_OF_POSITIONS) presenter.positionList.add(testPosition)
        presenter.disableAddPositionBasedOnPositionListLimit()

        verify(exactly = 1) {
            view.disableAddPosition()
            Timber.i(any<String>())
        }
    }

    @Test
    fun onClearPositionClickedNoPositionsPresent() {
        presenter.onClearPositionClicked()

        verify(exactly = 1) {
            presenter.clearPositionList()
            view.updateFrontPositionList()
            view.hideAddPosition()
            view.showInitialEmptyEntryInsteadOfPositionListFront()
            view.restoreNoPositions()
        }
        verify(exactly = 2) {
            Timber.i(any<String>())
        }
        Assert.assertEquals(false, presenter.nextEnabled)
    }

    @Test
    fun onClearPositionClickedNextEnabled() {
        presenter.onClearPositionClicked()

        verify(exactly = 1) {
            presenter.clearPositionList()
            view.updateFrontPositionList()
            view.hideAddPosition()
            view.showInitialEmptyEntryInsteadOfPositionListFront()
        }
        verify(exactly = 2) {
            Timber.i(any<String>())
        }
        Assert.assertEquals(false, presenter.nextEnabled)
    }

    @Test
    fun onSavePositionClicked() {
        presenter.onSavePositions()

        verify(exactly = 1) {
            view.updateFrontPositionList()
            view.hideAddPosition()
            view.showPositionListFrontInsteadOfInitialEmptyEntry()
            view.swapNoPositions()
            Timber.i(any<String>())
        }
        Assert.assertEquals(true, presenter.nextEnabled)
    }

    @Test
    fun setSavePositionListStateBasedOnActivePositionValid() {
        presenter.positionList.add(testPosition)
        presenter.setSavePositionListStateBasedOnActivePosition(0)

        verify(exactly = 1) {
            presenter.positionIsValid(any())
            presenter.enableAddPositionBasedOnPositionListLimit()
            view.enableSavePosition()
        }
        verify(exactly = 2) {
            Timber.i(any<String>())
        }
        presenter.positionList.clear()
    }

    @Test
    fun setSavePositionListStateBasedOnActivePositionInvalid() {
        presenter.positionList.add(CvPosition(String.empty, String.empty))
        presenter.setSavePositionListStateBasedOnActivePosition(0)

        verify(exactly = 1) {
            presenter.positionIsValid(any())
            view.disableSavePosition()
            view.disableAddPosition()
            Timber.i(any<String>())
        }
        presenter.positionList.clear()
    }

    @Test
    fun onPositionTitleEditedFocused() {
        val editedText = "Title"
        val focused = true
        presenter.positionList.add(testPosition)
        presenter.onPositionTitleEdited(0, editedText, focused)

        verify(exactly = 1) {
            presenter.setSavePositionListStateBasedOnActivePosition(0)
            presenter.decideOnClearButtonVisibility(editedText, focused)
            view.setPositionTitleClearButtonVisibility(focused)
        }
        verify(exactly = 3) {
            Timber.i(any<String>())
        }

        Assert.assertEquals(editedText, presenter.positionList[0].title)
    }

    @Test
    fun onPositionTitleEditedUnfocused() {
        val editedText = "Title"
        val focused = false
        presenter.positionList.add(testPosition)
        presenter.onPositionTitleEdited(0, editedText, focused)

        verify(exactly = 1) {
            presenter.setSavePositionListStateBasedOnActivePosition(0)
            presenter.decideOnClearButtonVisibility(editedText, focused)
            view.setPositionTitleClearButtonVisibility(focused)
        }
        verify(exactly = 3) {
            Timber.i(any<String>())
        }

        Assert.assertEquals(editedText, presenter.positionList[0].title)
    }

    @Test
    fun onPositionDescriptionEditedFocused() {
        val editedText = "Description"
        val focused = true
        presenter.positionList.add(testPosition)
        presenter.onPositionDescriptionEdited(0, editedText, focused)

        verify(exactly = 1) {
            presenter.setSavePositionListStateBasedOnActivePosition(0)
            presenter.decideOnClearButtonVisibility(editedText, focused)
            view.setPositionDescriptionClearButtonVisibility(focused)
        }
        verify(exactly = 3) {
            Timber.i(any<String>())
        }

        Assert.assertEquals(editedText, presenter.positionList[0].description)
    }

    @Test
    fun onPositionDescriptionUnfocused() {
        val editedText = "Test"
        val focused = false
        presenter.positionList.add(testPosition)
        presenter.onPositionDescriptionEdited(0, editedText, focused)

        verify(exactly = 1) {
            presenter.setSavePositionListStateBasedOnActivePosition(0)
            presenter.decideOnClearButtonVisibility(editedText, focused)
            view.setPositionDescriptionClearButtonVisibility(focused)
        }
        verify(exactly = 3) {
            Timber.i(any<String>())
        }

        Assert.assertEquals(editedText, presenter.positionList[0].description)
    }

    @Test
    fun onPositionTitleFocusChangeFocused() {
        val editedText = "Title"
        val focused = true
        presenter.positionList.add(testPosition)
        presenter.onPositionTitleFocusChange(editedText, focused)

        verify(exactly = 1) {
            view.setPositionTitleClearButtonVisibility(focused)
            Timber.i(any<String>())
        }
    }

    @Test
    fun onPositionTitleFocusChangeUnfocused() {
        val editedText = "Test"
        val focused = false
        presenter.positionList.add(testPosition)
        presenter.onPositionTitleFocusChange(editedText, focused)

        verify(exactly = 1) {
            view.setPositionTitleClearButtonVisibility(focused)
            Timber.i(any<String>())
        }
    }

    @Test
    fun onPositionDescriptionFocusChangeFocused() {
        val editedText = "Test"
        val focused = true
        presenter.positionList.add(testPosition)
        presenter.onPositionDescriptionFocusChange(editedText, focused)

        verify(exactly = 1) {
            view.setPositionDescriptionClearButtonVisibility(focused)
            Timber.i(any<String>())
        }
    }

    @Test
    fun onPositionDescriptionFocusChangeUnfocused() {
        val editedText = "Test"
        val focused = false
        presenter.positionList.add(testPosition)
        presenter.onPositionDescriptionFocusChange(editedText, focused)

        verify(exactly = 1) {
            view.setPositionDescriptionClearButtonVisibility(focused)
            Timber.i(any<String>())
        }
    }

    @Test
    fun onPositionTitleClearButtonSelected() {
        presenter.onPositionTitleClearButtonSelected()

        verify(exactly = 1) {
            view.clearPositionTitle()
            Timber.i(any<String>())
        }
    }

    @Test
    fun onPositionDescriptionClearButtonSelected() {
        presenter.onPositionDescriptionClearButtonSelected()

        verify(exactly = 1) {
            view.clearPositionDescription()
            Timber.i(any<String>())
        }
    }
}