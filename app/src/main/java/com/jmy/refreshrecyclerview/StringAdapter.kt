package com.jmy.refreshrecyclerview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by jmy on 2019/9/2.
 */
class StringAdapter(context: Context, var data: MutableList<String>) : MyBaseRefreshAdapter(context) {
    override fun onCreateHolder(viewGroup: ViewGroup?, itemType: Int): RecyclerView.ViewHolder {
        var v: View = LayoutInflater.from(context).inflate(R.layout.item_string, viewGroup, false)
        return MyViewHolder(v)
    }

    override fun onBindHolder(vh: RecyclerView.ViewHolder?, position: Int) {
        if (vh != null) {
            (vh as MyViewHolder).item.setText(data.get(position))
        }
    }

    override fun getCount(): Int {
        return data.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var item: TextView

        init {
            item = itemView.findViewById(R.id.item)
        }
    }
}