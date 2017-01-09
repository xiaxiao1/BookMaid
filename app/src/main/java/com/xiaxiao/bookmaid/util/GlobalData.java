package com.xiaxiao.bookmaid.util;

import android.app.Activity;

import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.bean.FamousWord;
import com.xiaxiao.bookmaid.bean.MyUser;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by xiaxi on 2016/11/6.
 */
public class GlobalData {
    public static BookBean book;
    public static BookNote bookNote;
    public static Activity activity;
    public static List<FamousWord> famousWords;
    public static String userId;
    public static MyUser bmobUser;
    public static int findPageType=0;//0  search;1: add
}
