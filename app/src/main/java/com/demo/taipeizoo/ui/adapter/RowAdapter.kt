package com.demo.taipeizoo.ui.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * RowAdapter for RecyclerView
 * RowAdapter 繼承 RecyclerView.Adapter，並且接收一個 List<RowData>
 *
 * Created by TeyaHong on 2021/8/4
 */
open class RowAdapter(private val listener: OnItemClickListener?) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: MutableList<RowData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val rowData = list.find { it.layout == viewType }!!
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)
        return rowData.onCreateViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val rowData = list[position]
        rowData.onBindViewHolder(holder, listener)
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].layout
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * Update list
     */
    fun update(rowDataList: List<RowData>) {
        this.list.apply {
            clear()
            addAll(rowDataList)
            notifyDataSetChanged()
        }
    }

    /**
     * Append items to end
     */
    fun addData(rowData: RowData) {
        this.list.apply {
            add(rowData)
            notifyItemInserted(size - 1)
        }
    }

    /**
     * Append items to end
     */
    fun addData(rowDataList: List<RowData>) {
        this.list.apply {
            addAll(rowDataList)
            notifyItemRangeInserted(size - rowDataList.size, rowDataList.size)
        }
    }

    /**
     * Add loading item
     */
    fun addLoadingView() {
        Handler(Looper.getMainLooper()).post {
            this.list.add(LoadMoreRowData())
            notifyItemInserted(this.list.size - 1)
        }
    }

    /**
     * Remove loading item
     */
    fun removeLoadingView() {
        if (this.list.size != 0) {
            this.list.removeAt(this.list.size - 1)
            notifyItemRemoved(this.list.size)
        }
    }

    /**
     * OnItemClickListener
     *
     * Created by TeyaHong on 2021/8/4
     */
    interface OnItemClickListener {
        fun onItemClick(view: View, t: Any)
    }
}