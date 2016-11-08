package com.xiaxiao.bookmaid.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/11/8.
 */
public class FeedBack extends BmobObject{
    String userId;
    String message;

    public FeedBack(String message, String userId) {
        this.message = message;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "FeedBack{" +
                "message='" + message + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
