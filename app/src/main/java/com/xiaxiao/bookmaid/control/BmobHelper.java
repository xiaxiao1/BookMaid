package com.xiaxiao.bookmaid.control;

import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.listener.OnResultListener;
import com.xiaxiao.bookmaid.util.Util;

import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;

/**
 * Created by xiaxi on 2016/11/30.
 */
public class BmobHelper {
    public static  void getLists(BmobQuery<BookBean> query , final OnResultListener onResultListener) {

FindListener<BookBean> findListener=new FindListener<BookBean>() {
    @Override
    public void done(List<BookBean> list, BmobException e) {
        Util.L("books size:"+list.size());
        onResultListener.onResult(list);
    }
};
        query.findObjects(findListener);
    }

    public  static void inserList(List<BmobObject> list, final OnResultListener onResultListener) {
        //第二种方式：v3.5.0开始提供
        for (int i=0;i<list.size();i++) {

        }
        new BmobBatch().insertBatch(list).doBatch(new QueryListListener<BatchResult>() {

            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if(e==null){
                    for(int i=0;i<o.size();i++){
                        BatchResult result = o.get(i);
                        BmobException ex =result.getError();
                        if(ex==null){
                            Util.L("第"+i+"个数据批量删除成功");
                            onResultListener.onSuccess(i+"");
                        }else{
                            Util.L("第"+i+"个数据批量删除失败："+ex.getMessage()+","+ex.getErrorCode());
                        }
                    }
                }else{
                    Util.L( "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }
}
