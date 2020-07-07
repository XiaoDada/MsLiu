package com.dafeng.msliu.view

import android.annotation.TargetApi
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView

/**
 * @author   fengda
 * @time     2020/7/7 15:58
 * @desc     解决垂直事件的分发
 * @updateAuthor  Author
 * @updateDate    Date
 */
class ScrollViewVertical : ScrollView {

    constructor(context: Context?) : super(context) {

    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    @TargetApi(21)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    // 滑动距离及坐标
    private var xDistance = 0f // 滑动距离及坐标
    private var yDistance = 0f // 滑动距离及坐标
    private var xLast = 0f // 滑动距离及坐标
    private var yLast = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                xDistance =0f
                yDistance = 0f
                xLast = ev.x
                yLast = ev.y
            }

            MotionEvent.ACTION_MOVE -> {
//                val absX = Math.abs(x - mDownPosX)
//                val absY = Math.abs(y - mDownPosY)
//                if (absX > absY) {
//                    return true
//                }

                val curX = ev.x
                val curY = ev.y

                xDistance += Math.abs(curX - xLast)
                yDistance += Math.abs(curY - yLast)
                xLast = curX
                yLast = curY
                val a: Float = xDistance //a边

                val b: Float = yDistance //b边

                val c = Math.sqrt((a * a + b * b).toDouble()) //c边，斜边

                val Q =
                    Math.asin(b / c) //a、b边的夹角度数(Q / Math.PI * 180这个才是人类常用的角度)

                if (Math.abs(Q / Math.PI * 180) > 45  && 0f != xDistance) {
                    return false
                }

            }

        }
        return super.onInterceptTouchEvent(ev)
    }

//    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
//        val x = ev.x
//        val y = ev.y
//        val action = ev.action
//        when (action) {
//            MotionEvent.ACTION_DOWN -> {
//                xLast = x
//                yLast = y
//            }
//            MotionEvent.ACTION_MOVE -> {
//                val deltaX: Float = Math.abs(x - xLast)
//                val deltaY: Float = Math.abs(y - yLast)
//                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
//                if (deltaX > deltaY) {
//                    return false
//                }
//            }
//        }
//        return super.onInterceptTouchEvent(ev)
//    }

}