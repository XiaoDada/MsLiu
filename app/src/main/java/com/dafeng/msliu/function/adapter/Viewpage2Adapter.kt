package com.dafeng.msliu.function.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.ArrayList

/**
 * @author   fengda
 * @time     2020/8/7 13:13
 * @desc     TODO
 * @updateAuthor  Author
 * @updateDate    Date
 */
class Viewpage2Adapter : FragmentStateAdapter {
    private lateinit var mFragments: List<Fragment>

    constructor(
        fragmentActivity: FragmentActivity,
        mFragments: ArrayList<Fragment>
    ) : super(fragmentActivity) {

        this.mFragments = mFragments
    }

    override fun getItemCount(): Int {
        return mFragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragments.get(position)
    }
}