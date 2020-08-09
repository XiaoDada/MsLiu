package com.dafeng.msliu.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.blankj.utilcode.util.ConvertUtils
import com.dafeng.msliu.R
import com.dafeng.msliu.Utils.Utils

/**
 *
 * 多指触摸实例
 */

class MultiTouchView1 : View {

    companion object {
        @JvmStatic
        val IMAGE_WIDTH = ConvertUtils.dp2px(200f)
    }

    private lateinit var mBitMap: Bitmap
    private lateinit var mPaint: Paint
    private var offsetX: Float = 0f
    private var offsetY: Float = 0f
    private var downX: Float = 0f
    private var downY: Float = 0f
    private var OriginalOffsetX: Float = 0f
    private var OriginalOffsetY: Float = 0f
    private var trackingPointerId: Int = 0


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context);
    }

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private fun init(context: Context) {
        mBitMap = Utils.drawableToBitmap(
            resources.getDrawable(R.drawable.huoying),
            IMAGE_WIDTH,
            IMAGE_WIDTH
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                trackingPointerId = event.getPointerId(0)
                downX = event.x
                downY = event.y
                OriginalOffsetX = offsetX//记录最后的偏移量
                OriginalOffsetY = offsetY
            }
            MotionEvent.ACTION_MOVE -> {
                var index = event.findPointerIndex(trackingPointerId)
                offsetX = event.getX(index) - downX + OriginalOffsetX
                offsetY = event.getY(index) - downY + OriginalOffsetY
                invalidate()
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                var actionIndex = event.actionIndex
                trackingPointerId = event.findPointerIndex(actionIndex)
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)
                OriginalOffsetX = offsetX//记录最后的偏移量
                OriginalOffsetY = offsetY
            }
            MotionEvent.ACTION_POINTER_UP -> {//抬起的时候要把当前的id给重置
                var upIndex = event.actionIndex

                var id = event.getPointerId(upIndex)
                if (id == trackingPointerId) {
                    var newIndex=0
                    if(upIndex==event.pointerCount-1){
                        newIndex=event.pointerCount-2
                    }else{
                        newIndex=event.pointerCount-1
                    }
                    trackingPointerId = event.findPointerIndex(newIndex)
                    downX = event.getX(newIndex)
                    downY = event.getY(newIndex)
                    OriginalOffsetX = offsetX//记录最后的偏移量
                    OriginalOffsetY = offsetY
                }


            }

        }

        return true
    }


    override fun onDraw(canvas: Canvas) {
        canvas.run { drawBitmap(mBitMap, offsetX, offsetY, mPaint) }
    }
}
