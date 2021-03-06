package com.dafeng.msliu.touchtest;

import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dafeng.msliu.R;
import com.dafeng.msliu.touchtest.adapter.BaseViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xujun
 * @explain 这个Activity是用来演示ScrollView里面嵌套ViewPager的外部解决法的
 * @time 2016/10/25 15:43.
 */
public class ThreeActivity extends AppCompatActivity {
    ViewPager mViewPager;
    TextView mTextView;

    private List<Fragment> mFragments;
    private BaseViewPagerAdapter mBaseViewPagerAdapter;

    ScrollView mNoHorizontalScrollView;
    private boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch_three_layout);
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

        mBaseViewPagerAdapter = new BaseViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mBaseViewPagerAdapter);

    }
}
