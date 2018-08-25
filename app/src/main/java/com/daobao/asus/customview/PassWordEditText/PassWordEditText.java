package com.daobao.asus.customview.PassWordEditText;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.daobao.asus.customview.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by db on 2018/8/24.
 */
public class PassWordEditText extends LinearLayout{
    private ImageView PassWordIv1;
    private ImageView PassWordIv2;
    private ImageView PassWordIv3;
    private ImageView PassWordIv4;
    private ImageView PassWordIv5;
    private ImageView PassWordIv6;
    private List<ImageView> PassWordIvList;
    private Integer currentIndex = 0;
    private int padding=10;
    private StringBuilder mPasswordBuilder;

    public PassWordEditText(Context context) {
        this(context,null);
    }

    public PassWordEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PassWordEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPasswordBuilder = new StringBuilder();
        setDividerDrawable(getResources().getDrawable(R.drawable.divider_password_edittext));
        setShowDividers(SHOW_DIVIDER_MIDDLE);
        setBackgroundResource(R.drawable.bg_password_edittext);
        initPassWordImageView();
        addView(PassWordIv1);
        addView(PassWordIv2);
        addView(PassWordIv3);
        addView(PassWordIv4);
        addView(PassWordIv5);
        addView(PassWordIv6);
    }

    private void initPassWordImageView() {
        PassWordIvList = new ArrayList<>();
        PassWordIv1 = new ImageView(getContext());
        PassWordIv2 = new ImageView(getContext());
        PassWordIv3 = new ImageView(getContext());
        PassWordIv4 = new ImageView(getContext());
        PassWordIv5 = new ImageView(getContext());
        PassWordIv6 = new ImageView(getContext());
        LayoutParams layoutParams = new LayoutParams(100,100);
        layoutParams.gravity = Gravity.CENTER;
        setItemPadding(padding);
        PassWordIv1.setLayoutParams(layoutParams);
        PassWordIv2.setLayoutParams(layoutParams);
        PassWordIv3.setLayoutParams(layoutParams);
        PassWordIv4.setLayoutParams(layoutParams);
        PassWordIv5.setLayoutParams(layoutParams);
        PassWordIv6.setLayoutParams(layoutParams);
        PassWordIvList.add(PassWordIv1);
        PassWordIvList.add(PassWordIv2);
        PassWordIvList.add(PassWordIv3);
        PassWordIvList.add(PassWordIv4);
        PassWordIvList.add(PassWordIv5);
        PassWordIvList.add(PassWordIv6);
    }

    public void setItemPadding(int padding){
        PassWordIv1.setPadding(padding,padding,padding,padding);
        PassWordIv2.setPadding(padding,padding,padding,padding);
        PassWordIv3.setPadding(padding,padding,padding,padding);
        PassWordIv4.setPadding(padding,padding,padding,padding);
        PassWordIv5.setPadding(padding,padding,padding,padding);
        PassWordIv6.setPadding(padding,padding,padding,padding);
    }
    /**
     * 輸入一位密碼后顯示
     */
    public void addPassWord(int input){
        if(currentIndex<6){
            mPasswordBuilder.append(input);
            PassWordIvList.get(currentIndex++).setImageResource(R.mipmap.ic_dot);
        }
    }

    /**
     * 刪除一位密碼
     */
    public void deletePassWord(){
        if(currentIndex>0){
            --currentIndex;
            mPasswordBuilder.deleteCharAt(currentIndex);
            PassWordIvList.get(currentIndex).setImageDrawable(null);
        }
    }

    /**
     * 刪除所有密碼
     */
    public void clearPassWord(){
        for(ImageView img:PassWordIvList)
        {
            img.setImageDrawable(null);
        }
        mPasswordBuilder.delete(0,mPasswordBuilder.length());
    }

    /**
     * 是否已经完成输入
     * @return boolean 是否完成
     */
    public boolean isInputComplet(){
        if(currentIndex==6){
            return true;
        }
        return false;
    }

    /**
     * 获取输入密码
     * @return 密码
     */
    public String getPassWord(){
        return mPasswordBuilder.toString();
    }
}
