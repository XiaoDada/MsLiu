package com.dafeng.msliu.view

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.animation.ScaleAnimation
import android.widget.ImageView

/****
 * 这里你要明白几个方法执行的流程： 首先ImageView是继承自View的子类.
 * onLayout方法：是一个回调方法.该方法会在在View中的layout方法中执行，在执行layout方法前面会首先执行setFrame方法.
 * layout方法：
 * setFrame方法：判断我们的View是否发生变化，如果发生变化，那么将最新的l，t，r，b传递给View，然后刷新进行动态更新UI.
 * 并且返回ture.没有变化返回false.
 *
 * invalidate方法：用于刷新当前控件,
 *
 *
 * @author zhangjia
 */
class DragImageView : ImageView {
    private var mActivity: Activity? = null
    private var screen_W = 0
    private var screen_H =0// 可见屏幕的宽高度 = 0
    private var bitmap_W = 0
    private var bitmap_H =0// 当前图片宽高 = 0
    private var MAX_W = 0
    private var MAX_H = 0
    private var MIN_W = 0
    private var MIN_H=0 // 极限值 = 0
    private var current_Top = 0
    private var current_Right = 0
    private var current_Bottom = 0
    private var current_Left=0// 当前图片上下左右坐标 = 0
    private var start_Top = -1
    private var start_Right = -1
    private var start_Bottom = -1
    private var start_Left = -1 // 初始化默认位置.
    private var start_x = 0
    private var start_y = 0
    private var current_x = 0
    private var current_y =0// 触摸位置 = 0
    private var beforeLenght = 0f
    private var afterLenght:Float=0f // 两触点距离 = 0f
    private var scale_temp:Float =0f// 缩放比例 = 0f
    private var mLastX = 0
    private var mLastY = 0

    /**
     * 模式 NONE：无 DRAG：拖拽. ZOOM:缩放
     *
     * @author zhangjia
     */
    private enum class MODE {
        NONE, DRAG, ZOOM
    }

    private var mode = MODE.NONE // 默认模式
    private var isControl_V = false // 垂直监控
    private var isControl_H = false // 水平监控
    private val scaleAnimation // 缩放动画
            : ScaleAnimation? = null
    private var isScaleAnim = false // 缩放动画
    private var myAsyncTask // 异步动画
            : MyAsyncTask? = null

    /**
     * 构造方法
     */
    constructor(context: Context?) : super(context) {}

    fun setmActivity(mActivity: Activity?) {
        this.mActivity = mActivity
    }

    /**
     * 可见屏幕宽度
     */
    fun setScreen_W(screen_W: Int) {
        this.screen_W = screen_W
    }

