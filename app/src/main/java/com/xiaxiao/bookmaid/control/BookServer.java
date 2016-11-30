package com.xiaxiao.bookmaid.control;

import android.content.Context;

import com.xiaxiao.bookmaid.bean.Book;
import com.xiaxiao.bookmaid.bean.FamousWord;
import com.xiaxiao.bookmaid.listener.OnResultListener;
import com.xiaxiao.bookmaid.util.GlobalData;
import com.xiaxiao.bookmaid.util.Util;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xiaxi on 2016/11/3.
 */
public class BookServer {
    BookDBHelper bookDBHelper;

    public BookServer(Context context) {
        bookDBHelper = new BookDBHelper(context, BookDBHelper.tableName, null, BookDBHelper.VERTION);
    }

    public void add(final Book book, final OnResultListener onResultListener) {
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

    public <E extends  BmobObject> void xx(E b) {

    }
    public void update(final Book book, final OnResultListener onResultListener) {
        book.update(book.getId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Util.L("update ok.");
                    bookDBHelper.update(book);
                    onResultListener.onSuccess(book.getId());
                } else {
                    Util.L("update error:"+e.getMessage()+" errorCode:"+e.getErrorCode());
                    onResultListener.onError(e);
                }
            }
        });
    }
    public void getBooks(int type,final OnResultListener onResultListener) {
        BmobQuery<Book> query = new BmobQuery<>();
        if (type!=-1) {
            query.addWhereEqualTo("type", type);
        }
        if (GlobalData.userId != null) {
            query.addWhereEqualTo("ownerId", GlobalData.userId);
        } else {
            query.addWhereEqualTo("ownerId", "-1");
        }
Util.L(BmobUser.getCurrentUser().getObjectId()+"  "+Util.getUserId());
        query.order("-createdAt");
        query.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> list, BmobException e) {
                if (e == null) {
                    Util.L("query ok.");
                    for (Book b:list) {
                        b.setId(b.getObjectId());
                    }
                    bookDBHelper.clearTable();
                    bookDBHelper.addBooks(list);
                    onResultListener.onResult(list);
                } else {
                    Util.L("query error:"+e.getMessage());
                    onResultListener.onError(e);
                }
            }
        });
    }

    public void  getFamousWords(final OnResultListener onResultListener) {
        BmobQuery<FamousWord> query = new BmobQuery<>();
        query.setLimit(100);
        query.order("-createdAt");
        query.findObjects(new FindListener<FamousWord>() {
            @Override
            public void done(List<FamousWord> list, BmobException e) {
                if (e == null) {
                    Util.L("query ok.");

                    onResultListener.onResult(list);
                } else {
                    Util.L("query error:"+e.getMessage());
                    onResultListener.onError(e);
                }
            }
        });
    }


}
