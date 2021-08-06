package com.demo.taipeizoo.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * LoadMoreScrollListener
 *
 * Created by TeyaHong on 2021/8/4
 */
class OnLoadMoreScrollListener(layoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {
    private var visibleThreshold = 5
    private lateinit var mOnLoadMoreListener: OnLoadMoreListener
    private var isLoadMoreAvailable: Boolean = true
    private var isLoading: Boolean = false
    private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private var layoutManager: RecyclerView.LayoutManager = layoutManager

    fun isLoading(): Boolean {
        return isLoading
    }

    fun setLoading() {
        isLoading = false
    }

    fun setLoadMoreAvailable(canLoadMore: Boolean) {
        isLoadMoreAvailable = canLoadMore
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy <= 0) return

        totalItemCount = layoutManager.itemCount

        lastVisibleItem = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (isLoadMoreAvailable && !isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
            isLoading = true
            mOnLoadMoreListener.onLoadMore()
        }
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}