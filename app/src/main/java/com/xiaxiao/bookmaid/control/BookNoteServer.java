package com.xiaxiao.bookmaid.control;

import android.content.Context;

import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.listener.OnResultListener;
import com.xiaxiao.bookmaid.util.Util;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xiaxi on 2016/11/3.
 */
public class BookNoteServer {

    public BookNoteServer(Context context) {
    }
    public void add(final BookNote bookNote, final OnResultListener onResultListener) {
        bookNote.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (onResultListener!=null) {
                    if (e == null) {
                        bookNote.setId(objectId);
                        onResultListener.onSuccess(objectId);
                    } else {
                        onResultListener.onError(e);
                    }
                }
            }
        });
    }


    public void getBookNotes(String bookId,final OnResultListener onResultListener) {
        BmobQuery<BookNote> query = new BmobQuery<>();
        query.addWhereEqualTo("bookId", bookId);
        query.order("-createdAt");
        query.findObjects(new FindListener<BookNote>() {
            @Override
            public void done(List<BookNote> list, BmobException e) {
                if (e == null) {
                    Util.L("query ok.");
                    for (BookNote bn:list) {
                        bn.setId(bn.getObjectId());
                    }
                    onResultListener.onResult(list);
                } else {
                    Util.L("query error:"+e.getMessage());
                    onResultListener.onError(e);
                }
            }
        });
    }


}
