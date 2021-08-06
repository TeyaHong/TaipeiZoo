package com.demo.taipeizoo.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.demo.taipeizoo.R
import com.demo.taipeizoo.data.model.Plant
import com.demo.taipeizoo.utils.toPx

/**
 * PlantRowData
 *
 * Created by TeyaHong on 2021/8/4
 */
class PlantRowData(private val plant: Plant) : RowData {
    inner class AreaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pic: ImageView = itemView.findViewById(R.id.iv_pic)
        val name: TextView = itemView.findViewById(R.id.tv_name)
        val alsoKnown: TextView = itemView.findViewById(R.id.tv_also_known)
    }

    override val layout: Int = R.layout.item_plant

    override fun onCreateViewHolder(view: View): RecyclerView.ViewHolder = AreaViewHolder(view)

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        listener: RowAdapter.OnItemClickListener?
    ) {
        viewHolder as AreaViewHolder
        Glide
            .with(viewHolder.itemView)
            .load(plant.pic)
            .transform(
                CenterCrop(),
                RoundedCorners(4.toPx())
            )
            .into(viewHolder.pic)
        ViewCompat.setTransitionName(viewHolder.pic, plant.nameEn)
        viewHolder.name.text = plant.nameCh
        viewHolder.alsoKnown.text = plant.alsoKnown
        viewHolder.itemView.setOnClickListener {
            // 進植物詳細資料
            listener?.onItemClick(it, plant)
        }
    }
}