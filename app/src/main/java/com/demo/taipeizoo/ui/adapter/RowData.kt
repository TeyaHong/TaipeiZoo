package com.demo.taipeizoo.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * RowData for RecyclerView
 * 所有類型「列」的通用interface
 *
 * Created by TeyaHong on 2021/8/4
 */
interface RowData {
    val layout: Int
    fun onCreateViewHolder(view: View): RecyclerView.ViewHolder
    fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        listener: RowAdapter.OnItemClickListener?
    )
}