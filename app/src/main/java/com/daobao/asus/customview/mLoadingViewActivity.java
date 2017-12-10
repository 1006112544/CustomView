package com.daobao.asus.customview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by db on 2017/12/10.
 */

public class mLoadingViewActivity extends Activity{
    private mLoadingView mLoadingView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mloadingview_activity);
        mLoadingView = findViewById(R.id.mLoadingView);
    }


    public void doClick(View view) {
        switch (view.getId())
        {
            case R.id.start:
                mLoadingView.start();
                break;
            case R.id.stop:
                mLoadingView.stop();
                break;
        }
    }
}
