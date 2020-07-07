package com.dafeng.msliu.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * @ explain:这个 ViewPager是用来解决ScrollView里面嵌套ViewPager的 内部解决法的
 * @ author：xujun on 2016/10/25 16:38
 * @ email：gdutxiaoxu@163.com
 */
class MyViewPager : ViewPager {
    var lastX = -1
    var lastY = -1

    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val x = ev.rawX.toInt()
        val y = ev.rawY.toInt()
        var dealtX = 0
        var dealtY = 0
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                dealtX = 0
                dealtY = 0
                // 保证子View能够接收到Action_move事件
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                dealtX += Math.abs(x - lastX)
                dealtY += Math.abs(y - lastY)
                Log.i(TAG, "dealtX:=$dealtX")
                Log.i(TAG, "dealtY:=$dealtY")
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (dealtX >= dealtY) {
                    parent.requestDisallowInterceptTouchEvent(true)
                } else {
                    parent.requestDisallowInterceptTouchEvent(false)
                }
                lastX = x
                lastY = y
            }
            MotionEvent.ACTION_CANCEL,MotionEvent.ACTION_UP -> {
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    companion object {
        private const val TAG = "xujun"
    }
}