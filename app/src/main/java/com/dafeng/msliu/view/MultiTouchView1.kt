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

class MultiTouchView1 :View{

    companion object{
        @JvmStatic
        val IMAGE_WIDTH= ConvertUtils.dp2px(200f)
    }
    private lateinit var mBitMap: Bitmap
    private lateinit var mPaint: Paint
    private var offsetX:Float=0f
    private var offsetY:Float=0f
    private var downX:Float=0f
    private var downY:Float=0f

    constructor(context:Context,attrs:AttributeSet) : super(context, attrs){
         init(context);
    }
    init {
        mPaint=Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private fun init(context: Context) {
         mBitMap = Utils.drawableToBitmap(resources.getDrawable(R.drawable.huoying), IMAGE_WIDTH, IMAGE_WIDTH)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.actionMasked){
            MotionEvent.ACTION_DOWN->{
                downX=event.x;
                downY=event.y;
            }
            MotionEvent.ACTION_MOVE->{
                offsetX=event.x-downX;
                offsetY=event.y-downY;
                invalidate()
            }

        }

        return true
    }



    override fun onDraw(canvas: Canvas) {
        canvas.run { drawBitmap(mBitMap,offsetX,offsetY,mPaint) }
    }
}
