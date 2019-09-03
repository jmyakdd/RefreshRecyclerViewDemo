package com.jmy.refreshrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by jmy on 2019/9/3.
 */
public class MyRefreshRecyclerView extends RelativeLayout {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout emptyLayout;
    private MyRefreshListener listener;

    private boolean isLoading = false;
    private boolean canLoadMore = true;

    private MyBaseRefreshAdapter adapter;

    /**
     * 设置下拉刷新、上拉加载更多监听
     *
     * @param listener
     */
    public void setMyRefreshListener(MyRefreshListener listener) {
        this.listener = listener;
    }

    public MyRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public MyRefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.my_refresh_recyclerview_layout, this, true);
        recyclerView = view.findViewById(R.id.recyclerview);
        emptyLayout = view.findViewById(R.id.empty_layout);
        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                canLoadMore = true;
                if (listener != null) {
                    listener.onRefresh();
                }
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean isSlidingUpward = false;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
                    int itemCount = manager.getItemCount();
                    if (lastItemPosition == (itemCount - 1) && isSlidingUpward && !isLoading && canLoadMore) {
                        isLoading = true;
                        if (listener != null) {
                            listener.onLoad();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingUpward = dy > 0;
            }
        });

        /*recyclerView.getAdapter().registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if(recyclerView.getAdapter().getItemCount()==1){
                    emptyLayout.setVisibility(VISIBLE);
                    recyclerView.setVisibility(GONE);
                }else{
                    emptyLayout.setVisibility(GONE);
                    recyclerView.setVisibility(VISIBLE);
                }
            }
        });*/
    }

    public void setNoData(boolean b){
        if(b){
            emptyLayout.setVisibility(VISIBLE);
            recyclerView.setVisibility(GONE);
        }else{
            emptyLayout.setVisibility(GONE);
            recyclerView.setVisibility(VISIBLE);
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        recyclerView.addItemDecoration(itemDecoration);
    }

    public void setRefreshComplete() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void setLoadComplete() {
        isLoading = false;
    }

    public void setLoadAllData() {
        this.canLoadMore = false;
    }

    interface MyRefreshListener {
        void onRefresh();

        void onLoad();
    }
}
