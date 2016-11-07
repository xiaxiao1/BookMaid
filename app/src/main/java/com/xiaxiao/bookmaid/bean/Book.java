package com.xiaxiao.bookmaid.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class Book extends BmobObject{
    String name;
    String id;
    /*
    * type=1: 已买
    *      0：没买
    *     -1：全部
    * */
    int type;
    /*
    * readStatus=0: 没读
    *            1：在读
    *            2：已读
    * */
    int readStatus;
    long addedTime;

    public Book( String name, String id, int type,long addedTime,int readStatus) {
        this.readStatus=readStatus;
        this.addedTime = addedTime;
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Book(Book book) {
        this.name=book.getName();
        this.id=book.getId();
        this.addedTime=book.getAddedTime();
        this.type=book.getType();
    }
    public Book(String name, int type,long addedTime) {
        this.addedTime = addedTime;
        this.name = name;
        this.type = type;
    }

    public long getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(long addedTime) {
        this.addedTime = addedTime;
    }

    public String  getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Book(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

    @Override
    public String toString() {
        return "Book{" +
                "addedTime=" + addedTime +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", type=" + type +
                ", readStatus=" + readStatus +
                '}';
    }
}
