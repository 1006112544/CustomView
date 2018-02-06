package com.daobao.asus.customview.LoadingView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.daobao.asus.customview.R;

/**
 * Created by db on 2018/1/20.
 */

public class LoadingView extends LinearLayout {
    private ShapeView mShapeView;
    private View mShadowView;
    private int mTranslationYDistance = 0;
    //动画执行时间
    private final long AnimatorDuration = 500;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTranslationYDistance = 118;
        initLayout();
    }

    /**
     * 初始化布局
     */
    private void initLayout() {
        inflate(getContext(), R.layout.loadview_ui,this);
        mShapeView = findViewById(R.id.mLoadingView);
        mShadowView = findViewById(R.id.load_shadow);
        startFallAnimation();
    }

    /**
     * 开始下落动画
     */
    private void startFallAnimation() {
        //下落动画
        ObjectAnimator translationAnimator
                = ObjectAnimator.ofFloat(mShapeView,"translationY",0,dip2px(mTranslationYDistance));
        translationAnimator.setDuration(AnimatorDuration);
        //阴影缩小
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mShadowView,"scaleX",1f,0.3f);
        scaleAnimator.setDuration(AnimatorDuration);
        //组合动画
        AnimatorSet set = new AnimatorSet();
        //设置差值器
        set.setInterpolator(new AccelerateInterpolator());//加速差值器
        set.playTogether(translationAnimator,scaleAnimator);
        set.start();
        //顺序执行
        //set.playSequentially();
        //监听动画执行完毕
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //改变形状
                mShapeView.changeShape();
                //上抛动画
                startUpAnimator();
            }


        });
    }

    /**
     * 开始上抛动画
     */
    private void startUpAnimator() {
        //上抛动画
        ObjectAnimator translationAnimator
                = ObjectAnimator.ofFloat(mShapeView,"translationY",dip2px(mTranslationYDistance),0);
        translationAnimator.setDuration(AnimatorDuration);
        //阴影缩小
        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(mShadowView,"scaleX",0.3f,1f);
        scaleAnimator.setDuration(AnimatorDuration);
        //组合动画
        AnimatorSet set = new AnimatorSet();
        //设置差值器
        set.setInterpolator(new DecelerateInterpolator());//减速差值器
        set.playTogether(translationAnimator,scaleAnimator);
        //监听动画执行完毕
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //下落动画
                startFallAnimation();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                //开始旋转
                startRotationAnimation();
            }
        });
        set.start();
    }

    /**
     * 开始旋转动画
     */
    private void startRotationAnimation() {
        ObjectAnimator RotationAnimator = null;
        switch (mShapeView.getShape())
        {
            case Rectangle:
                RotationAnimator = ObjectAnimator.ofFloat(mShapeView,"rotation",0,180);
                break;
            case Triangle:
                RotationAnimator = ObjectAnimator.ofFloat(mShapeView,"rotation",0,-120);
                break;
            case Circular:
                break;
        }
        if(RotationAnimator!=null)
        {
            RotationAnimator.setDuration(AnimatorDuration);
            RotationAnimator.setInterpolator(new DecelerateInterpolator());//减速差值器
            RotationAnimator.start();
        }
    }

    private int dip2px(int dip) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }
}
