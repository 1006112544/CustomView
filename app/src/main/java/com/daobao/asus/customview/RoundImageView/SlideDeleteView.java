package com.daobao.asus.customview.RoundImageView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by db on 2018/10/21.
 */
public class SlideDeleteView extends LinearLayout{

    private VelocityTracker mVelocityTracker;
    private Scroller mScroller = new Scroller(getContext());
    private BtnState mBtnState;
    private int mMaxScrollDistance;//最大滑动距离

    //滑动后开启按钮的状态
    public enum BtnState{
        OPEN,
        WILL_OPEN,
        CLOSE,
        WILL_CLOSE
    }

    public SlideDeleteView(Context context) {
        this(context,null);
    }

    public SlideDeleteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideDeleteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context, new LinearInterpolator());
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                switch (mBtnState){
                    case CLOSE:
                        break;
                    case OPEN:
                        break;
                    case WILL_OPEN:
                        break;
                    case WILL_CLOSE:
                        break;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                break;

        }
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);
        velocityTracker.clear();
        velocityTracker.recycle();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDetachedFromWindow() {
        mVelocityTracker.recycle();
        super.onDetachedFromWindow();
    }
}
