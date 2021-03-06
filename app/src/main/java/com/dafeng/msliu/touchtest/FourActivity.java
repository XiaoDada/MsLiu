package com.dafeng.msliu.touchtest;

import android.os.Bundle;

import android.widget.ScrollView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dafeng.msliu.R;
import com.dafeng.msliu.touchtest.adapter.BaseFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class FourActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TextView mTextView;

    private List<Fragment> mFragments;

    ScrollView mNoHorizontalScrollView;
    private boolean first = true;
    private BaseFragmentAdapter mBaseFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch_four_layout);
        initView();
        initListener();
        initData();
    }

    private void scroll() {
        mNoHorizontalScrollView.scrollTo(0, 0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && first) {
            first = false;
            scroll();
        }
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
        mNoHorizontalScrollView = (ScrollView) findViewById(R.id.NoHorizontalScrollView);
    }

    private void initData() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            ImageFragment imageFragment = ImageFragment.newInstance(R.drawable.huoying);
            mFragments.add(imageFragment);
        }
        mBaseFragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments);

        mViewPager.setAdapter(mBaseFragmentAdapter);

    }
}
