package com.xiaxiao.bookmaid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.bean.RelationShip;
import com.xiaxiao.bookmaid.util.BmobIniter;
import com.xiaxiao.bookmaid.util.Util;
import com.xiaxiao.bookmaid.widget.BottomView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        BottomView bottomView = (BottomView) findViewById(R.id.bottom);
        bottomView.setBottomClickListener(new BottomView.BottomClickListener(){
            @Override
            public void onBottomClick(int index) {
                Util.L("bottom index: "+index);
            }
        });
        BmobIniter.init(this);
        Button btn = (Button) findViewById(R.id.btn1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gogogo2();
            }
        });


    }

    public void gogogo() {
//        BmobQuery<RelationShip> relationShipBmobQuery = new BmobQuery<RelationShip>();
//        relationShipBmobQuery.order("-createdAt");
//        relationShipBmobQuery.findObjects(new FindListener<RelationShip>() {
//            @Override
//            public void done(List<RelationShip> list, BmobException e) {
//                if (e == null) {
//                    for (RelationShip r:list) {
//                        final String rId=r.getObjectId();
//                      /*  Util.L(r.getBookId());
//                        String bookId = r.getBookId();*/
//                        BmobQuery<BookBean> bookBeanBmobQuery = new BmobQuery<BookBean>();
////                        bookBeanBmobQuery.addWhereEqualTo("objectId", bookId);
//                        bookBeanBmobQuery.getObject(bookId, new QueryListener<BookBean>() {
//                            @Override
//                            public void done(BookBean bookBean, BmobException e) {
//                                Util.L(bookBean.getObjectId()+"    "+bookBean.getName());
//                                RelationShip relationShip = new RelationShip();
//                                relationShip.setBook(bookBean);
//                                relationShip.update(rId, new UpdateListener() {
//                                    @Override
//                                    public void done(BmobException e) {
//                                        if (e==null) {
//                                            Util.L("======================================upodate ok");
//                                        }
//                                    }
//                                });
//                            }
//                        });
//                    }
//
//                } else {
//                    Util.L(e.getMessage());
//                }
//            }
//        });
    }


    public void gogogo2() {
        BmobQuery<BookNote> relationShipBmobQuery = new BmobQuery<BookNote>();
        relationShipBmobQuery.order("-createdAt");
        relationShipBmobQuery.findObjects(new FindListener<BookNote>() {
            @Override
            public void done(List<BookNote> list, BmobException e) {
                if (e == null) {
                    for (BookNote r:list) {
                        final String rId=r.getObjectId();
                        Util.L(r.getBookId());
                        String ownerId = r.getBookId();
                        BmobQuery<BookBean> bookBeanBmobQuery = new BmobQuery<BookBean>();
//                        bookBeanBmobQuery.addWhereEqualTo("objectId", bookId);
                        bookBeanBmobQuery.getObject(ownerId, new QueryListener<BookBean>() {
                            @Override
                            public void done(BookBean bmobUser, BmobException e) {
//                                Util.L(bookBean.getObjectId()+"    "+bookBean.getName());
                                BookNote relationShip = new BookNote();
                                relationShip.setBook(bmobUser);
                                relationShip.update(rId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e==null) {
                                            Util.L("======================================upodate ok");
                                        }
                                    }
                                });
                            }
                        });
                    }

                } else {
                    Util.L(e.getMessage());
                }
            }
        });
    }



}
