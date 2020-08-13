package com.example.kdotdi.app.view.cv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kdotdi.app.R
import com.example.kdotdi.domain.entity.CvPosition
import com.example.kdotdi.presenter.cv.DisplayPositionItemPresenter
import kotlinx.android.synthetic.main.item_cv_position_front.view.*

class DisplayPositionAdapter :
    RecyclerView.Adapter<DisplayPositionAdapter.DisplayPositionViewHolder>() {

    lateinit var presenter: DisplayPositionItemPresenter
    lateinit var data: MutableList<CvPosition>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = DisplayPositionViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cv_position_front, parent, false)
    ).apply {
        itemView.setOnClickListener {
            presenter.onPositionFrontSelected(
                layoutPosition
            )
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: DisplayPositionViewHolder, position: Int) =
        holder.bind(data[position])

    fun synchronizeStateWithAddPositionAdapter() = notifyDataSetChanged()

    class DisplayPositionViewHolder(
        private val item: View
    ) : RecyclerView.ViewHolder(item) {
        fun bind(position: CvPosition) = with(item) {
            labelCvPositionFrontPositionTitle.text = position.title
            labelCvPositionFrontPositionDescription.text = position.description
        }
    }
}