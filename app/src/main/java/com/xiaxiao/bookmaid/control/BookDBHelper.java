package com.xiaxiao.bookmaid.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/3.
 */
public class BookDBHelper extends SQLiteOpenHelper{

    //版本号
    public static final int VERTION=1;
    //表名
    public static final String tableName = "book";
    //建表语句
    private String createTable = "create table "+tableName+"(id varchar(30),name varchar(30),type int,added_time integer,read_status int)";
    //执行操作的对象
    private SQLiteDatabase db;
    //must have
    public BookDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int
            version) {
        super(context, name, factory, version);
        getDB();
    }

    //第一次创建数据库的时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Util.L("update database");
        db.execSQL("drop table book");
        db.execSQL(createTable);
    }

    /**
     * 向数据库添加一本书
     * @param book
     * @return
     */
    public boolean add(BookBean book) {
        ContentValues value = new ContentValues();
//        value.put("id",book.getId());
//        value.put("name",book.getName());
//        value.put("type",book.getType());
//        value.put("added_time",book.getAddedTime());
//        value.put("read_status",book.getReadStatus());
        if(-1==db.insert(tableName, null, value)){
            return false;
        }
        return true;
    }

    public void addBooks(List<BookBean> list) {
//        for (Book b:list) {
//            add(b);
//        }
    }

    /**
     * 从数据库删除一本书
     * @param book
     * @return
     */
    public boolean delete(BookBean book) {
        //删除条件
        String whereClause = "id=?";
        //删除条件参数
        String[] whereArgs = {""+book.getObjectId()+""};
        //执行删除
        if(0<db.delete(tableName,whereClause,whereArgs)){
            return true;
        }
        return false;
    }

    public int  clearTable() {
        return db.delete(tableName, null, null);
    }

    /**
     * 更新一本书的信息
     * @param book
     * @return
     */
    public boolean update(BookBean book) {
        ContentValues value = new ContentValues();
//        value.put("id",book.getId());
//        value.put("name",book.getName());
//        value.put("type",book.getType());
//        value.put("added_time",book.getAddedTime());
//        value.put("read_status",book.getReadStatus());
        //更新条件
        String whereClause = "id=?";
        //更新条件参数
        String[] whereArgs = {""+book.getObjectId()+""};
        if(0<db.update(tableName, value, whereClause, whereArgs)){
            Util.L("update local DB ok");
            return true;
        }
        Util.L("update local DB error");
        return false;
    }

    /**
     * 获取不同类型的书
     * @param type
     * @return
     */
    public List<BookBean> getBooks(int type) {
        //查询条件
        String whereClause = null;
        //查询条件参数
        String[] whereArgs = null;
        if (type!=-1) {
            whereClause = "type=?";
            //查询条件参数
            whereArgs = new String[]{String.valueOf(type)};
        }
        Cursor cursor=db.query(tableName, null, whereClause, whereArgs, null, null, null, null);
        Util.L("count :"+cursor.getCount());
        if (cursor.getCount()<=0) {
            Util.L("no database datas");
            return null;
        }
        List<BookBean> list=new ArrayList<>();
        while(cursor.moveToNext()) {
            BookBean b=new BookBean();
//            b.setId(cursor.getString(0));
//            b.setType(cursor.getInt(2));
//            b.setAddedTime(cursor.getInt(3));
//            b.setReadStatus(cursor.getInt(4));
            list.add(b);
        }
        cursor.close();
        return list;
    }

    /**
     * 模糊查询某一本书
     * @param bookName
     * @return
     */
    public List<BookBean> queryBooks(String bookName) {
        //查询条件
        String whereClause = "name like ?";
        //查询条件参数
        String[] whereArgs = {"%"+bookName+"%"};
        Cursor cursor=db.query(tableName, null, whereClause, whereArgs, null, null, null, null);
        Util.L("count :"+cursor.getCount());
        List<BookBean> list=new ArrayList<>();
        while(cursor.moveToNext()) {
            BookBean b=new BookBean();
//            b.setId(cursor.getString(0));
//            b.setType(cursor.getInt(2));
//            b.setAddedTime(cursor.getInt(3));
//            b.setReadStatus(cursor.getInt(4));
            list.add(b);
        }
        cursor.close();
        return list;
    }


    private SQLiteDatabase getDB() {
        if (db==null) {
            db = this.getWritableDatabase();
        }
        return db;
    }
    private void closeDB() {
        if (db!=null) {
            db.close();
        }
    }
}
