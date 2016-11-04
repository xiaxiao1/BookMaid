package com.xiaxiao.bookmaid;

import android.util.Log;

import java.text.SimpleDateFormat;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class Util {
    private static final String TAG = "xiaxaio";
    public  static void L(String message) {
        Log.i(TAG, message);
    }

    public static String getTimeStr(long l) {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        return s.format(l);
    }
}
