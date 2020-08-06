package com.dafeng.msliu.Utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.PixelFormat

import android.graphics.drawable.Drawable




object  Utils {

    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    fun drawableToBitmap(drawable: Drawable,width:Int,height:Int): Bitmap {
        if (width==0) {
          var width= drawable.intrinsicWidth
        }
        if (height==0) {
            var height= drawable.intrinsicHeight
        }
        val bitmap = Bitmap.createBitmap(width, height,
                if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap
    }
}