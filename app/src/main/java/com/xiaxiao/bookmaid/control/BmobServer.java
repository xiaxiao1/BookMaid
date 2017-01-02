package com.xiaxiao.bookmaid.control;

import android.content.Context;

import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.bean.FamousWord;
import com.xiaxiao.bookmaid.bean.MyUser;
import com.xiaxiao.bookmaid.bean.RelationShip;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.listener.ErrorListener;
import com.xiaxiao.bookmaid.listener.SuccessListener;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

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
            mBmobQuery.addWhereEqualTo("owner", Util.getUser());
        } else {
            mBmobQuery.addWhereEqualTo("owner", null);
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
        mBmobQuery = new BmobQuery<BookNote>();
        if (mBmobQuery==null) {
            handleError(null);
            return;
        }

        showWaitDialog();
        mBmobQuery.order("-createdAt");
        mBmobQuery.include("whoWrite,replyWhos,book,book.recommendPerson");
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
        mBmobQuery = new BmobQuery<BookNote>();
//        query.addWhereEqualTo("bookId", bookId);
        mBmobQuery.order("-createdAt");
        mBmobQuery.include("whoWrite,replyWhos,book");
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

    /**
     * add a book
     * @param bookNote
     * @param bmobListener
     */
    public void addBookNote(final BookNote bookNote,BmobListener bmobListener) {
        addListener(bmobListener);
        showWaitDialog();
        bookNote.save(new SaveListener<String>() {
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

    public void upFile(File file,BmobListener bmobListener) {
        addListener(bmobListener);
        showWaitDialog();
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    handleSuccess(bmobFile);
                } else {
                    handleError(e);
                }
            }
        });
    }
    public void updateBookCoverImage(File coverfile, final BookBean bookBean,BmobListener bmobListener) {
        addListener(bmobListener);
        final BmobFile bmobFile = new BmobFile(coverfile);
        showWaitDialog();
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    bookBean.setCoverImage(bmobFile);
                    bookBean.update(bookBean.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            dismissWaitDialog();
                            if (e == null) {
                                handleSuccess(1);
                            } else {
                                handleError(e);
                            }
                        }
                    });
                } else {
                    dismissWaitDialog();
                    handleError(e);
                }
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
//                tv2.setText(""+value);
            }
        });
    }

    public void updateUserheadImage(File headfile, final BmobUser bmobUser, BmobListener bmobListener) {
        addListener(bmobListener);
        final BmobFile bmobFile = new BmobFile(headfile);
        showWaitDialog();
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    MyUser myUser=(MyUser)bmobUser;
                    myUser.setHeadImage(bmobFile);
                    myUser.update(myUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            dismissWaitDialog();
                            if (e == null) {
                                handleSuccess(1);
                            } else {
                                handleError(e);
                            }
                        }
                    });
                } else {
                    dismissWaitDialog();
                    handleError(null);
                }
            }

            @Override
            public void onProgress(Integer value) {
                // 返回的上传进度（百分比）
//                tv2.setText(""+value);
            }
        });
    }

    /**
     * add a relationship
     * @param relationShip
     */
    public void addRelationShip(final RelationShip relationShip) {
        showWaitDialog();
        relationShip.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    handleSuccess(objectId);
                } else {
                    handleError(e);
                }

            }
        });
    }

    public void addRelationShip(final RelationShip relationShip, BmobListener bmobListener) {
        addListener(bmobListener);
        addRelationShip(relationShip);
    }

    public void login(BmobUser bmobUser, BmobListener bmobListener) {
        BmobUser b=bmobUser;
        addListener(bmobListener);
        showWaitDialog();
        bmobUser.login(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    Util.L("login ok");
                    handleSuccess(bmobUser);
                } else {
                    Util.L("login error");
                    handleError(e);
                }
            }
        });
    }

    public void signUp(BmobUser bmobUser, BmobListener bmobListener) {
        addListener(bmobListener);
        showWaitDialog();
        bmobUser.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    handleSuccess(bmobUser);
                } else {
                    handleError(e);
                }
            }
        });
    }

    public void getShelf(BmobUser bmobUser, BmobListener bmobListener) {
        addListener(bmobListener);
        mBmobQuery = new BmobQuery<RelationShip>();
        mBmobQuery.addWhereEqualTo("owner", bmobUser);
        mBmobQuery.include("book.objectId");
        showWaitDialog();
        mBmobQuery.findObjects(new FindListener<RelationShip>() {
            @Override
            public void done(List<RelationShip> list, BmobException e) {
                dismissWaitDialog();
                if (e == null) {
                    handleSuccess(list);
                } else {
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
