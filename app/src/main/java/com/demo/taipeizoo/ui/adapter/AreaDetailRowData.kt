package com.demo.taipeizoo.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demo.taipeizoo.R
import com.demo.taipeizoo.data.model.Area

/**
 * AreaDetailRowData
 *
 * Created by TeyaHong on 2021/8/4
 */
class AreaDetailRowData(private val area: Area) : RowData {
    inner class AreaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pic: ImageView = itemView.findViewById(R.id.iv_pic)
        val info: TextView = itemView.findViewById(R.id.tv_info)
        val memo: TextView = itemView.findViewById(R.id.tv_memo)
        val category: TextView = itemView.findViewById(R.id.tv_category)
        val browser: ImageView = itemView.findViewById(R.id.iv_browser)
    }

    override val layout: Int = R.layout.item_area_detail

    override fun onCreateViewHolder(view: View): RecyclerView.ViewHolder = AreaViewHolder(view)

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        listener: RowAdapter.OnItemClickListener?
    ) {
        viewHolder as AreaViewHolder
        Glide
            .with(viewHolder.itemView)
            .load(area.pic)
            .into(viewHolder.pic)
        viewHolder.info.text = area.info
        viewHolder.memo.text =
            if (area.memo.isEmpty()) viewHolder.itemView.context.getString(R.string.empty_memo) else area.memo
        viewHolder.category.text = area.category
        viewHolder.browser.setOnClickListener {
            listener?.onItemClick(it, area)
        }
    }
}