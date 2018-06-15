package com.renrg.heaven.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.renrg.heaven.R;
import com.renrg.heaven.adapter.WelcomeAdapter;
import com.renrg.heaven.base.BaseActivity;
import com.renrg.heaven.transfer.DepthPageTransformer;

public class WelcomeActivity extends BaseActivity {

    private ViewPager welcomeViewPager;
    private LinearLayout welcomeDots;
    private ImageView mLightDots;
    private Button welcomeStart;

    private int[] images = new int[]{
            R.mipmap.indicator1, R.mipmap.indicator2, R.mipmap.indicator3};
    private int mDistance;

    @Override
    protected void setLayoutView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    protected void initView() {
        welcomeViewPager = findViewById(R.id.welcome_viewpager);
        welcomeDots = findViewById(R.id.welcome_dots);
        mLightDots = findViewById(R.id.iv_light_dots);
        welcomeStart = findViewById(R.id.welcome_start);
        welcomeViewPager.setAdapter(new WelcomeAdapter(this, images));
        welcomeViewPager.setPageTransformer(true, new DepthPageTransformer());
        addGrayDots();
    }

    @Override
    protected void addViewListener() {
        welcomeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            }
        });
        mLightDots.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获得两个圆点之间的距离
                mDistance = welcomeDots.getChildAt(1).getLeft() - welcomeDots.getChildAt(0).getLeft();
                mLightDots.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        welcomeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //页面滚动时小白点移动的距离，并通过setLayoutParams(params)不断更新其位置
                startMoveDot(position, mDistance * (position + positionOffset));
            }

            @Override
            public void onPageSelected(int position) {
                //页面跳转时，设置小圆点的margin
                startMoveDot(position, mDistance * position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void startMoveDot(int position, float leftMargin) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLightDots.getLayoutParams();
        params.leftMargin = (int) leftMargin;
        mLightDots.setLayoutParams(params);
        if (position == 2) {
            welcomeStart.setVisibility(View.VISIBLE);
        } else {
            welcomeStart.setVisibility(View.INVISIBLE);
        }
    }

    private void addGrayDots() {
        for (int i = 0; i < 3; i++) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ImageView grayDot = new ImageView(this);
            grayDot.setImageResource(R.drawable.gray_dot);
            if (i > 0)
                layoutParams.leftMargin = 40;
            welcomeDots.addView(grayDot, layoutParams);
        }
    }
}
