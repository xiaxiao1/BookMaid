package com.xiaxiao.bookmaid.util;

import android.app.Activity;

import com.xiaxiao.bookmaid.bean.Book;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.bean.FamousWord;

import java.util.List;

import cn.bmob.v3.BmobUser;

/**
 * Created by xiaxi on 2016/11/6.
 */
public class GlobalData {
    public static Book book;
    public static BookNote bookNote;
    public static Activity activity;
    public static List<FamousWord> famousWords;
    public static String userId;
    public static BmobUser bmobUser;
}
