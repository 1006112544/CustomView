package com.daobao.asus.customview.RecyclerView;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.daobao.asus.customview.R;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    private ItemRemoveRecyclerView recyclerView;
    private ArrayList<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        recyclerView = (ItemRemoveRecyclerView) findViewById(R.id.id_item_remove_recyclerview);

        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add(i + "");
        }

        final MyAdapter adapter = new MyAdapter(this, mList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(RecyclerViewActivity.this, "** " + mList.get(position) + " **", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(int position) {
                adapter.removeItem(position);
            }
        });
    }
}
