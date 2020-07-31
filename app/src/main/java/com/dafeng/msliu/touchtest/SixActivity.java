package com.dafeng.msliu.touchtest;

import android.os.Bundle;

import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dafeng.msliu.R;
import com.dafeng.msliu.touchtest.adapter.BaseFragmentAdapter;
import com.dafeng.msliu.touchtest.adapter.ItemAdapter;
import com.dafeng.msliu.touchtest.adapter.RecyclerUtils;

import java.util.ArrayList;
import java.util.List;

public class SixActivity extends AppCompatActivity {


    ViewPager mViewPager;
    TextView mTextView;
    RecyclerView mRecyclerView;

    private List<Fragment> mFragments;
    private ArrayList<String> mList;
    private ItemAdapter mItemAdapter;

    private BaseFragmentAdapter mBaseFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six);
        initView();
        initListener();
        initData();
    }



    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTextView.setText(String.format("%d/8", position + 1));
            }
        });

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTextView = (TextView) findViewById(R.id.tv_page);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            String s = String.format("水温刚刚合适，服旁名居然有些喜欢上这个男孩了。他还给我在水中沐浴的照片起了个奇怪的名字：“枸杞炖鸡汤。", i);
            mList.add(s);
        }
        mItemAdapter = new ItemAdapter(this, mList);
        RecyclerUtils.init(mRecyclerView);
        mRecyclerView.setAdapter(mItemAdapter);

        mFragments = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ImageFragment imageFragment = ImageFragment.newInstance(R.drawable.huoying);
            mFragments.add(imageFragment);
        }

        mBaseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mBaseFragmentAdapter);

    }
}
