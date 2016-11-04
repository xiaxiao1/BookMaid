package com.xiaxiao.bookmaid;

import android.content.Context;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by xiaxi on 2016/11/3.
 */
public class BookServer {
    BookDBHelper bookDBHelper;

    public BookServer(Context context) {
        bookDBHelper = new BookDBHelper(context, BookDBHelper.tableName, null, BookDBHelper.VERTION);
    }
    public void add(final Book book, final BookManager.OnResultListener onResultListener) {
        book.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (onResultListener!=null) {
                    if (e == null) {
                        book.setId(objectId);
                        bookDBHelper.add(book);
                        onResultListener.onSuccess(objectId);
                    } else {
                        onResultListener.onError(e);
                    }
                }
            }
        });
    }
}
