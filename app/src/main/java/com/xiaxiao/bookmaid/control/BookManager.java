package com.xiaxiao.bookmaid.control;

import android.content.Context;

import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.listener.OnResultListener;
import com.xiaxiao.bookmaid.util.Util;

import java.util.List;

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
    public void add(BookBean book, OnResultListener onResultListener) {
//        bookServer.add(book,onResultListener);
        Util.L("add book");

    }

    public void delete(BookBean book) {
        Util.L("delete book");
//        bookDBHelper.delete(book);
    }

    public void update(BookBean book,OnResultListener onResultListener) {
        Util.L("update book");
//        bookServer.update(book,onResultListener);
    }

    public List<BookBean> query(BookBean book) {
        Util.L("query");
        return null;
    }
    public List<BookBean> query(String bookName) {
        Util.L("query");
        return null;
    }

    public void getBooks(int type,OnResultListener onResultListener) {
        bookServer.getBooks(type,onResultListener);
    }

    public List<BookBean> getBooksInLocal(int type) {
        return null;
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
