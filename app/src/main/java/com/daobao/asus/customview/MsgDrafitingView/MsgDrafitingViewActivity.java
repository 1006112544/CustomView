package com.daobao.asus.customview.MsgDrafitingView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daobao.asus.customview.R;

/**
 * Created by db on 2018/2/5.
 */

public class MsgDrafitingViewActivity extends AppCompatActivity{
    private Button mButton;
    private TextView mText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qq_msg_drafitingview_activity);
        mButton = findViewById(R.id.mBtn);
        mText = findViewById(R.id.mText);
        MsgDrafitingView.attach(mButton, new MsgDrafitingListener.BubbleDisappearListener() {
            @Override
            public void dismiss(View view) {

            }
        });
        MsgDrafitingView.attach(mText, new MsgDrafitingListener.BubbleDisappearListener() {
            @Override
            public void dismiss(View view) {

            }
        });
    }
}
