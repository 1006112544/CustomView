package com.daobao.asus.customview.PassWordEditText;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
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
                        mPassWordEditText.addPassWord(1);
                        break;
                    case TWO:
                        mPassWordEditText.addPassWord(2);
                        break;
                    case THREE:
                        mPassWordEditText.addPassWord(3);
                        break;
                    case FOUR:
                        mPassWordEditText.addPassWord(4);
                        break;
                    case FIVE:
                        mPassWordEditText.addPassWord(5);
                        break;
                    case SIX:
                        mPassWordEditText.addPassWord(6);
                        break;
                    case SEVEN:
                        mPassWordEditText.addPassWord(7);
                        break;
                    case EIGHT:
                        mPassWordEditText.addPassWord(8);
                        break;
                    case NIGHT:
                        mPassWordEditText.addPassWord(9);
                        break;
                    case FINISH:
                        if(mPassWordEditText.isInputComplet()){
                            Toast.makeText(PassWordEditTextActivity.this
                                    ,mPassWordEditText.getPassWord(),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(PassWordEditTextActivity.this
                                    ,"请输入完整密码",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case ZERO:
                        mPassWordEditText.addPassWord(0);
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
