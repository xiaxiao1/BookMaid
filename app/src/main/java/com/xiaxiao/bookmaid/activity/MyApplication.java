package com.xiaxiao.bookmaid.activity;

import android.app.Application;

import com.xiaxiao.bookmaid.util.BmobIniter;
import com.xiaxiao.bookmaid.util.Util;

/**
 * Created by xiaxiao on 2016/11/7.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Util.L("start application");
        BmobIniter.init(this);
    }
}
