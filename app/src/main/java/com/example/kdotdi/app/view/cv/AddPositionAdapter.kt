package com.example.kdotdi.app.view.cv

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kdotdi.app.R
import com.example.kdotdi.domain.entity.CvPosition
import com.example.kdotdi.presenter.cv.AddPositionItemPresenter
import com.example.kdotdi.common.extensions.setVisible
import com.example.kdotdi.common.extensions.showKeyboard
import com.example.kdotdi.common.utils.widget.SimpleTextWatcher
import kotlinx.android.synthetic.main.item_cv_position_active.view.*
import kotlinx.android.synthetic.main.item_cv_position_inactive.view.*

class AddPositionAdapter : RecyclerView.Adapter<AddPositionAdapter.AddPositionViewHolder>() {

    lateinit var presenter: AddPositionItemPresenter
    lateinit var data: MutableList<CvPosition>
    private var activePosition = -1
    lateinit var activeItemView: View

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPositionViewHolder {
        return when (viewType) {
            R.layout.item_cv_position_active -> {
                AddPositionActiveViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_cv_position_active, parent, false)
                ).apply {
                    setUpAddPositionActiveView(this@apply)
                }
            }
            else -> AddPositionInactiveViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_cv_position_inactive, parent, false)
            )
        }.apply {
            itemView.setOnClickListener {
                presenter.onPositionSelected(
                    layoutPosition
                )
            }
        }
    }

    private fun setUpAddPositionActiveView(holder: AddPositionActiveViewHolder) =
        with(holder) {
            itemView.textCvPositionActivePositionTitle.addTextChangedListener(
                object : SimpleTextWatcher() {
                    override fun afterTextChanged(
                        text: Editable?
                    ) {
                        presenter.onPositionTitleEdited(
                            layoutPosition,
                            text.toString(),
                            itemView.textCvPositionActivePositionTitle.isFocused
                        )
                    }
                }
            )
            itemView.textCvPositionActivePositionTitle.setOnFocusChangeListener { _, hasFocus ->
                presenter.onPositionTitleFocusChange(
                    itemView.textCvPositionActivePositionTitle.text.toString(),
                    hasFocus
                )
            }
            itemView.imageCvPositionActivePositionTitleClear.setOnClickListener {
                presenter.onPositionTitleClearButtonSelected()
            }
            itemView.textCvPositionActivePositionDescription.addTextChangedListener(
                object : SimpleTextWatcher() {
                    override fun afterTextChanged(
                        text: Editable?
                    ) {
                        presenter.onPositionDescriptionEdited(
                            layoutPosition,
                            text.toString(),
                            itemView.textCvPositionActivePositionDescription.isFocused
                        )
                    }
                }
            )
            itemView.textCvPositionActivePositionDescription.setOnFocusChangeListener { _, hasFocus ->
                presenter.onPositionDescriptionFocusChange(
                    itemView.textCvPositionActivePositionDescription.text.toString(),
                    hasFocus
                )
            }
            itemView.imageCvPositionActivePositionDescriptionClear.setOnClickListener {
                presenter.onPositionDescriptionClearButtonSelected()
            }
        }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: AddPositionViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemViewType(position: Int) = if (position == activePosition)
        R.layout.item_cv_position_active
    else
        R.layout.item_cv_position_inactive

    fun addEmptyPositionAtTheEnd() {
        activePosition = data.size - 1
        notifyItemInserted(data.size - 1)
    }

    fun onPositionSelected(position: Int) {
        activePosition = position
        notifyItemChanged(position)
    }

    fun onPositionRemove(position: Int) {
        presenter.onPositionSwiped(position)
    }

    fun synchronizeState(newActivePosition: Int, removedPosition: Int) {
        activePosition = newActivePosition
        notifyItemRemoved(removedPosition)
    }

    fun clearPositionList() {
        activePosition = 0
        notifyDataSetChanged()
    }

    fun setPositionTitleClearButtonVisibility(decision: Boolean) {
        activeItemView.imageCvPositionActivePositionTitleClear.setVisible(decision)
    }

    fun setPositionDescriptionClearButtonVisibility(decision: Boolean) {
        activeItemView.imageCvPositionActivePositionDescriptionClear.setVisible(decision)
    }

    fun clearPositionTitle() {
        activeItemView.textCvPositionActivePositionTitle.text.clear()
    }

    fun clearPositionDescription() {
        activeItemView.textCvPositionActivePositionDescription.text.clear()
    }

    abstract class AddPositionViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        abstract fun bind(position: CvPosition)
    }

    inner class AddPositionActiveViewHolder(
        private val item: View
    ) : AddPositionViewHolder(item) {
        override fun bind(position: CvPosition) = with(item) {
            activeItemView = item
            textCvPositionActivePositionTitle.setText(position.title)
            post {
                textCvPositionActivePositionTitle.showKeyboard()
            }
            textCvPositionActivePositionDescription.setText(position.description)
        }
    }

    class AddPositionInactiveViewHolder(
        private val item: View
    ) : AddPositionViewHolder(item) {
        override fun bind(position: CvPosition) = with(item) {
            labelCvPositionPositionTitleInactive.text = position.title
            labelCvPositionPositionDescriptionInactive.text = position.description
        }
    }
}