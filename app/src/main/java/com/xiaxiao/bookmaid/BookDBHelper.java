package com.xiaxiao.bookmaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
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
    private String createTable = "create table "+tableName+"(id varchar(30),name varchar(30),type int,added_time integer)";
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
    }

    /**
     * 向数据库添加一本书
     * @param book
     * @return
     */
    public boolean add(Book book) {
        ContentValues value = new ContentValues();
        value.put("id",book.getId());
        value.put("name",book.getName());
        value.put("type",book.getType());
        value.put("added_time",book.getAddedTime());
        if(-1==db.insert(tableName, null, value)){
            return false;
        }
        return true;
    }

    /**
     * 从数据库删除一本书
     * @param book
     * @return
     */
    public boolean delete(Book book) {
        //删除条件
        String whereClause = "id=?";
        //删除条件参数
        String[] whereArgs = {"'"+book.getId()+"'"};
        //执行删除
        if(0<db.delete(tableName,whereClause,whereArgs)){
            return true;
        }
        return false;
    }

    /**
     * 更新一本书的信息
     * @param book
     * @return
     */
    public boolean update(Book book) {
        ContentValues value = new ContentValues();
        value.put("id",book.getId());
        value.put("name",book.getName());
        value.put("type",book.getType());
        value.put("added_time",book.getAddedTime());
        //更新条件
        String whereClause = "id=?";
        //更新条件参数
        String[] whereArgs = {"'"+book.getId()+"'"};
        if(0<db.update(tableName, value, whereClause, whereArgs)){
            return true;
        }
        return false;
    }

    /**
     * 获取不同类型的书
     * @param type
     * @return
     */
    public List<Book> getBooks(int type) {
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
        List<Book> list=new ArrayList<>();
        while(cursor.moveToNext()) {
            Book b=new Book(cursor.getString(1));
            b.setId(cursor.getString(0));
            b.setType(cursor.getInt(2));
            b.setAddedTime(cursor.getInt(3));
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
    public List<Book> queryBooks(String bookName) {
        //查询条件
        String whereClause = "name like ?";
        //查询条件参数
        String[] whereArgs = {"%"+bookName+"%"};
        Cursor cursor=db.query(tableName, null, whereClause, whereArgs, null, null, null, null);
        Util.L("count :"+cursor.getCount());
        List<Book> list=new ArrayList<>();
        while(cursor.moveToNext()) {
            Book b=new Book(cursor.getString(1));
            b.setId(cursor.getString(0));
            b.setType(cursor.getInt(2));
            b.setAddedTime(cursor.getInt(3));
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
}
