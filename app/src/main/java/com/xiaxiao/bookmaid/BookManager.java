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

    public void update(Book book,OnResultListener onResultListener) {
        Util.L("update book");
        bookServer.update(book,onResultListener);
    }

    public List<Book> query(Book book) {
        Util.L("query");
        return bookDBHelper.queryBooks(book.getName());
    }
    public List<Book> query(String bookName) {
        Util.L("query");
        return bookDBHelper.queryBooks(bookName);
    }

    public void getBooks(int type,OnResultListener onResultListener) {
        bookServer.getBooks(type,onResultListener);
    }

    public List<Book> getBooksInLocal(int type) {
        return bookDBHelper.getBooks(type);
    }

    public int clearLocal(){
        return bookDBHelper.clearTable();
    }
   /*abstract class  OnResultListener  implements BookListener{

        @Override
        public void onResult(Object object) {

        }

       @Override
       public abstract void onSuccess(String objectId);

       @Override
       public abstract void onError(BmobException e);
   }*/
}
