package com.xiaxiao.bookmaid;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class BookManager {
    BookDBHelper bookDBHelper;
    BookServer bookServer;


    public BookManager(Context context){
        bookDBHelper = new BookDBHelper(context, BookDBHelper.tableName, null, BookDBHelper.VERTION);
        bookServer = new BookServer(context);
    }
    public void add(Book book,OnResultListener onResultListener) {
        bookServer.add(book,onResultListener);
        Util.L("add book");

    }

    public void delete(Book book) {
        Util.L("delete book");
        bookDBHelper.delete(book);
    }

    public void update(Book book) {
        Util.L("update book");
        bookDBHelper.update(book);
    }

    public List<Book> query(Book book) {
        Util.L("query");
        return bookDBHelper.queryBooks(book.getName());
    }
    public List<Book> query(String bookName) {
        Util.L("query");
        return bookDBHelper.queryBooks(bookName);
    }

    public List<Book> getBooks(int type) {
        List<Book> list;
        list=bookDBHelper.getBooks(type);
        return list;
    }

    interface OnResultListener{
        public void onSuccess(String objectId);
        public void onError(BmobException e);

    }
}
