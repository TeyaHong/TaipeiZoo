package com.demo.taipeizoo.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.demo.taipeizoo.R

/**
 * PlantSectionRowData
 *
 * Created by TeyaHong on 2021/8/4
 */
class PlantSectionRowData : RowData {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override val layout: Int = R.layout.item_section_title

    override fun onCreateViewHolder(view: View): RecyclerView.ViewHolder = ViewHolder(view)

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        listener: RowAdapter.OnItemClickListener?
    ) {
    }
}