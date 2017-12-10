package com.daobao.asus.customview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doClick(View view) {
        switch (view.getId())
        {
            case R.id.bt_qqStepView:
                startActivity(new Intent(MainActivity.this,qqStepActivity.class));
                break;
            case R.id.bt_mLoadingView:
                startActivity(new Intent(MainActivity.this,mLoadingViewActivity.class));
                break;

        }
    }

}
