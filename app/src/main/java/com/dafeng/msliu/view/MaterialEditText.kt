package com.dafeng.msliu.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.blankj.utilcode.util.ConvertUtils
import com.dafeng.msliu.Utils.dp
import com.dafeng.msliu.view.textview.TextUtil
import java.util.*

/**
 *
 * 自定义 MaterialEditText
 */
class MaterialEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    companion object {
        private val TEXT_SIZE = 12.dp

        private val TEXT_MARGIN = 12.dp

        private val TEXT_HORIZONTAL_OFFSET = 0.dp//去掉下面的线后开始边距改为0

        private val TEXT_VERTICAL_OFFSET = 20.dp

        private val EXTRA_VERTICAL_OFFSET = 12.dp
        //这个大小是最大状态到最小状态的差值
        private val EXTRA_TEXTSIZE_OFFSET = 4.dp

        private val EXTRA_BOTTOM_PADDING_SIZE = 5.dp

    }

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintLine by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG)
    }
    private var floatingLabelShown = false
    private var isAnimator=false;
    var floatingLabelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "floatingLabelFraction", 0f, 1f)
    }

    init {
        floatingLabelFraction = 0f
        paint.textSize = TEXT_SIZE
        setPadding(
            paddingLeft,
            paddingTop + TEXT_SIZE.toInt() + TEXT_MARGIN.toInt(),
            paddingRight,
            paddingBottom
        )
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (floatingLabelShown && text.isNullOrEmpty()) {
                    floatingLabelShown = false
                    isAnimator=true
                    animator.reverse()
                } else if (!floatingLabelShown && !text.isNullOrEmpty()) {
                    isAnimator=true
                    floatingLabelShown = true
                    animator.start()
                }
            }

        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.alpha = (floatingLabelFraction * 0xff).toInt()
        paint.textSize=TEXT_SIZE+EXTRA_TEXTSIZE_OFFSET*(1 - floatingLabelFraction)
        val currentVertivalValue = TEXT_VERTICAL_OFFSET + EXTRA_VERTICAL_OFFSET * (1 - floatingLabelFraction)
        canvas.drawText(hint.toString(), TEXT_HORIZONTAL_OFFSET, currentVertivalValue, paint)
        paintLine.color=Color.RED
        paintLine.strokeWidth=8f
        val measureText = getPaint().measureText(text.toString())
        canvas.drawLine(0f,(height-1).toFloat(), measureText,(height-1).toFloat(), paintLine);
    }


}