package com.daobao.asus.customview.PassWordEditText;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.daobao.asus.customview.R;

/**
 * Created by db on 2018/8/24.
 */
public class PassWordEditTextActivity extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private PassWordEditRvAdapter mAdapter;
    private PassWordEditText mPassWordEditText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_edtit_text);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.password_recyclerView);
        mPassWordEditText = findViewById(R.id.password_editText);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mAdapter = new PassWordEditRvAdapter(this);
        mAdapter.setOnButtonClickListener(new PassWordEditRvAdapter.OnButtonClickListener() {
            @Override
            public void onButtonClick(PassWordEditRvAdapter.ButtonType type) {
                switch (type){
                    case ONE:
                        mPassWordEditText.addPassWord();
                        break;
                    case TWO:
                        mPassWordEditText.addPassWord();
                        break;
                    case THREE:
                        mPassWordEditText.addPassWord();
                        break;
                    case FOUR:
                        mPassWordEditText.addPassWord();
                        break;
                    case FIVE:
                        mPassWordEditText.addPassWord();
                        break;
                    case SIX:
                        mPassWordEditText.addPassWord();
                        break;
                    case SEVEN:
                        mPassWordEditText.addPassWord();
                        break;
                    case EIGHT:
                        mPassWordEditText.addPassWord();
                        break;
                    case NIGHT:
                        mPassWordEditText.addPassWord();
                        break;
                    case FINISH:
                        break;
                    case ZERO:
                        mPassWordEditText.addPassWord();
                        break;
                    case DELETE:
                        mPassWordEditText.deletePassWord();
                        break;
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        GridDivider divider1 = new GridDivider(this);
        mRecyclerView.addItemDecoration(divider1);
    }
}
