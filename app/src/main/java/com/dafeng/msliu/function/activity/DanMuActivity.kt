package com.dafeng.msliu.function.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dafeng.msliu.R
import com.dafeng.msliu.function.adapter.DanMuItemAdapter
import com.dafeng.msliu.function.adapter.Viewpage2Adapter
import kotlinx.android.synthetic.main.danmu_layout.*
import java.util.ArrayList

/**
 * @author   fengda
 * @time     2020/8/25 16:16
 * @desc     TODO
 * @updateAuthor  Author
 * @updateDate    Date
 */
public class DanMuActivity : AppCompatActivity() {

    private val mTitles1 = arrayOf(
        "后端", "设计", "工具资源", "热门", "iOS", "Android"
        , "前端"
    )
    var toMutableList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.danmu_layout)
        toMutableList = mTitles1.toMutableList()
        initView()


    }

    private fun initView() {
        var duadapter = DanMuItemAdapter()
        duadapter.setData(toMutableList)
        recycleView.run {
            layoutManager =
                LinearLayoutManager(this@DanMuActivity, LinearLayoutManager.VERTICAL, true)
            adapter = duadapter
        }

        var num=0
        button.setOnClickListener {
            toMutableList.add(0,"你好呀"+num)
            num++
            duadapter.notifyItemInserted(0)
            recycleView.scrollToPosition(0)
        }

    }

}