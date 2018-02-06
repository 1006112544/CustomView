package com.daobao.asus.customview.GuassianBlur;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.renderscript.Type;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.daobao.asus.customview.R;

/**
 * Created by db on 2018/2/5.
 */

public class GaussianBlurActivity extends AppCompatActivity{
    private ImageView mImgBg;
    private ImageView mImgView;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaussian_blur_activity);
        mImgBg = findViewById(R.id.img_bg);
        mImgView = findViewById(R.id.imgView);
        //高斯模糊
        Bitmap sampleImg = BitmapFactory.decodeResource(getResources(), R.drawable.img); // 获取原图
        Bitmap gaussianBlurImg = blurBitmap(this,sampleImg, 25f);
        mImgBg.setImageBitmap(gaussianBlurImg);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Bitmap blurBitmap(Context context, Bitmap image, @FloatRange(from = 1, to = 25)
            float blurRadius) {
        // 图片缩放比例 (例如 1/10)
        int scaleRatio = 10;
        // 计算图片缩小后的长宽
        int width = Math.round(image.getWidth() / scaleRatio);
        int height = Math.round(image.getHeight() / scaleRatio);

        // 创建一张缩小后的图片做为渲染的图片
        Bitmap bitmap = Bitmap.createScaledBitmap(image, width, height, false);

        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象，第二个参数Element相当于一种像素处理的算法，高斯模糊的话用这个就好
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        Allocation input = Allocation.createFromBitmap(rs, bitmap);
        // 创建相同类型的Allocation对象用来输出
        Type type = input.getType();
        Allocation output = Allocation.createTyped(rs, type);

        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(blurRadius);
        // 设置blurScript对象的输入内存
        blurScript.setInput(input);
        // 将输出数据保存到输出内存中
        blurScript.forEach(output);
        // 将数据填充到bitmap中
        output.copyTo(bitmap);

        // 销毁它们释放内存
        input.destroy();
        output.destroy();
        blurScript.destroy();
        rs.destroy();
        return bitmap;
    }
}
