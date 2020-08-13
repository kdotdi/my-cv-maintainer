package com.example.kdotdi.presenter.cv

interface AddPositionItemPresenter {
    fun onPositionSelected(position: Int)
    fun onPositionTitleEdited(position: Int, positionTitleEditedText: String, positionTitleIsFocused: Boolean)
    fun onPositionDescriptionEdited(position: Int, positionDescriptionEditedText: String, positionDescriptionIsFocused: Boolean)
    fun onPositionTitleFocusChange(positionTitleEditedText: String, positionTitleIsFocused: Boolean)
    fun onPositionDescriptionFocusChange(positionDescriptionEditedText: String, positionDescriptionIsFocused: Boolean)
    fun onPositionTitleClearButtonSelected()
    fun onPositionDescriptionClearButtonSelected()
    fun onPositionSwiped(swipedPosition: Int)
}