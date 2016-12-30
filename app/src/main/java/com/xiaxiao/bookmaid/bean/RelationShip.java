package com.xiaxiao.bookmaid.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class RelationShip extends BmobObject{

    /*
    * buyType=1: 已买
    *      0：没买
    *     -1：全部
    * */
    int buyType;
    /*
    * readType=0: 没读
    *            1：在读
    *            2：已读
    * */
    int readType;
    long addedTime;

    String ownerId;
    BmobUser owner;
    BookBean book;


    public RelationShip(String name, String id, int type, long addedTime, int readStatus) {
        this.readType =readStatus;
        this.addedTime = addedTime;

        this.buyType = type;
    }

    public RelationShip(RelationShip book) {

        this.addedTime=book.getAddedTime();
        this.buyType =book.getBuyType();
        this.readType =book.getReadType();
    }
    public RelationShip(String name, int type, long addedTime) {
        this.addedTime = addedTime;
        this.buyType = type;
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
                ", buyType=" + buyType +
                ", readType=" + readType +
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



    public int getBuyType() {
        return buyType;
    }

    public void setBuyType(int buyType) {
        this.buyType = buyType;
    }

    public int getReadType() {
        return readType;
    }

    public void setReadType(int readType) {
        this.readType = readType;
    }

}
