package com.daobao.asus.customview.DropDownAnimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.daobao.asus.customview.R;

/**
 * Created by db on 2018/8/26.
 */
public class DropDownAnimationActivity extends AppCompatActivity{
    private LinearLayout mHiddenLayout;
    private float mDensity;
    private int mHiddenViewMeasureHeight;
    private boolean isInitComplet = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop_down_animation);
        initView();
        //获取像素密度
        mDensity = getResources().getDisplayMetrics().density;
        //方法一固定高 在OnCreate中无法通过view,getHeight获取高度 采用这种办法则被隐藏布局的高必须设置为固定dp
        //mHiddenViewMeasureHeight = (int) (mDensity*40+0.5);//40dp
        //isInitComplet = true;
        //方法二 异步获取宽高
        mHiddenLayout.setVisibility(View.VISIBLE);
        mHiddenLayout.post(new Runnable() {
            @Override
            public void run() {
                mHiddenLayout.setVisibility(View.GONE);
                mHiddenViewMeasureHeight = mHiddenLayout.getHeight();
                isInitComplet = true;
            }
        });
    }

    private void initView() {
        mHiddenLayout = findViewById(R.id.hidden_layout);
    }

    public void doClick(View view){
        if(isInitComplet){
            if(mHiddenLayout.getVisibility() == View.GONE){
                //打开动画
                animateOpen(mHiddenLayout);
            }else {
                //关闭动画
                animateClose(mHiddenLayout);
            }
        }
    }

    private void animateOpen(final View view){
        view.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(view,0,mHiddenViewMeasureHeight);
        animator.start();
    }

    private void animateClose(final View view){
        ValueAnimator animator = createDropAnimator(view,view.getHeight(),0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View view,int start,int end){
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(start,end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return valueAnimator;
    }

}
