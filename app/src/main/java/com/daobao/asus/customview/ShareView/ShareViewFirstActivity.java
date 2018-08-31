package com.daobao.asus.customview.ShareView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import com.daobao.asus.customview.R;

/**
 * Created by db on 2018/8/31.
 */
public class ShareViewFirstActivity extends AppCompatActivity {
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shareview_first);
        initView();
    }

    private void initView() {
        mImageView = findViewById(R.id.share_view_img);
    }

    public void doClick(View view){
        Intent intent = new Intent(this,ShareViewSecondActivity.class);
        Bundle bundle = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            bundle = ActivityOptions.makeSceneTransitionAnimation(this,mImageView,"share").toBundle();
            startActivity(intent,bundle);
            //如果有多个共享view可以通过Pair.create()来创建多个共享元素
            //startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this,
            // Pair.<View, String>create(mImageView,"share"),
            // Pair.<View, String>create(mImageView,"share")).toBundle());
        }

    }
}
