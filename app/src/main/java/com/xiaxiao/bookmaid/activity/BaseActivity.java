package com.xiaxiao.bookmaid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaxiao.bookmaid.control.BmobServer;

public class BaseActivity extends AppCompatActivity {

    public BmobServer.Builder requsetBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requsetBuilder = new BmobServer.Builder(this);

    }

}
