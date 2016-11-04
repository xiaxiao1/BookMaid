package com.xiaxiao.bookmaid;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class Util {
    private static final String TAG = "xiaxiao";
    public  static void L(String message) {
        Log.i(TAG, message);
    }

    public static void toast(Context context, String message) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
    public static String getTimeStr(long l) {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        return s.format(l);
    }
}
