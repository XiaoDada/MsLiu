package com.dafeng.msliu.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.dafeng.msliu.R
import com.dafeng.msliu.Utils.Utils

class MultiTouchView1 :View{
    private lateinit var mBitMap: Bitmap
    private lateinit var mPaint: Paint
    constructor(context:Context,attrs:AttributeSet) : super(context, attrs){
         init(context);
    }
    init {
        mPaint=Paint(Paint.ANTI_ALIAS_FLAG)
    }

    private fun init(context: Context) {

         mBitMap = Utils.drawableToBitmap(resources.getDrawable(R.drawable.huoying), 100, 100)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.run { drawBitmap(mBitMap,0f,0f,mPaint) }
    }
}
