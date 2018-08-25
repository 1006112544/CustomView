package com.daobao.asus.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.daobao.asus.customview.FlowLayout.FlowActivity;
import com.daobao.asus.customview.GalleryView.GalleryActivity;
import com.daobao.asus.customview.GuassianBlur.GaussianBlurActivity;
import com.daobao.asus.customview.LoadingView.mLoadingViewActivity;
import com.daobao.asus.customview.MsgDrafitingView.MsgDrafitingViewActivity;
import com.daobao.asus.customview.PassWordEditText.PassWordEditTextActivity;
import com.daobao.asus.customview.RecyclerView.RecyclerViewActivity;
import com.daobao.asus.customview.RoundImageView.RoundAngleImageActivity;
import com.daobao.asus.customview.StepView.qqStepActivity;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mLinerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinerLayout = findViewById(R.id.mLinerLayout);
    }

    public void doClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_qqStepView:
                startActivity(new Intent(MainActivity.this,qqStepActivity.class));
                break;
            case R.id.bt_mLoadingView:
                startActivity(new Intent(MainActivity.this,mLoadingViewActivity.class));
                break;
            case R.id.startAnim:
                Animation anim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.myanim);
                mLinerLayout.startAnimation(anim);
                break;
            case R.id.qqMsgDrafitingViewBt:
                startActivity(new Intent(MainActivity.this,MsgDrafitingViewActivity.class));
                break;
            case R.id.GaussianBlur:
                startActivity(new Intent(MainActivity.this,GaussianBlurActivity.class));
                break;
            case R.id.RoundAngleImageBt:
                startActivity(new Intent(MainActivity.this,RoundAngleImageActivity.class));
                break;
            case R.id.GalleryBtn:
                startActivity(new Intent(MainActivity.this,GalleryActivity.class));
                break;
            case R.id.MRecyclerView:
                startActivity(new Intent(MainActivity.this,RecyclerViewActivity.class));
                break;
            case R.id.FlowLayout:
                startActivity(new Intent(MainActivity.this,FlowActivity.class));
                break;
            case R.id.PassWordEditText:
                startActivity(new Intent(MainActivity.this, PassWordEditTextActivity.class));
        }
    }

}
