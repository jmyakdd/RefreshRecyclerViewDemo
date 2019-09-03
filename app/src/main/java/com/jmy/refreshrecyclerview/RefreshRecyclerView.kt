package com.jmy.refreshrecyclerview

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Created by jmy on 2019/9/3.
 */
class RefreshRecyclerView : RecyclerView {

    private var listener: OnLoadMoreListener? = null
    var isLoading = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        this.listener = listener
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var isSlidingUpward = false
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                isSlidingUpward = dy > 0
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var manager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (newState == SCROLL_STATE_IDLE) {
                    var lastItemPosition = manager.findLastCompletelyVisibleItemPosition()
                    var itemCount = manager.itemCount
                    if (lastItemPosition == (itemCount - 1) && isSlidingUpward && !isLoading) {
                        isLoading = true
                        listener.onLoadMore()
                    }
                }
            }
        })
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }
}