    /**
     * 可见屏幕高度
     */
    fun setScreen_H(screen_H: Int) {
        this.screen_H = screen_H
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    /***
     * 设置显示图片
     */
    override fun setImageBitmap(bm: Bitmap) {
        super.setImageBitmap(bm)
        /** 获取图片宽高  */
        bitmap_W = bm.width
        bitmap_H = bm.height
        MAX_W = bitmap_W * 3
        MAX_H = bitmap_H * 3
        MIN_W = bitmap_W / 2
        MIN_H = bitmap_H / 2
    }

    override fun onLayout(
        changed: Boolean, left: Int, top: Int, right: Int,
        bottom: Int
    ) {
        super.onLayout(changed, left, top, right, bottom)
        if (start_Top == -1) {
            start_Top = top
            start_Left = left
            start_Bottom = bottom
            start_Right = right
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val x = event.y.toInt()
        val y = event.x.toInt()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> parent.requestDisallowInterceptTouchEvent(true)
            MotionEvent.ACTION_POINTER_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
                onPointerDown(event)
            }
            MotionEvent.ACTION_MOVE -> if (mode != MODE.NONE) {
                parent.requestDisallowInterceptTouchEvent(true)
            } else {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        mLastX = x
        mLastY = y
        return super.dispatchTouchEvent(event)
    }

    /***
     * touch 事件
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        Log.i(TAG, "onTouchEvent:=")
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                Log.i(TAG, "onTouchEvent:=  ACTION_DOWN")
                onTouchDown(event)
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                Log.i(
                    TAG,
                    "onTouchEvent:=  ACTION_POINTER_DOWN"
                )
                onPointerDown(event)
            }
            MotionEvent.ACTION_MOVE -> {
                Log.i(TAG, "onTouchEvent:=  ACTION_MOVE")
                onTouchMove(event)
            }
            MotionEvent.ACTION_UP -> {
                Log.i(TAG, "onTouchEvent:=  ACTION_UP")
                mode = MODE.NONE
            }
            MotionEvent.ACTION_POINTER_UP -> {
                Log.i(TAG, "onTouchEvent:=  ACTION_POINTER_UP")
                mode = MODE.NONE
                /** 执行缩放还原  */
                if (isScaleAnim) {
                    doScaleAnim()
                }
            }
        }
        return true
    }

    /**
     * 按下
     */
    fun onTouchDown(event: MotionEvent) {
        mode = MODE.DRAG
        current_x = event.rawX.toInt()
        current_y = event.rawY.toInt()
        start_x = event.x.toInt()
        start_y = current_y - this.top
    }

    /**
     * 两个手指 只能放大缩小
     */
    fun onPointerDown(event: MotionEvent) {
        if (event.pointerCount == 2) {
            mode = MODE.ZOOM
            beforeLenght = getDistance(event) // 获取两点的距离
        }
    }

    /**
     * 移动的处理
     */
    fun onTouchMove(event: MotionEvent) {
        var left = 0
        var top = 0
        var right = 0
        var bottom = 0
        /** 处理拖动  */
        if (mode == MODE.DRAG) {
            /** 在这里要进行判断处理，防止在drag时候越界  */
            /** 获取相应的l，t,r ,b  */
            left = current_x - start_x
            right = current_x + this.width - start_x
            top = current_y - start_y
            bottom = current_y - start_y + this.height
            /** 水平进行判断  */
            if (isControl_H) {
                if (left >= 0) {
                    left = 0
                    right = this.width
                }
                if (right <= screen_W) {
                    left = screen_W - this.width
                    right = screen_W
                }
            } else {
                left = left
                right = right
            }
            /** 垂直判断  */
            if (isControl_V) {
                if (top >= 0) {
                    top = 0
                    bottom = this.height
                }
                if (bottom <= screen_H) {
                    top = screen_H - this.height
                    bottom = screen_H
                }
            } else {
                top = top
                bottom = bottom
            }
            if (isControl_H || isControl_V) setPosition(left, top, right, bottom)
            current_x = event.rawX.toInt()
            current_y = event.rawY.toInt()
        } else if (mode == MODE.ZOOM) {
            afterLenght = getDistance(event) // 获取两点的距离
            val gapLenght = afterLenght - beforeLenght // 变化的长度
            if (Math.abs(gapLenght) > 5f) {
                scale_temp = afterLenght / beforeLenght // 求的缩放的比例
                setScale(scale_temp)
                beforeLenght = afterLenght
            }
        }
    }

    /**
     * 获取两点的距离
     */
    fun getDistance(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return Math.sqrt(x * x + y * y.toDouble()).toFloat()
    }

    /**
     * 实现处理拖动
     */
    private fun setPosition(left: Int, top: Int, right: Int, bottom: Int) {
        layout(left, top, right, bottom)
    }

    /**
     * 处理缩放
     */
    fun setScale(scale: Float) {
        val disX = (this.width * Math.abs(1 - scale)).toInt() / 4 // 获取缩放水平距离
        val disY = (this.height * Math.abs(1 - scale)).toInt() / 4 // 获取缩放垂直距离

        // 放大
        if (scale > 1 && this.width <= MAX_W) {
            current_Left = this.left - disX
            current_Top = this.top - disY
            current_Right = this.right + disX
            current_Bottom = this.bottom + disY
            setFrame(
                current_Left, current_Top, current_Right,
                current_Bottom
            )
            /***
             * 此时因为考虑到对称，所以只做一遍判断就可以了。
             */
            isControl_V = if (current_Top <= 0 && current_Bottom >= screen_H) {
                //		Log.e("jj", "屏幕高度=" + this.getHeight());
                true // 开启垂直监控
            } else {
                false
            }
            isControl_H = if (current_Left <= 0 && current_Right >= screen_W) {
                true // 开启水平监控
            } else {
                false
            }
        } else if (scale < 1 && this.width >= MIN_W) {
            current_Left = this.left + disX
            current_Top = this.top + disY
            current_Right = this.right - disX
            current_Bottom = this.bottom - disY
            /***
             * 在这里要进行缩放处理
             */
            // 上边越界
            if (isControl_V && current_Top > 0) {
                current_Top = 0
                current_Bottom = this.bottom - 2 * disY
                if (current_Bottom < screen_H) {
                    current_Bottom = screen_H
                    isControl_V = false // 关闭垂直监听
                }
            }
            // 下边越界
            if (isControl_V && current_Bottom < screen_H) {
                current_Bottom = screen_H
                current_Top = this.top + 2 * disY
                if (current_Top > 0) {
                    current_Top = 0
                    isControl_V = false // 关闭垂直监听
                }
            }

            // 左边越界
            if (isControl_H && current_Left >= 0) {
                current_Left = 0
                current_Right = this.right - 2 * disX
                if (current_Right <= screen_W) {
                    current_Right = screen_W
                    isControl_H = false // 关闭
                }
            }
            // 右边越界
            if (isControl_H && current_Right <= screen_W) {
                current_Right = screen_W
                current_Left = this.left + 2 * disX
                if (current_Left >= 0) {
                    current_Left = 0
                    isControl_H = false // 关闭
                }
            }
            if (isControl_H || isControl_V) {
                setFrame(
                    current_Left, current_Top, current_Right,
                    current_Bottom
                )
            } else {
                setFrame(
                    current_Left, current_Top, current_Right,
                    current_Bottom
                )
                isScaleAnim = true // 开启缩放动画
            }
        }
    }

    /***
     * 缩放动画处理
     */
    fun doScaleAnim() {
        myAsyncTask = MyAsyncTask(
            screen_W, this.width,
            this.height
        )
        myAsyncTask!!.setLTRB(
            this.left, this.top, this.right,
            this.bottom
        )
        myAsyncTask!!.execute()
        isScaleAnim = false // 关闭动画
    }
    /***
     * 回缩动画執行
     */
    inner class MyAsyncTask(private val screen_W: Int,
    private var current_Width: Int,
    private val current_Height: Int ): AsyncTask<Void?, Int?, Void?>() {
        private var left = 0
        private var top = 0
        private var right = 0
        private var bottom = 0
        private var scale_WH=0f // 宽高的比例
        private val STEP = 8f // 步伐
        private var step_H: Float=0f
        private var step_V: Float=0f // 水平步伐，垂直步伐
        init {
            scale_WH = current_Height.toFloat() / current_Width
            step_H = STEP
            step_V = scale_WH * STEP
        }

        /**
         * 当前的位置属性
         */
        fun setLTRB(left: Int, top: Int, right: Int, bottom: Int) {
            this.left = left
            this.top = top
            this.right = right
            this.bottom = bottom
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            while (current_Width <= screen_W) {
                left -= step_H.toInt()
                top -= step_V.toInt()
                right += step_H.toInt()
                bottom += step_V.toInt()
                current_Width += 2 * step_H.toInt()
                left = Math.max(left, start_Left)
                top = Math.max(top, start_Top)
                right = Math.min(right, start_Right)
                bottom = Math.min(bottom, start_Bottom)
                Log.e(
                    "jj",
                    "top=$top,bottom=$bottom,left=$left,right=$right"
                )
                publishProgress(*arrayOf(left, top, right, bottom))
                //                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            mActivity!!.runOnUiThread { setFrame(values[0]!!, values[1]!!, values[2]!!, values[3]!!) }

        }
    }

    companion object {
        private const val TAG = "xujun"
    }
}