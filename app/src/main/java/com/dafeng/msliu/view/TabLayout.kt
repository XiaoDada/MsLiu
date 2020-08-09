package com.dafeng.msliu.view
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import kotlin.math.max

/**
 * <pre>
 *     author : ${da.feng}
 *     e-mail : coderfengda@163.com
 *     time   : 2020/08/09
 *     desc   :
 * </pre>
 */


class TabLayout(context : Context, attrs: AttributeSet) : ViewGroup(context,attrs) {

    ///存放子view的四条边框
    private val childRectList by lazy {
        mutableListOf<Rect>()
    }
    //为了计算出子view的大小
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var usedWidth=0 //当前使用的宽
        var usedHeight=0//当前使用的高
        var lineMaxHeight=0;//当前的行高
        var lineUsedWidth=0;//记录当前行的宽度
        //先获取一下父view 宽的测量模式和大小
       val widthMeasureMode= MeasureSpec.getMode(widthMeasureSpec)
       val widthMeasureSize= MeasureSpec.getSize(widthMeasureSpec)
        for (index in 0 until childCount){
            val child = getChildAt(index);
            //难点：计算出每个子view的widthMeasureSpec,heightMeasureSpec
            //widthUsed =0 因为Android汇报机制不全面，子view被压小后没有通知，我们给width写为0，
            // 代表是任意宽度(从屏幕的最左端开始)，你别压我的大小就行。
            measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,usedHeight)
            if(widthMeasureMode!=MeasureSpec.UNSPECIFIED&&
                lineUsedWidth+child.marginRight+child.marginLeft+child.measuredWidth>widthMeasureSize){
                lineUsedWidth=0
                usedHeight+= lineMaxHeight
                lineMaxHeight=0
                measureChildWithMargins(child,widthMeasureSpec,0,heightMeasureSpec,usedHeight)
            }

            //计算之后存入
            if(index>=childRectList.size){
                childRectList.add(Rect())
            }
            val childBounds = childRectList[index]
            childBounds.set(lineUsedWidth+child.marginLeft,
                        usedHeight+child.marginTop,
                child.measuredWidth+lineUsedWidth+child.marginRight,
                child.measuredHeight+usedHeight+child.marginBottom)
            //计算宽度
            lineUsedWidth += child.measuredWidth+child.marginLeft+child.marginRight
            usedWidth= max(usedWidth,lineUsedWidth)
            //计算高度
            lineMaxHeight= max(lineMaxHeight,child.measuredHeight+child.marginRight+child.marginLeft)
//            usedHeight+=lineMaxHeight

        }

        //汇总一下计算出自身的宽高
        val selfWidth = usedWidth
        val selfHeight = usedHeight + lineMaxHeight
        setMeasuredDimension(selfWidth,selfHeight)
    }

    override fun onLayout(isChange: Boolean,left:Int,  top :Int,  right :Int,  bottom :Int) {
        for (index in 0 until childCount){
            val child = getChildAt(index)
            val rect = childRectList[index]
            child.layout(rect.left,rect.top,rect.right,rect.bottom);
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context,attrs)
    }

}