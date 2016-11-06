package com.xiaxiao.bookmaid.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiaxi on 2016/11/6.
 */
public class BookNote extends BmobObject{
    String id;
    String content;
    String bookId;
    String userName;

    public BookNote(String content, String id, String bookId, String userName) {
        this.content = content;
        this.id = id;
        this.bookId = bookId;
        this.userName = userName;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
