package com.dafeng.msliu.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @ explain:
 * @ author：xujun on 2016/10/26 11:10
 * @ email：gdutxiaoxu@163.com
 */
class ChildViewPager : ViewPager {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val curPosition: Int
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> parent.requestDisallowInterceptTouchEvent(true)
            MotionEvent.ACTION_MOVE -> {
                curPosition = this.currentItem
                val count = this.adapter!!.count
                Log.i(TAG, "curPosition:=$curPosition")
                //                全部由孩子拦截触摸事件
                /*                getParent().requestDisallowInterceptTouchEvent(true);*/
                // 当当前页面在最后一页和第0页的时候，由父亲拦截触摸事件
                if (curPosition == count - 1 || curPosition == 0) {
                    parent.requestDisallowInterceptTouchEvent(false)
                } else { //其他情况，由孩子拦截触摸事件
                    parent.requestDisallowInterceptTouchEvent(true)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    companion object {
        private const val TAG = "xujun"
    }
}