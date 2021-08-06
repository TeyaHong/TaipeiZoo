package com.demo.taipeizoo.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.demo.taipeizoo.R
import com.demo.taipeizoo.data.model.Area
import com.demo.taipeizoo.utils.toPx

/**
 * AreaRowData
 *
 * Created by TeyaHong on 2021/8/4
 */
class AreaRowData(private val area: Area) : RowData {
    inner class AreaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pic: ImageView = itemView.findViewById(R.id.iv_pic)
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val info: TextView = itemView.findViewById(R.id.tv_info)
        val memo: TextView = itemView.findViewById(R.id.tv_memo)
    }

    override val layout: Int = R.layout.item_areas

    override fun onCreateViewHolder(view: View): RecyclerView.ViewHolder = AreaViewHolder(view)

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        listener: RowAdapter.OnItemClickListener?
    ) {
        viewHolder as AreaViewHolder
        Glide
            .with(viewHolder.itemView)
            .load(area.pic)
            .transform(
                CenterCrop(),
                RoundedCorners(4.toPx())
            )
            .into(viewHolder.pic)
        viewHolder.name.text = area.name
        viewHolder.info.text = area.info
        viewHolder.memo.text =
            if (area.memo.isEmpty()) viewHolder.itemView.context.getString(R.string.empty_memo) else area.memo
        viewHolder.itemView.setOnClickListener {
            // 進館區詳細資料
            listener?.onItemClick(it, area)
        }
    }
}