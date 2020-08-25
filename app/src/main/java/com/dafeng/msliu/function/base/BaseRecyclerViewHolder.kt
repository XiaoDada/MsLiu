package com.dafeng.msliu.function.base

import android.graphics.Bitmap
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author fengda
 * @time 2018/8/29 20:38
 * @desc TODO
 * @updateAuthor Author
 * @updateDate Date
 */
class BaseRecyclerViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
    private val viewArray: SparseArray<View?>//缓存view

    init {
        viewArray = SparseArray()
    }
    /**
     * 根据传入的view id找view
     *
     * @param viewId
     * @param <T>
     * @return
    </T> */
    fun <T : View?> getView(viewId: Int): T? {
        var view = viewArray[viewId]
        if (view == null) {
            view = itemView.findViewById(viewId)
            viewArray.put(viewId, view)
        }
        return view as T?
    }

    /**
     * 为TextView设置CharSequence
     *
     * @param viewId
     * @param text
     * @return
     */
    fun setText(viewId: Int, text: CharSequence?): BaseRecyclerViewHolder {
        val view = getView<TextView>(viewId)!!
        view.text = text
        return this
    }

    /**
     * 为TextView设置字体颜色
     *
     * @param viewId
     * @param colorId R.color.xxxx
     * @return
     */
    fun setTextColor(viewId: Int, @ColorRes colorId: Int): BaseRecyclerViewHolder {
        val view = getView<TextView>(viewId)!!
        view.setTextColor(view.context.resources.getColor(colorId))
        return this
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param drawableId
     * @return
     */
    fun setImageResource(viewId: Int, drawableId: Int): BaseRecyclerViewHolder {
        val view = getView<ImageView>(viewId)!!
        view.setImageResource(drawableId)
        return this
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param bm
     * @return
     */
    fun setImageBitmap(viewId: Int, bm: Bitmap?): BaseRecyclerViewHolder {
        val view = getView<ImageView>(viewId)!!
        view.setImageBitmap(bm)
        return this
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param url
     * @return
     */
    fun setImageByUrl(viewId: Int, url: Any?): BaseRecyclerViewHolder {
        val imageView = getView<ImageView>(viewId)!!
        //        GlideHelper.loadImg(imageView.getContext(), url, imageView, R.drawable.img_nodata_xxxh);
        return this
    }

    /**
     * 为ImageView设置图片
     *
     * @param viewId
     * @param url
     * @return
     */
    fun setImageByUrl(viewId: Int, url: Any?, errImg: Int): BaseRecyclerViewHolder {
        val imageView = getView<ImageView>(viewId)!!
        //        GlideHelper.loadImg(imageView.getContext(), url, imageView, errImg);
        return this
    }

    /**
     * 设置View的显示隐藏
     *
     * @param viewId
     * @param isVisible
     * @return
     */
    fun setVisible(viewId: Int, isVisible: Boolean): BaseRecyclerViewHolder {
        val view = getView<View>(viewId)
        if (view != null) {
            view.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
        return this
    }

    /**
     * 设置View的显示占位
     *
     * @param viewId
     * @param isVisible
     * @return
     */
    fun setInVisible(viewId: Int, isVisible: Boolean): BaseRecyclerViewHolder {
        val view = getView<View>(viewId)
        if (view != null) {
            view.visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        }
        return this
    }

    /**
     * 设置字体的颜色
     *
     * @param viewId
     * @param
     * @return
     */
    fun setColor(viewId: Int, color: Int): BaseRecyclerViewHolder {
        val view = getView<TextView>(viewId)!!
        view.setTextColor(view.context.resources.getColor(color))
        return this
    }

    /**
     * 设置View的监听事件
     *
     * @param viewId
     * @param listener
     * @return
     */
    fun setOnClickListener(
        viewId: Int,
        listener: View.OnClickListener?
    ): BaseRecyclerViewHolder {
        val view = getView<View>(viewId)
        view?.setOnClickListener(listener)
        return this
    } //    /**

    //     * 给item布局设置 margin  父布局固定为 RecyclerView
    //     *
    //     * @param context
    //     * @param viewId
    //     * @param leftdp
    //     * @param topdp
    //     * @param rightdp
    //     * @param bottomdp
    //     */
    //    public void setMargins(Context context, int viewId, float leftdp, float topdp, float rightdp, float bottomdp) {
    //        ConstraintLayout view = getView(viewId);
    //        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
    //        params.setMargins(ConvertUtils.dp2px(leftdp), ConvertUtils.dp2px(topdp), ConvertUtils.dp2px(rightdp), ConvertUtils.dp2px(bottomdp));
    //        view.setLayoutParams(params);
    //    }

}