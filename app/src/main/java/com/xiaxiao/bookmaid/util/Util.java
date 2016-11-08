package com.xiaxiao.bookmaid.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.xiaxiao.bookmaid.bean.FamousWord;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class Util {
    private static String[] colorNumber={"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
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

    public static String getRandomColor() {
        int i;
        StringBuffer sb = new StringBuffer();
        sb.append("#");
        for (int a=0;a<6;a++) {
            i= (int) (Math.random() * 16);
            sb.append(colorNumber[i]);
        }
        return sb.toString();
    }

    public static boolean isLogin(){
        BmobUser bmobUser = BmobUser.getCurrentUser(BmobUser.class);
        return bmobUser!=null&&bmobUser.getObjectId()!=null;
    }

    public static FamousWord pickOneFrom(List<FamousWord> list){
        if (list==null||list.size()==0) {
            return null;
        }
        int length=list.size();
        int index=(int)(Math.random()*length);
        return list.get(index);
    }

    public static String getUserId() {
        return BmobUser.getCurrentUser().getObjectId();
    }
}
