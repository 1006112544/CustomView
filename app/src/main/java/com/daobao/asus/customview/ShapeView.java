package com.daobao.asus.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by db on 2017/12/10.
 */

public class ShapeView extends View{
    private Paint mPaint;
    private Path mPath;
    int mWidth;//组件宽
    int mHeight;//组件高
    private int DEFAULT_DIAMETER = 500;//px
    private Shape mShape;
    private int mDiameter;
    private Thread mThread;
    private boolean IsStart = false;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
        }
    };

    public ShapeView(Context context) {
        this(context,null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShape = Shape.Circular;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        switch (MeasureSpec.getMode(widthMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                mWidth = MeasureSpec.getSize(widthMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                mWidth = DEFAULT_DIAMETER;
                break;
            case MeasureSpec.UNSPECIFIED:
                mWidth = DEFAULT_DIAMETER;
                break;
        }
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                mHeight = MeasureSpec.getSize(heightMeasureSpec);
                break;
            case MeasureSpec.AT_MOST:
                mHeight = DEFAULT_DIAMETER;
                break;
            case MeasureSpec.UNSPECIFIED:
                mHeight = DEFAULT_DIAMETER;
                break;
        }
        mDiameter = mWidth < mHeight ? mWidth : mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mShape)
        {
            case Circular:
                mPaint.setColor(Color.parseColor("#FF7F50"));
                canvas.drawCircle(mDiameter/2,mDiameter/2,mDiameter/2,mPaint);
                break;
            case Rectangle:
                mPaint.setColor(Color.parseColor("#8DB6CD"));
                RectF rectF = new RectF(0,0,mDiameter,mDiameter);
                canvas.drawRect(rectF,mPaint);
                break;
            case Triangle:
                mPaint.setColor(Color.GREEN);
                if(mPath == null)
                {
                    mPath = new Path();
                    mPath.moveTo(mDiameter/2,0);
                    mPath.lineTo(0, (float) ((mDiameter/2)*Math.sqrt(3)));
                    mPath.lineTo(mDiameter,(float) ((mDiameter/2)*Math.sqrt(3)));
                    mPath.close();//把路径闭合
                }
                canvas.drawPath(mPath,mPaint);
                break;
        }

    }

    public enum Shape
    {
        Circular,
        Rectangle,//矩形
        Triangle //三角形
    }

    public void changeShape()
    {
        if(mShape.equals(Shape.Circular))
        {
            mShape = Shape.Rectangle;
        }
        else if(mShape.equals(Shape.Rectangle))
        {
            mShape = Shape.Triangle;
        }
        else if(mShape.equals(Shape.Triangle))
        {
            mShape = Shape.Circular;
        }
        invalidate();
    }

    public void start()
    {
        IsStart = true;
        if(mThread == null)
        {
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (IsStart)
                    {
                        try {
                            Thread.sleep(1000);
                            changeShape();
                            handler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(!IsStart)
                    {
                        mShape = Shape.Circular;
                        handler.sendEmptyMessage(1);
                    }
                }
            });
            mThread.start();
        }
        else if(!mThread.isAlive())
        {
            mThread.start();
        }
    }

    public void stop()
    {
        if (IsStart) {
            IsStart = false;
        }
    }

    public Shape getShape()
    {
        return mShape;
    }
}
