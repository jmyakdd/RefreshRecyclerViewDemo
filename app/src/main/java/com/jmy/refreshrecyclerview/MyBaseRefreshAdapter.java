package com.jmy.refreshrecyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by jmy on 2019/9/3.
 */
public abstract class MyBaseRefreshAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_ITEM = 1;
    public static final int TYPE_FOOTER = 2;

    public static final int LOADING = 0;
    public static final int LOADING_COMPLETE = 1;
    public static final int LOADING_END = 2;

    private int loadState = LOADING_COMPLETE;

    protected Context context;

    public MyBaseRefreshAdapter(Context context) {
        this.context = context;
    }

    public void setLoadState(int loadState) {
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return getCount() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
        if (itemType == TYPE_ITEM) {
            return onCreateHolder(viewGroup, itemType);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_footer, viewGroup, false);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vh, int position) {
        if (vh instanceof FooterViewHolder) {
            FooterViewHolder viewHolder = (FooterViewHolder) vh;
            switch (loadState) {
                case LOADING:
                    viewHolder.progressBar.setVisibility(View.VISIBLE);
                    viewHolder.tv_loading.setVisibility(View.GONE);
                    viewHolder.tv_end.setVisibility(View.GONE);
                    break;
                case LOADING_COMPLETE:
                    viewHolder.progressBar.setVisibility(View.INVISIBLE);
                    viewHolder.tv_loading.setVisibility(View.INVISIBLE);
                    viewHolder.tv_end.setVisibility(View.GONE);
                    break;
                case LOADING_END:
                    viewHolder.progressBar.setVisibility(View.GONE);
                    viewHolder.tv_loading.setVisibility(View.GONE);
                    viewHolder.tv_end.setVisibility(View.VISIBLE);
                    break;
            }
        } else {
            onBindHolder(vh, position);
        }
    }

    abstract RecyclerView.ViewHolder onCreateHolder(ViewGroup viewGroup, int itemType);

    abstract void onBindHolder(RecyclerView.ViewHolder vh, int position);

    abstract int getCount();

    class FooterViewHolder extends RecyclerView.ViewHolder {
        TextView tv_loading, tv_end;
        ProgressBar progressBar;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_loading = itemView.findViewById(R.id.tv_loading);
            tv_end = itemView.findViewById(R.id.tv_end);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
