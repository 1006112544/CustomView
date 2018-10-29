package com.daobao.asus.customview.Animation3D;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import com.daobao.asus.customview.CircleImgView.CircleImgView;
import com.daobao.asus.customview.R;

/**
 * 3D旋转动画Activity
 *
 * Created by db on 2018/10/16.
 */
public class Animation3DActivity extends AppCompatActivity{
    private CircleImgView mCircleIv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate3d_animation);
        mCircleIv = findViewById(R.id.rotate3d_circleIv);
        mCircleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float centerY = mCircleIv.getMeasuredHeight()/2f;
                float centerX = mCircleIv.getMeasuredWidth()/2f;
                //创建动画对象
                Rotate3DAnimation r3a = new Rotate3DAnimation(0, 360,
                        100, centerX, centerY,false);
                r3a.setDuration(5*1000);
                r3a.setFillAfter(true);
                //设置加速插补器
                r3a.setInterpolator(new AccelerateInterpolator());
                mCircleIv.startAnimation(r3a);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
    }
}
