package com.xiaxiao.bookmaid.activity;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.util.Util;

public class ShelfActivity extends BaseActivity {

    Fragment3 fragment3;
    ImageView back_img;
    ImageView add_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf);
        initViews();
        fragment3 = new Fragment3();
        FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.frame, fragment3).show(fragment3).commit();
    }

    public void initViews() {
        back_img = (ImageView) findViewById(R.id.shelf_back_img);
        add_img = (ImageView) findViewById(R.id.shelf_add_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShelfActivity.this.finish();
            }
        });

        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.goFindBookPage(ShelfActivity.this);
            }
        });
    }
}
