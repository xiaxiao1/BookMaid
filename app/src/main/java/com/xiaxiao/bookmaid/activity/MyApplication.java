package com.xiaxiao.bookmaid.activity;

import android.app.Application;

import com.xiaxiao.bookmaid.util.BmobIniter;

/**
 * Created by xiaxiao on 2016/11/7.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BmobIniter.init(this);
    }
}
