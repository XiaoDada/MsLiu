package com.dafeng.msliu.function.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.ToastUtils
import com.dafeng.msliu.R
import com.dafeng.msliu.function.adapter.Viewpage2Adapter
import com.dafeng.msliu.function.fragment.SimpleCardFragment
import kotlinx.android.synthetic.main.viewpage_paging_layout.*
import java.util.*

/**
 * @author   fengda
 * @time     2020/8/7 11:42
 * @desc     TODO
 * @updateAuthor  Author
 * @updateDate    Date
 */
class ViewpagePagingActivity : AppCompatActivity() {
    private val mTitles = arrayOf(
        "热门", "iOS", "Android"
        , "前端", "后端", "设计", "工具资源"
    )


    private val mTitles1 = arrayOf(
         "后端", "设计", "工具资源","热门", "iOS", "Android"
        , "前端"
    )
    private val mFragments =
        ArrayList<Fragment>()
    private var mAdapter: Viewpage2Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewpage_paging_layout)
        initView()

    }

    fun initView() {
        for (title in mTitles) {
            mFragments.add(SimpleCardFragment.getInstance(title))
        }
        mAdapter = Viewpage2Adapter(this, mFragments)
        view_page.adapter = mAdapter

        view_page.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
               var stringBuffer= StringBuffer()
                tv_index.text=stringBuffer.append(position+1).append("/").append(mFragments.size)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                if (mFragments.size - 1 == position && positionOffsetPixels == 0) {
                    ToastUtils.showShort("滑动到最后一页了")
                    mFragments.clear()
                    for (title in mTitles1) {
                        mFragments.add(SimpleCardFragment.getInstance(title))
                    }
                    mAdapter!!.notifyDataSetChanged()
                }

            }

        })


    }

}