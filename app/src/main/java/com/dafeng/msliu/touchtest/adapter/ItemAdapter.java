package com.dafeng.msliu.touchtest.adapter;

import android.content.Context;
import android.widget.TextView;

import com.dafeng.msliu.R;
import com.dafeng.msliu.view.textview.QuoteTextView;
import com.dafeng.msliu.view.textview.ShowAllTextView;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/10/25 15:21
 * @ email：gdutxiaoxu@163.com
 */
public class ItemAdapter extends BaseRecyclerAdapter<String> {

    public ItemAdapter(Context context, List<String> datas) {
        super(context, R.layout.item_string, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position) {
        QuoteTextView tv = holder.getView(R.id.tv);
        String s = mDatas.get(position);
        if (position == 0) {
            tv.setQuoteTextView("这是一间营业时间从午夜十二点到早上七点的特殊食堂这这是一间营业这这是一间营业时间从午夜十二点到早上七点的特殊食堂这","","","...",0);
        } else if (position == 1) {
            tv.setQuoteTextView("这是一间营业时间从午夜十二点到早上七点的特殊食堂这这是一间营业这这是一间营业时间从午夜十二点到早上七点的特殊食堂这", "<img src='rating_star'/>", "<img src='rating_star_gray'/>", "***", 3);
        } else if (position == 2) {
            tv.setQuoteTextView("这是一间营业时间从午夜十二", "", "<font color='red' size='20'> 创建</font>", "...", 5);
        }  else if (position == 3) {
            tv.setQuoteTextView("这是一间营业时间从午夜十二", "", "", "...等五名学生", 5);
        }  else if (position == 4) {
            tv.setQuoteTextView("这是一间营业时间从午夜十二萨达四大四大法发顺丰发发萨达撒范德萨发萨芬", "", "", "...等五名学生", 5);
        }  else {
            tv.setQuoteTextView("这是一间营业时间从午夜十二点到早上七点的特殊食堂这这是一间营业这这是一间营业时间从午夜十二点到早上七点的特殊食堂这", "", " <img src='ic_director'/>", "...", 2);
        }


    }
}
