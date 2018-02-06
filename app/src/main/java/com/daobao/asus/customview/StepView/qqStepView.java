package com.daobao.asus.customview.StepView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.daobao.asus.customview.R;

/**
 * Created by db on 2017/12/5.
 */

public class qqStepView extends View{
    private int SurfaceArcColor = Color.RED;
    private int InnerArcColor = Color.BLUE;
    private int ArcWidth = 20;//20px
    private int stepTextColor = Color.BLACK;
    private int stepTextSize = 20;//px
    private int CurrentStep = 0;//当前步数
    private int MaxStep = 100;//最大步数
    int mWidth;//组件宽
    int mHeight;//组件高
    private int DEFAULT_DIAMETER = 300;//px
    private Paint ArcPaint;
    private Paint TextPaint;
    int mDiameter;//组件宽高
    private String TAG = "cc";
    public qqStepView(Context context) {
        this(context,null);
    }

    public qqStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public qqStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.qqStepView);
        SurfaceArcColor = array.getColor(R.styleable.qqStepView_SurfaceArcColor,SurfaceArcColor);
        InnerArcColor = array.getColor(R.styleable.qqStepView_InnerArcColor,InnerArcColor);
        ArcWidth = (int) array.getDimension(R.styleable.qqStepView_ArcWidth,ArcWidth);
        stepTextColor = array.getColor(R.styleable.qqStepView_stepTextColor,stepTextColor);
        stepTextSize = array.getDimensionPixelSize(R.styleable.qqStepView_stepTextSize,SptoPx(stepTextSize));

        ArcPaint = new Paint();
        ArcPaint.setAntiAlias(true);
        ArcPaint.setStrokeWidth(ArcWidth);
        ArcPaint.setStyle(Paint.Style.STROKE);//设置为空心
        ArcPaint.setStrokeCap(Paint.Cap.ROUND);//笔头为圆形

        TextPaint = new Paint();
        TextPaint.setAntiAlias(true);
        TextPaint.setColor(stepTextColor);
        TextPaint.setTextSize(stepTextSize);

        array.recycle();
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
        DrawInnerArc(canvas);
        DrawSurfaceArc(canvas);
        DrawStepText(canvas);
    }

    private void DrawSurfaceArc(Canvas canvas)
    {
        if(CurrentStep<=MaxStep)
        {
            int CurrentAngle = (int) ((CurrentStep*1.0/MaxStep)*270);//当前角度
            ArcPaint.setColor(SurfaceArcColor);
            RectF rectF = new RectF(ArcWidth/2,ArcWidth/2,mDiameter-ArcWidth/2,mDiameter);
            canvas.drawArc(rectF,135,CurrentAngle,false,ArcPaint);
        }
        else
        {
            ArcPaint.setColor(SurfaceArcColor);
            RectF rectF = new RectF(ArcWidth/2,ArcWidth/2,mDiameter-ArcWidth/2,mDiameter);
            canvas.drawArc(rectF,135,270,false,ArcPaint);
        }
    }

    private void DrawInnerArc(Canvas canvas)
    {
        ArcPaint.setColor(InnerArcColor);
        RectF rectF = new RectF(ArcWidth/2,ArcWidth/2,mDiameter-ArcWidth/2,mDiameter);
        canvas.drawArc(rectF,135,270,false,ArcPaint);
    }

    private void DrawStepText(Canvas canvas)
    {
        Paint.FontMetricsInt fontMetrics = TextPaint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        int baseLine = mDiameter/2 + dy+getPaddingTop()/2-getPaddingBottom()/2;
        String mText = CurrentStep+"";
        Rect bounds = new Rect();
        TextPaint.getTextBounds(mText,0,mText.length(),bounds);
        int x = mDiameter/2-bounds.width()/2;
        canvas.drawText(mText,x,baseLine,TextPaint);
    }

    public void setCurrentAngle(int CurrentStep)
    {
        this.CurrentStep = CurrentStep;
        invalidate();//重绘
    }

    public void setMaxStep(int MaxStep)
    {
        this.MaxStep = MaxStep;
    }

    private int SptoPx(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,sp,getResources().getDisplayMetrics());
    }

}
