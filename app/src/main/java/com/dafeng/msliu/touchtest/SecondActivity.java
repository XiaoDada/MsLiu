package com.dafeng.msliu.touchtest;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.dafeng.msliu.R;
import com.dafeng.msliu.touchtest.adapter.BaseViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    ViewPager mViewPager;
    TextView mTextView;
    private List<Fragment> mFragments;
    private BaseViewPagerAdapter mBaseViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.touch_second_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTextView = (TextView) findViewById(R.id.tv_page);
        mFragments = new ArrayList<>();

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mTextView.setText(String.format("%d/8", position + 1));
            }
        });

        for (int i = 0; i < 8; i++) {
            ImageFragment imageFragment = ImageFragment.newInstance(R.drawable.huoying);
            mFragments.add(imageFragment);
        }

        mBaseViewPagerAdapter = new BaseViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mBaseViewPagerAdapter);
    }
}
