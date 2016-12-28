package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.bean.RelationShip;
import com.xiaxiao.bookmaid.util.BitmapUtil;
import com.xiaxiao.bookmaid.util.BmobIniter;
import com.xiaxiao.bookmaid.util.RuntimePermissionsManager;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;
import com.xiaxiao.bookmaid.widget.BottomView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class TestActivity extends BaseActivity {
ImageView testImg;
    Bitmap bitmap;
    private  UIDialog uiDialog ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getRuntimePermissionManager(this);
        runtimePermissionsManager.requestPermissions();
        uiDialog = new UIDialog(TestActivity.this);
        BottomView bottomView = (BottomView) findViewById(R.id.bottom);
        testImg = (ImageView) findViewById(R.id.test_img);
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
//                gogogo2();
//                dede();
            }
        });
    }

    public void photo() {
        uiDialog.showChooseDialog(new UIDialog.CustomDialogListener() {
            @Override
            public void onItemClick(int index) {
                switch (index) {
                    case 0:
                        //paizhao
                        BitmapUtil.doTakePhoto(TestActivity.this);
                        break;
                    case 1:
                        //xiangce
                        BitmapUtil.doPickPhotoFromGallery(TestActivity.this);
                        break;
                    case 2:
                        //quxiao
                        uiDialog.dismissCustomDialog();
                        break;
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        uiDialog.dismissCustomDialog();
        switch (requestCode) {
            case BitmapUtil.PHOTO_PICKED_WITH_DATA:
                Util.toast(this,"从相册里选");
                Uri photo_uri = data.getData();
                try {
                    bitmap = Bitmap.createScaledBitmap(BitmapUtil.getThumbnail(photo_uri, this),
                                800, 800, true);
                    testImg.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case BitmapUtil.CAMERA_WITH_DATA:
                Util.toast(this,"拍照的");
                File file = new File(BitmapUtil.HEAD_IMAGE_PATH + "temp.jpg");
                try {
                    bitmap = Bitmap.createScaledBitmap(BitmapUtil.getThumbnail(file, this), 800,
                                800, true);
                    testImg.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        runtimePermissionsManager.handle(requestCode, permissions, grantResults);
    }

    /* public void gogogo() {
//        BmobQuery<RelationShip> relationShipBmobQuery = new BmobQuery<RelationShip>();
//        relationShipBmobQuery.order("-createdAt");
//        relationShipBmobQuery.findObjects(new FindListener<RelationShip>() {
//            @Override
//            public void done(List<RelationShip> list, BmobException e) {
//                if (e == null) {
//                    for (RelationShip r:list) {
//                        final String rId=r.getObjectId();
//                      *//*  Util.L(r.getBookId());
//                        String bookId = r.getBookId();*//*
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
*/


    public void dede() {



        BmobQuery<BookBean> bookBeanBmobQuery = new BmobQuery<>();
        bookBeanBmobQuery.findObjects(new FindListener<BookBean>() {
            @Override
            public void done(final List<BookBean> list, BmobException e) {
                BmobQuery<BmobUser> bmobUserBmobQuery = new BmobQuery<>();
                bmobUserBmobQuery.getObject("6ac3bcb9a7", new QueryListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if (e==null) {
                            Util.L(bmobUser.getUsername());
                            for (BookBean book:list) {
                                book.setRecommendPerson(bmobUser);
                                book.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e==null) {
                                            Util.L("更新推荐人成功");
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
    }


}
