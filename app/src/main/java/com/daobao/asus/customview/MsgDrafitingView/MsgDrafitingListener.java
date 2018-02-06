package com.daobao.asus.customview.MsgDrafitingView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daobao.asus.customview.R;

/**
 * Created by db on 2018/2/6.
 */

public class MsgDrafitingListener implements View.OnTouchListener {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams params;
    private MsgDrafitingView mMsgDrafitingView;
    private Context context;
    // 爆炸动画
    private FrameLayout mBombFrame;
    private ImageView mBombImage;
    private BubbleDisappearListener mDisappearListener;

    public MsgDrafitingListener(Context context,BubbleDisappearListener disappearListener)
    {
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        mMsgDrafitingView = new MsgDrafitingView(context);
        //背景透明
        params.format = PixelFormat.TRANSPARENT;
        this.context = context;

        mBombFrame = new FrameLayout(context);
        mBombImage = new ImageView(context);
        mBombImage.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mBombFrame.addView(mBombImage);
        this.mDisappearListener = disappearListener;
    }


    @Override
    public boolean onTouch(final View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                //隐藏自己
                view.setVisibility(View.INVISIBLE);
                mWindowManager.addView(mMsgDrafitingView,params);
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                Bitmap bitmap = getBitmapByView(view);
                mMsgDrafitingView.initPoint(location[0] + view.getWidth() / 2,
                        location[1]+view.getHeight()/2 -Utils.getStatusBarHeight(context));
                // 给消息拖拽设置一个Bitmap
                mMsgDrafitingView.setDragBitmap(bitmap);
                //设置OnTouchUpListener
                mMsgDrafitingView.setOnToucnUpListener(new MsgDrafitingView.OnToucnUpListener() {
                    @Override
                    public void restore() {
                        //还原位子
                        // 把消息的View移除
                        mWindowManager.removeView(mMsgDrafitingView);
                        // 把原来的View显示
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void dismiss(PointF pointF) {
                        //爆炸效果
                        // 要去执行爆炸动画 （帧动画）
                        // 原来的View的View肯定要移除
                        mWindowManager.removeView(mMsgDrafitingView);
                        // 要在 mWindowManager 添加一个爆炸动画
                        mWindowManager.addView(mBombFrame,params);
                        mBombImage.setBackgroundResource(R.drawable.anim_bubble_pop);

                        AnimationDrawable drawable = (AnimationDrawable) mBombImage.getBackground();
                        mBombImage.setX(pointF.x-drawable.getIntrinsicWidth()/2);
                        mBombImage.setY(pointF.y-drawable.getIntrinsicHeight()/2);

                        drawable.start();
                        // 等它执行完之后我要移除掉这个 爆炸动画也就是 mBombFrame
                        mBombImage.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mWindowManager.removeView(mBombFrame);
                                // 通知一下外面该消失
                                if(mDisappearListener != null){
                                    mDisappearListener.dismiss(view);
                                }
                            }
                        },getAnimationDrawableTime(drawable));
                    }
                });
                break;
            case MotionEvent.ACTION_MOVE:
                mMsgDrafitingView.updatePoint(motionEvent.getRawX(),
                        motionEvent.getRawY() - Utils.getStatusBarHeight(context));
                break;
            case MotionEvent.ACTION_UP:
                mMsgDrafitingView.OnTouchUp();
                break;
        }
        return true;
    }

    private Bitmap getBitmapByView(View view) {
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }


    public interface BubbleDisappearListener {
        void dismiss(View view);
    }

    private long getAnimationDrawableTime(AnimationDrawable drawable) {
        int numberOfFrames = drawable.getNumberOfFrames();
        long time = 0;
        for (int i=0;i<numberOfFrames;i++){
            time += drawable.getDuration(i);
        }
        return time;
    }
}
