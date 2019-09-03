package com.jmy.refreshrecyclerview

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by jmy on 2019/9/3.
 */
abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {
    private var isSlidingUpward = false
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        isSlidingUpward = dy > 0
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        var manager: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            var lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
            var itemCount = manager.itemCount
            if (lastItemPosition == (itemCount - 1) && isSlidingUpward) {
                onLoadMore()
            }
        }
    }

    abstract fun onLoadMore()
}