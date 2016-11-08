package com.xiaxiao.bookmaid.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by xiaxiao on 2016/11/8.
 */
public class FamousWord extends BmobObject{
    String userId;
    String content;
    String writer;
    String username;

    public FamousWord(String content,String writer, String userId, String username) {
        this.content = content;
        this.userId = userId;
        this.username = username;
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "FamousWord{" +
                "content='" + content + '\'' +
                ", userId='" + userId + '\'' +
                ", writer='" + writer + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
