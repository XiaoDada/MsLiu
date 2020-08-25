package com.dafeng.msliu.function.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dafeng.msliu.R;
import com.dafeng.msliu.function.base.BaseRecyclerViewHolder;
import com.dafeng.msliu.touchtest.adapter.BaseRecyclerAdapter;
import com.dafeng.msliu.touchtest.adapter.BaseRecyclerHolder;
import com.dafeng.msliu.view.textview.QuoteTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/25 15:21
 * @ email：gdutxiaoxu@163.com
 */
public class DanMuItemAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    private Context mContext;
    private List<String> mDatas;

    public void setData(List<String> datas) {
        mDatas = datas;
    }

    @NonNull
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new BaseRecyclerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.damu_item_string, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerViewHolder holder, int position) {
        TextView tv = holder.getView(R.id.tv);
        String s = mDatas.get(position);
        tv.setText(s);
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
}
