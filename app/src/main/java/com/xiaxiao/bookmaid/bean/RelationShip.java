package com.xiaxiao.bookmaid.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class RelationShip extends BmobObject{

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

    String ownerId;
    BmobUser owner;
    BookBean book;


    public RelationShip(String name, String id, int type, long addedTime, int readStatus) {
        this.readStatus=readStatus;
        this.addedTime = addedTime;

        this.type = type;
    }

    public RelationShip(RelationShip book) {

        this.addedTime=book.getAddedTime();
        this.type=book.getType();
        this.readStatus=book.getReadStatus();
    }
    public RelationShip(String name, int type, long addedTime) {
        this.addedTime = addedTime;
        this.type = type;
    }
    public RelationShip(){}

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public BmobUser getOwner() {
        return owner;
    }

    public void setOwner(BmobUser owner) {
        this.owner = owner;
    }



    @Override
    public String toString() {
        return "Book{" +
                "addedTime=" + addedTime +
                ", type=" + type +
                ", readStatus=" + readStatus +
                ", ownerId='" + ownerId + '\'' +
                '}';
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public long getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(long addedTime) {
        this.addedTime = addedTime;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(int readStatus) {
        this.readStatus = readStatus;
    }

}
