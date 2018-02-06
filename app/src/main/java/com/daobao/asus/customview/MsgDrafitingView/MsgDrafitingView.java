package com.daobao.asus.customview.MsgDrafitingView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

/**
 * Created by db on 2018/2/5.
 */

public class MsgDrafitingView extends View{

    private PointF mLittleCirclePoint;
    private PointF mBigCirclePoint;
    private Paint mPaint;
    //大圆半径
    private int mBigCircleRadius = 10;
    //小圆半径
    private int mLittleCircleRadiusMax = 10;
    private int mLittleCircleRadiusMin = 2;
    private int mLittleCircleRadius;
    private Bitmap dragBitmap;
    private OnToucnUpListener mOnToucnUpListener;


    public MsgDrafitingView(Context context) {
        this(context,null);
    }

    public MsgDrafitingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MsgDrafitingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mBigCircleRadius = dip2px(mBigCircleRadius);
        mLittleCircleRadiusMax = dip2px(mLittleCircleRadiusMax);
        mLittleCircleRadiusMin = dip2px(mLittleCircleRadiusMin);
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBigCirclePoint == null || mLittleCirclePoint == null) {
            return;
        }
        //画大圆
        canvas.drawCircle(mBigCirclePoint.x, mBigCirclePoint.y, mBigCircleRadius, mPaint);
        //获得贝塞尔路径
        Path bezeierPath = getBezeierPath();
        if (bezeierPath!=null) {
            // 小到一定层度就不见了（不画了）
            canvas.drawCircle(mLittleCirclePoint.x, mLittleCirclePoint.y, mLittleCircleRadius, mPaint);
            // 画贝塞尔曲线
            canvas.drawPath(bezeierPath, mPaint);
        }
        // 画图片
        if (dragBitmap != null) {
            canvas.drawBitmap(dragBitmap, mBigCirclePoint.x - dragBitmap.getWidth() / 2,
                    mBigCirclePoint.y - dragBitmap.getHeight() / 2, null);
        }
    }

    private Path getBezeierPath() {
        double distance = getDistance(mBigCirclePoint,mLittleCirclePoint);

        mLittleCircleRadius = (int) (mLittleCircleRadiusMax - distance / 10);
        if (mLittleCircleRadius < mLittleCircleRadiusMin) {
            // 超过一定距离 贝塞尔和固定圆都不要画了
            return null;
        }

        Path bezeierPath = new Path();

        // 求角 a
        // 求斜率
        float dy = (mBigCirclePoint.y-mLittleCirclePoint.y);
        float dx = (mBigCirclePoint.x-mLittleCirclePoint.x);
        float tanA = dy/dx;
        // 求角a
        double arcTanA = Math.atan(tanA);

        // A
        float Ax = (float) (mLittleCirclePoint.x + mLittleCircleRadius*Math.sin(arcTanA));
        float Ay = (float) (mLittleCirclePoint.y - mLittleCircleRadius*Math.cos(arcTanA));

        // B
        float Bx = (float) (mBigCirclePoint.x + mBigCircleRadius*Math.sin(arcTanA));
        float By = (float) (mBigCirclePoint.y - mBigCircleRadius*Math.cos(arcTanA));

        // C
        float Cx = (float) (mBigCirclePoint.x - mBigCircleRadius*Math.sin(arcTanA));
        float Cy = (float) (mBigCirclePoint.y + mBigCircleRadius*Math.cos(arcTanA));

        // D
        float Dx = (float) (mLittleCirclePoint.x - mLittleCircleRadius*Math.sin(arcTanA));
        float Dy = (float) (mLittleCirclePoint.y + mLittleCircleRadius*Math.cos(arcTanA));



        // 拼装 贝塞尔的曲线路径
        bezeierPath.moveTo(Ax,Ay); // 移动
        // 两个点
        PointF controlPoint = getControlPoint();
        // 画了第一条  第一个点（控制点,两个圆心的中心点），终点
        bezeierPath.quadTo(controlPoint.x,controlPoint.y,Bx,By);

        // 画第二条
        bezeierPath.lineTo(Cx,Cy); // 链接到
        bezeierPath.quadTo(controlPoint.x,controlPoint.y,Dx,Dy);
        bezeierPath.close();

        return bezeierPath;
    }
    /**
     * 获得控制点距离
     */
    public PointF getControlPoint() {
        return new PointF((mLittleCirclePoint.x+mBigCirclePoint.x)/2,(mLittleCirclePoint.y+mBigCirclePoint.y)/2);
    }

    /**
     * 获得两点之间的距离
     */
    private double getDistance(PointF point1, PointF point2) {
        return Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y));
    }

    /**
     * 绑定View
     */
    public static void attach(View view, MsgDrafitingListener.BubbleDisappearListener disappearListener) {
        view.setOnTouchListener(new MsgDrafitingListener(view.getContext(),disappearListener));
    }

    public void initPoint(float x, float y) {
        mBigCirclePoint = new PointF(x,y);
        mLittleCirclePoint = new PointF(x,y);
    }

    public void updatePoint(float x,float y)
    {
        mBigCirclePoint.x = x;
        mBigCirclePoint.y = y;
        invalidate();
    }

    public void setDragBitmap(Bitmap dragBitmap) {
        this.dragBitmap = dragBitmap;
    }

    public void setOnToucnUpListener(OnToucnUpListener listener)
    {
        mOnToucnUpListener = listener;
    }

    public interface OnToucnUpListener {
        // 还原
        void restore();
        // 消失爆炸
        void dismiss(PointF pointF);
    }

    /**
     * 处理手指抬起后的操作
     */
    public void OnTouchUp()
    {
        if (mLittleCircleRadius > mLittleCircleRadiusMin) {
            // 回弹  ValueAnimator 值变化的动画  0 变化到 1
            ValueAnimator animator = ObjectAnimator.ofFloat(1);
            animator.setDuration(250);
            final PointF start = new PointF(mBigCirclePoint.x, mBigCirclePoint.y);
            final PointF end = new PointF(mLittleCirclePoint.x, mLittleCirclePoint.y);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float percent = (float) animation.getAnimatedValue();// 0 - 1
                    PointF pointF = Utils.getPointByPercent(start, end, percent);
                    //更新位子
                    updatePoint(pointF.x, pointF.y);
                }
            });
            // 设置一个差值器 在结束的时候回弹
            animator.setInterpolator(new OvershootInterpolator(3f));
            animator.start();
            // 还要通知 TouchListener
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    if(mOnToucnUpListener != null){
                        mOnToucnUpListener.restore();
                    }
                }
            });
        } else {
            // 爆炸
            if(mOnToucnUpListener != null){
                mOnToucnUpListener.dismiss(mBigCirclePoint);
            }
        }
    }
}
