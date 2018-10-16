package com.daobao.asus.customview.Animation3D;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 3d旋转动画,总体效果为以中心为圆点进行旋转
 *
 * Created by db on 2018/10/16.
 */
public class Rotate3DAnimation extends Animation {
    private float fromDegree; //开始角度
    private float toDegree;   //结束角度
    private Camera mCamera;
    private float mCenterX;   //动画执行中心X
    private float mCenterY;   //动画执行中心Y
    private float mDepthZ;    //Z轴上的深度
    private boolean mReverse; //旋转方式由远及近还是由近及远

    public Rotate3DAnimation(float fromDegree, float toDegree, float depthZ,
                             float mCenterX, float mCenterY,boolean reverse) {
        super();
        this.fromDegree = fromDegree;
        this.toDegree = toDegree;
        this.mDepthZ = depthZ;
        this.mCenterX = mCenterX;
        this.mCenterY = mCenterY;
        this.mReverse = reverse;
    }

    /**
     * 在初始化方法中初始化相机Camera
     */
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        float degrees = fromDegree + (fromDegree - toDegree)*interpolatedTime;
        mCamera.save();
        if(mReverse){
            //由近到远的效果
            mCamera.translate(0, 0, mDepthZ*interpolatedTime);
        }else{
            //由远及近的效果
            mCamera.translate(0, 0, mDepthZ * (1-interpolatedTime));
        }
        Matrix matrix = t.getMatrix();
        mCamera.rotateY(degrees);
        mCamera.getMatrix(matrix);
        mCamera.restore();//恢复保存状态
        //执行前平移 旋转中心为0.0,所以我们需要将图片平移到0.0
        matrix.preTranslate(-mCenterX, -mCenterY);
        //执行后平移 执行完动画后还原到原来的位置
        matrix.postTranslate(mCenterX, mCenterY);
    }
}
