package com.daobao.asus.customview.FlowLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by db on 2018/7/12.
 */

public class FlowLayout extends ViewGroup{

    private List<List<View>> mChildViews = new ArrayList<>();

    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mChildViews.clear();
        //测量宽高
        int width = MeasureSpec.getSize(widthMeasureSpec);//获取自己的宽度
        int height = getPaddingTop() + getPaddingBottom();//去除padding
        int lineWidth = getPaddingLeft()+getPaddingRight();//一行的宽度

        ArrayList<View> childViews = new ArrayList<>();
        int maxHeight = 0;
        //测量子view
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++){
            View childView = getChildAt(i);
            //调用测量childview 其会调用子view的OnMeasure方法
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
            //根据子view计算自己的宽高 注意什么时候换行
            if(lineWidth+childView.getMeasuredWidth()>width){
                //换行 累加高度
                height += childView.getMeasuredHeight();
                mChildViews.add(childViews);
                childViews = new ArrayList<>();
                //把换行的放入新的List中
                childViews.add(childView);
                lineWidth = childView.getMeasuredWidth();
            }else {
                lineWidth += childView.getMeasuredWidth();
                childViews.add(childView);
                maxHeight = Math.max(childView.getMeasuredHeight(),maxHeight);
            }
        }
        mChildViews.add(childViews);
        height += maxHeight;
        //指定自己的宽高
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        //循环放入子view
        int left,top = getPaddingTop(),right,bottom;
        int maxTop = 0;
        for(List<View> childViews:mChildViews){
            left = getPaddingLeft();
            for(View childView:childViews){
                right = left + childView.getMeasuredWidth();
                bottom = top + childView.getMeasuredHeight();
                if(childView.getMeasuredHeight()>maxTop){
                    maxTop = childView.getMeasuredHeight();
                }
                //摆放
                childView.layout(left,top,right,bottom);
                left += childView.getMeasuredWidth();
            }
            top += maxTop;
        }
    }
}
