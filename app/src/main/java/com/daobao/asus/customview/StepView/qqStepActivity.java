package com.daobao.asus.customview.StepView;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.daobao.asus.customview.R;

/**
 * Created by db on 2017/12/5.
 */

public class qqStepActivity extends Activity {
    private qqStepView mQqStepView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qqstepview_activity);
        mQqStepView = findViewById(R.id.mQqStepView);
        mQqStepView.setMaxStep(4000);
        ValueAnimator animator = ValueAnimator.ofInt(0,3000);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int CurrentStep = (int) valueAnimator.getAnimatedValue();
                mQqStepView.setCurrentAngle(CurrentStep);
            }
        });
        animator.start();
    }
}
