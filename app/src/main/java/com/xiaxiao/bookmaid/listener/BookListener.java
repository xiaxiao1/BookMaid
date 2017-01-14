package com.xiaxiao.bookmaid.listener;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2016/11/4.
 */
public  interface BookListener {
    public void onSuccess(String objectId);
    public void onError(BmobException e);

    public void onResult(Object object);
}
