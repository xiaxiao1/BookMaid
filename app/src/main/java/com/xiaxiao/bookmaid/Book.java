package com.xiaxiao.bookmaid;

import java.util.Date;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class Book {
    String name;
    int id;
    int type;
    long addedTime;

    public Book(long addedTime, int id, String name, int type) {
        this.addedTime = addedTime;
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public long getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(long addedTime) {
        this.addedTime = addedTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
