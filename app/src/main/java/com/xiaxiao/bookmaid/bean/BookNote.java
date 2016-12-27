package com.xiaxiao.bookmaid.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 * Created by xiaxi on 2016/11/6.
 */
public class BookNote extends BmobObject{
    String id;
    String content;
    String bookId;
    String userName;
    String ownerId;
    String replyWho;

    BmobUser whoWrite;
    BmobUser replyWhos;
    BookBean book;

    public BookNote(String content, String id, String bookId, String userName) {
        this.content = content;
        this.id = id;
        this.bookId = bookId;
        this.userName = userName;
    }

    public BookNote() {

    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public BmobUser getReplyWhos() {
        return replyWhos;
    }

    public void setReplyWhos(BmobUser replyWhos) {
        this.replyWhos = replyWhos;
    }

    public BmobUser getWhoWrite() {
        return whoWrite;
    }

    public void setWhoWrite(BmobUser whoWrite) {
        this.whoWrite = whoWrite;
    }

    public String getReplyWho() {
        return replyWho;
    }

    public void setReplyWho(String replyWho) {
        this.replyWho = replyWho;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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
