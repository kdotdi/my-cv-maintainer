package com.example.kdotdi.app.view.cv

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.kdotdi.presenter.base.PresenterFactory
import com.example.kdotdi.presenter.cv.CvPresenter
import com.example.kdotdi.presenter.cv.CvView
import com.example.kdotdi.app.R
import com.example.kdotdi.domain.entity.CvSummary
import com.example.kdotdi.common.base.BackAware
import com.example.kdotdi.common.base.BasePresenterFragment
import com.example.kdotdi.common.extensions.*
import com.squareup.picasso.Picasso
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.bottom_sheet_add_positions.view.*
import kotlinx.android.synthetic.main.fragment_cv.*
import kotlinx.android.synthetic.main.fragment_cv.view.*
import javax.inject.Inject
import javax.inject.Provider

class CvFragment :
    BasePresenterFragment<CvPresenter, CvView>(),
    BackAware,
    CvView {

    private val addPositionView =
        AddPositionView()

    @Inject
    lateinit var cvPresenterProvider: Provider<CvPresenter>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_cv, container, false)
            .apply {
                groupClickableAddPosition.setOnClickListener {
                    presenter.onAddPositions()
                }

                addPositionView.attach(
                    containerAddPositions,
                    recyclerCvPositionsFront,
                    presenter.positionList,
                    color(R.color.global_red),
                    drawable(R.drawable.ic_delete_sweep)
                ) {
                    when (it) {
                        is AddPositionAction.CancelAction -> presenter.onClearPositionClicked()
                        is AddPositionAction.SaveAction -> presenter.onSavePositions()
                        is AddPositionAction.AddAction -> presenter.onAddPositionClicked()
                    }
                }

                labelCvNext.setOnClickListener {
                    presenter.nextSelected()
                }
            }

    override fun loadPersonImage(uri: String) {
        Picasso.get().load(uri)
            .into(imageCvPicture)
    }

    override fun fillCvSummary(cvSummary: CvSummary) {
        labelCvPersonName.text = cvSummary.name
        labelCvSummary.text = cvSummary.summary
    }

    override fun displayAddPosition() {
        activity?.window?.statusBarColor = color(R.color.global_dim)
        addPositionView.show()
    }

    override fun displayAddPositionAt(position: Int) {
        addPositionView.updateActivePosition(position)
        displayAddPosition()
    }

    override fun hideAddPosition() {
        activity?.window?.statusBarColor = Color.TRANSPARENT
        addPositionView.hide()
    }

    override fun updateFrontPositionList() {
        addPositionView.updateFrontPositionList()
    }

    override fun showPositionListFrontInsteadOfInitialEmptyEntry() {
        groupAddPosition.setVisible(false)
    }

    override fun showInitialEmptyEntryInsteadOfPositionListFront() {
        groupAddPosition.setVisible(true)
    }

    override fun swapNoPositions() {
        labelCvNext.text = string(R.string.general_next_button)
    }

    override fun restoreNoPositions() {
        labelCvNext.text = string(R.string.cv_next_empty)
    }

    override fun addEmptyPositionAtTheEnd() {
        addPositionView.addEmptyPositionAtTheEnd()
    }

    override fun onPositionSelected(position: Int) {
        addPositionView.onPositionSelected(position)
    }

    override fun onPositionSwiped(newActivePosition: Int, removedPosition: Int) {
        addPositionView.onPositionSwiped(newActivePosition, removedPosition)
    }

    override fun enableAddPosition() {
        addPositionView.enableAddPosition()
    }

    override fun disableAddPosition() {
        addPositionView.disableAddPosition()
    }

    override fun enableSavePosition() {
        addPositionView.enableSave()
    }

    override fun disableSavePosition() {
        addPositionView.disableSave()
    }

    override fun clearPositionList() {
        addPositionView.clearPositionList()
    }

    override fun setPositionTitleClearButtonVisibility(decision: Boolean) {
        addPositionView.setPositionTitleClearButtonVisibility(decision)
    }

    override fun setPositionDescriptionClearButtonVisibility(decision: Boolean) {
        addPositionView.setPositionDescriptionClearButtonVisibility(decision)
    }

    override fun clearPositionTitle() {
        addPositionView.clearPositionTitle()
    }

    override fun clearPositionDescription() {
        addPositionView.clearPositionDescription()
    }

    //region BasePresenterFragment
    override fun presenterClass(): Class<CvPresenter> {
        return CvPresenter::class.java
    }

    override fun prepareFactory(): PresenterFactory =
        object : PresenterFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> createPresenter(presenterClass: Class<T>): T {
                return cvPresenterProvider.get() as T
            }
        }

    override fun injectDependencies() {
        AndroidSupportInjection.inject(this)
    }

    override fun onPresenterPrepared(fromStorage: Boolean) {
        if (fromStorage) {
            return
        }

        addPositionView.attachPresenter(presenter)
    }
    //endregion

    //region BackAware
    override fun onBackPressed() {
        presenter.backSelected()
    }
    //endregion

    //region navigation

    override fun displayPrevious() {
        findNavController().popBackStack()
    }
    //endregion
}