package com.xiaxiao.bookmaid;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class BookManager {
    BookDBHelper bookDBHelper;



    public BookManager(Context context){
        bookDBHelper = new BookDBHelper(context, BookDBHelper.tableName, null, 1);
    }
    public boolean add(Book book) {
        boolean ok=false;
        Util.L("add book");
        ok=bookDBHelper.add(book);
        return ok;
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
}
