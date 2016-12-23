package com.xiaxiao.bookmaid.listener;

import com.xiaxiao.bookmaid.util.Util;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by Administrator on 2016/11/4.
 */
public abstract class SuccessListener implements BmobListener {

        @Override
        public void onError(BmobException e) {
                if (e!=null) {
                        Util.L(e.getMessage());
                }
        }
}
