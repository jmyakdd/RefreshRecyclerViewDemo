package com.jmy.refreshrecyclerview

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var count = 0

    val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg!!.what) {
                0 -> {
                    data.clear()
                    count = 0
                    setData()
                    adapter.notifyDataSetChanged()
                    my_recyclerview.setRefreshComplete()
                    if(data.size==0){
                        my_recyclerview.setNoData(true)
                    }else{
                        my_recyclerview.setNoData(false)
                    }
                }
                1 -> {
                    my_recyclerview.setLoadComplete()
                    setData()
                    adapter.notifyDataSetChanged()
                    if (data.size > 50) {
                        adapter.setLoadState(MyBaseRefreshAdapter.LOADING_END)
                        my_recyclerview.setLoadAllData()
                    } else {
                        adapter.setLoadState(MyBaseRefreshAdapter.LOADING_COMPLETE)
                    }
                    if(data.size==0){
                        my_recyclerview.setNoData(true)
                    }else{
                        my_recyclerview.setNoData(false)
                    }
                }
            }
        }
    }

    lateinit var adapter: StringAdapter
    var data = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setData()
        adapter = StringAdapter(this, data)
        my_recyclerview.setAdapter(adapter)
        my_recyclerview.setLayoutManager(LinearLayoutManager(this))

        if(data.size==0){
            my_recyclerview.setNoData(true)
        }else{
            my_recyclerview.setNoData(false)
        }

        my_recyclerview.setMyRefreshListener(object : MyRefreshRecyclerView.MyRefreshListener {
            override fun onRefresh() {
                Thread(object : Runnable {
                    override fun run() {
                        try {
                            Thread.sleep(2000)
                            handler.sendEmptyMessage(0)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }).start()
            }

            override fun onLoad() {
                adapter.setLoadState(MyBaseRefreshAdapter.LOADING)
                Thread(object : Runnable {
                    override fun run() {
                        try {
                            Thread.sleep(2000)
                            handler.sendEmptyMessage(1)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }).start()
            }
        })
    }

    private fun setData() {
        for (i in 0..29) {
            count++
            data.add("name${count}")
        }
    }
}
