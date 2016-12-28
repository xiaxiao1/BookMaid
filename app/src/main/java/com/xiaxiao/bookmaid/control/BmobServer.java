package com.xiaxiao.bookmaid.control;

import android.content.Context;

import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.bean.FamousWord;
import com.xiaxiao.bookmaid.bean.RelationShip;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.listener.ErrorListener;
import com.xiaxiao.bookmaid.listener.OnResultListener;
import com.xiaxiao.bookmaid.listener.SuccessListener;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
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
    UIDialog waitdialog;
    boolean enableDialog=true;

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
        Context mContext;

        public Builder(Context context) {
            bmobServer = new BmobServer(context);
            this.mContext = context;

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
        public Builder enableDialog(boolean enableDialog) {
            bmobServer.enableDialog=enableDialog;
            return this;
        }
        public  BmobServer build() {
            bmobServer.waitdialog = new UIDialog(mContext);
            return bmobServer;
        }
    }

    /**
     * add a book
     * @param book
     */
    public void addBook(final BookBean book) {
        showWaitDialog();
        book.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                    dismissWaitDialog();
                    if (e == null) {
//                        book.setId(objectId);
                        handleSuccess(objectId);
                    } else {
                        handleError(e);
                    }

            }
        });
    }

    /**
     * add o book
     * @param book
     * @param bmobListener
     */
    public void addBook(final BookBean book, final BmobListener bmobListener) {
        addListener(bmobListener);
        addBook(book);
    }

    /**
     * update a book . useless now
     * @param book
     */
    public void updateBook(final BookBean book) {
        showWaitDialog();
        book.update(book.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    Util.L("update ok.");
                    handleSuccess(book.getObjectId());
                } else {
                    Util.L("update error:"+e.getMessage()+" errorCode:"+e.getErrorCode());
                    handleError(e);
                }
            }
        });
    }
    public void updateBook(final BookBean book, final BmobListener bmobListener) {
        addListener(bmobListener);
        updateBook(book);
    }

    public void getBooks() {
        if (mBmobQuery==null) {
            return;
        }
       /* if (type!=BOOKTYPE_ALL) {
            mBmobQuery.addWhereEqualTo("type", type);
        }*/
        showWaitDialog();
        mBmobQuery.findObjects(new FindListener<BookBean>() {
            @Override
            public void done(List<BookBean> list, BmobException e) {
                dismissWaitDialog();
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
    public void getBooksWithDefaultOptions(final BmobListener bmobListener) {
        addListener(bmobListener);
        mBmobQuery = new BmobQuery<BookBean>();
        mBmobQuery.order("-createdAt");
        mBmobQuery.include("recommendPerson");
        /*if (GlobalData.userId != null) {
            mBmobQuery.addWhereEqualTo("ownerId", GlobalData.userId);
        } else {
            mBmobQuery.addWhereEqualTo("ownerId", "-1");
        }*/
        getBooks();
    }

    public void getMyBooks(final BmobListener bmobListener) {
        addListener(bmobListener);
        mBmobQuery = new BmobQuery<RelationShip>();
        mBmobQuery.order("-createdAt");
        if (Util.isLogin()) {
            mBmobQuery.addWhereEqualTo("ownerId", Util.getUser2().getObjectId());
        } else {
            mBmobQuery.addWhereEqualTo("ownerId", "-1");
        }
        mBmobQuery.include("book");
        mBmobQuery.findObjects(new FindListener<RelationShip>() {
            @Override
            public void done(List<RelationShip> list, BmobException e) {
                if (e == null) {
                    List<BookBean> books = new ArrayList<BookBean>();
                    for (RelationShip r : list) {
                        books.add(r.getBook());
                    }
                    handleSuccess(books);
                } else {
                    handleError(e);
                }
            }
        });
    }


    public void getFamousWords() {
        if (mBmobQuery==null) {
            return;
        }
        showWaitDialog();
        mBmobQuery.findObjects(new FindListener<FamousWord>() {
            @Override
            public void done(List<FamousWord> list, BmobException e) {
                dismissWaitDialog();
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


    public void getAllIdeas(BmobListener bmobListener) {
        addListener(bmobListener);
        mBmobQuery = new BmobQuery();
        if (mBmobQuery==null) {
            handleError(null);
            return;
        }

        showWaitDialog();
        mBmobQuery.findObjects(new FindListener<BookNote>() {
            @Override
            public void done(List<BookNote> list, BmobException e) {
                dismissWaitDialog();
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


    public void getBookNotes(String bookId,BmobListener bmobListener) {
        addListener(bmobListener);
        BmobQuery<BookNote> query = new BmobQuery<>();
//        query.addWhereEqualTo("bookId", bookId);
        query.order("-createdAt");
        showWaitDialog();
        query.findObjects(new FindListener<BookNote>() {
            @Override
            public void done(List<BookNote> list, BmobException e) {
                dismissWaitDialog();
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

    //*******************************************************************************************//


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

    protected void showWaitDialog() {
        if (enableDialog) {
            waitdialog.showWaitDialog();
        }
    }
    protected  void dismissWaitDialog() {
        waitdialog.dismissWaitDialog();
    }
}
