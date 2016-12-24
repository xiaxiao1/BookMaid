package com.xiaxiao.bookmaid.control;

import android.content.Context;

import com.xiaxiao.bookmaid.bean.Book;
import com.xiaxiao.bookmaid.bean.FamousWord;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.listener.ErrorListener;
import com.xiaxiao.bookmaid.listener.OnResultListener;
import com.xiaxiao.bookmaid.listener.SuccessListener;
import com.xiaxiao.bookmaid.util.GlobalData;
import com.xiaxiao.bookmaid.util.Util;

import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by xiaxi on 2016/11/3.
 */
public class BmobServer {

    BmobListener mSuccessListener;
    BmobListener mErrorListener;
    BmobQuery mBmobQuery;

    public static final int BOOKTYPE_HAVE=1;
    public static final int BOOKTYPE_NO_HAVE=0;
    public static final int BOOKTYPE_ALL=-1;
    protected BmobServer(Context context) {
    }

    /*public static Builder build(Context context) {
        return new Builder(context);
    }*/
    public static class Builder{
        BmobServer bmobServer;

        public Builder(Context context) {
            bmobServer = new BmobServer(context);
        }

        /**
         * add errorListener for the request
         * @param errorListener
         * @return
         */
        public Builder addErrorListener(ErrorListener errorListener){
            bmobServer.mErrorListener = errorListener;
            return this;
        }

        /**
         * add successListener for the request
         * @param successListener
         * @return
         */
        public Builder addSuccessListener(SuccessListener successListener) {
            bmobServer.mSuccessListener = successListener;
            return this;
        }

        /**
         * add full listener for the request
         * @param bmobListener
         * @return
         */
        public Builder addBmobListener(BmobListener bmobListener) {
            bmobServer.addListener(bmobListener);
            return this;
        }

        /**
         * add query terms for the requset
         * @param bmobQuery
         * @return
         */
        public Builder addBmobQuery(BmobQuery bmobQuery) {
            bmobServer.mBmobQuery = bmobQuery;
            return this;
        }
        public  BmobServer build() {
            return bmobServer;
        }
    }

    public void addBook(final Book book) {
        book.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                    if (e == null) {
                        book.setId(objectId);
                        handleSuccess(objectId);
                    } else {
                        handleError(e);
                    }

            }
        });
    }
    public void addBook(final Book book, final BmobListener bmobListener) {
        addListener(bmobListener);
        addBook(book);
    }

    public void updateBook(final Book book) {
        book.update(book.getId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Util.L("update ok.");
                    handleSuccess(book.getId());
                } else {
                    Util.L("update error:"+e.getMessage()+" errorCode:"+e.getErrorCode());
                    handleError(e);
                }
            }
        });
    }
    public void updateBook(final Book book, final BmobListener bmobListener) {
        addListener(bmobListener);
        updateBook(book);
    }

    public void getBooks(int type) {
        if (mBmobQuery==null) {
            return;
        }
        if (type!=BOOKTYPE_ALL) {
            mBmobQuery.addWhereEqualTo("type", type);
        }
//        mBmobQuery.order("-createdAt");
        mBmobQuery.findObjects(new FindListener<Book>() {
            @Override
            public void done(List<Book> list, BmobException e) {
                if (e == null) {
                    Util.L("query ok.");
                    for (Book b:list) {
                        b.setId(b.getObjectId());
                    }
                   handleSuccess(list);
                } else {
                    Util.L("query error:"+e.getMessage());
                    handleError(e);
                }
            }
        });
    }
    public void getBooksWithDefaultOptions(int type,final BmobListener bmobListener) {
        addListener(bmobListener);
        mBmobQuery = new BmobQuery<Book>();
        mBmobQuery.order("-createdAt");
        getBooks(type);
    }


    public void getFamousWords() {
        if (mBmobQuery==null) {
            return;
        }
        mBmobQuery.findObjects(new FindListener<FamousWord>() {
            @Override
            public void done(List<FamousWord> list, BmobException e) {
                if (e == null) {
                    Util.L("query ok.");
                    handleSuccess(list);
                } else {
                    Util.L("query error:"+e.getMessage());
                    handleError(e);
                }
            }
        });
    }
    public void  getFamousWordsWithDefaultOptions(final BmobListener bmobListener) {
        addListener(bmobListener);
        mBmobQuery = new BmobQuery<>();
        mBmobQuery.setLimit(100);
        mBmobQuery.order("-createdAt");
        getFamousWords();
    }


    protected void addListener(BmobListener bmobListener) {
        mSuccessListener=bmobListener;
        mErrorListener=bmobListener;
    }

    protected void handleSuccess(Object object) {
        if (mSuccessListener!=null) {
            mSuccessListener.onSuccess(object);
        }
    }
    protected void handleError(BmobException e) {
        if (mErrorListener!=null) {
            mErrorListener.onError(e);
        }
    }
}
