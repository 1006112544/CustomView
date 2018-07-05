package com.daobao.asus.customview.GalleryView;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.daobao.asus.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by db on 2018/7/5.
 */

public class GalleryActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    //屏幕宽度
    private int mScreenWidth;
    private List<View> viewlist = new ArrayList<>();
    private VpAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        initView();
        init();
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.union_card_viewpager);
    }

    private void init() {
        for (int i = 0; i < 8; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_gallery, null);
            viewlist.add(view);
        }
        //屏幕宽度
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        //设置缓存数量
        mViewPager.setOffscreenPageLimit(5);
//        //设置间距
//        mViewPager.setPageMargin(20);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                (int) (mScreenWidth/(1.1)), (int) (mScreenWidth/1.8));
        params.gravity = Gravity.CENTER_VERTICAL;
        mViewPager.setLayoutParams(params);

        adapter = new VpAdapter();
        mViewPager.setRotation(90);
        mViewPager.setAdapter(adapter);
        mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mViewPager.setCurrentItem(0);
    }

    class VpAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewlist.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewlist.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = viewlist.get(position);
            view.setRotation(-90);
            container.addView(view);
            return viewlist.get(position);
        }
    }

    /**
     * 切换动画
     */
    class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.9f;
        private static final float MIN_ALPHA = 0.5f;

        private float defaultScale = 0.9f;

        @Override
        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();
            if (position < -1) {
                view.setScaleX(defaultScale);
                view.setScaleY(defaultScale);
            } else if (position <= 1) {
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else {
                view.setScaleX(defaultScale);
                view.setScaleY(defaultScale);
            }
        }
    }
}