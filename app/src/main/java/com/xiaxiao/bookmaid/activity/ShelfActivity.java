package com.xiaxiao.bookmaid.activity;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaxiao.bookmaid.R;

public class ShelfActivity extends BaseActivity {

    Fragment3 fragment3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelf);
        fragment3 = new Fragment3();
        FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.frame, fragment3).show(fragment3).commit();
    }
}
