/**
 * @author anxiaoyu
 * @desc webview
 */
package com.dafeng.msliu.view;


import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewParent;
import android.webkit.WebView;


import java.io.InputStream;

public class OrcWebView extends WebView {
    private OnScrollChangeListener mOnScrollChangeListener;

    public OrcWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // webview的高度
        float webcontent = getContentHeight() * getScale();
        // 当前webview的高度
        float webnow = getHeight() + getScrollY();
        if (Math.abs(webcontent - webnow) < 1) {
            //处于底端
            if (mOnScrollChangeListener != null) {
                mOnScrollChangeListener.onPageEnd(l, t, oldl, oldt);
            }
//            Log.e("fengda", "end");
        } else if (getScrollY() == 0) {
            //处于顶端
            if (mOnScrollChangeListener != null) {
                mOnScrollChangeListener.onPageTop(l, t, oldl, oldt);
            }
//            Log.e("fengda", "star");
        } else {
            if (mOnScrollChangeListener != null) {
                mOnScrollChangeListener.onScrollChanged(l, t, oldl, oldt,getScrollY());
            }
//            Log.e("fengda", "going" + l);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = this.getParent();
        while (parent != null) {
            if (parent instanceof ScrollLayout) {
//                Log.e("fengda", "parent instanceof ScrollLayout");
                ((ScrollLayout) parent).setAssociatedWebView(this);
                break;
            }
            parent = parent.getParent();
        }
    }

    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        this.mOnScrollChangeListener = listener;
    }

//    @Override
//    public void destroy() {
//    }
//    public void destroyView() {
//        super.destroy();
//    }
//
//    @Override
//    public void removeParentView(ViewGroup parent) {
//    }
//
//    @Override
//    public void stopLoading() {
//    }
//    public void stopLoadingView() {
//        super.stopLoading();
//    }
//
//    @Override
//    public void removeAllViews() {
//    }
//    public void removeAllView(){
//        super.removeAllViews();
//    }


    public interface OnScrollChangeListener {

        public void onPageEnd(int l, int t, int oldl, int oldt);

        public void onPageTop(int l, int t, int oldl, int oldt);

        public void onScrollChanged(int l, int t, int oldl, int oldt, int scrollY);

    }
}
