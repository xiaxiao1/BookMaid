package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.bean.RelationShip;
import com.xiaxiao.bookmaid.control.BmobServer;
import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.BitmapUtil;
import com.xiaxiao.bookmaid.util.CropUtil;
import com.xiaxiao.bookmaid.util.GlideHelper;
import com.xiaxiao.bookmaid.util.GlobalData;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;

public class AddBookActivity extends BaseActivity implements View.OnClickListener{

    ImageView back_img;
    ImageView bookCover_img;
    EditText editname_et;
    EditText editWriter_et;
    EditText editIntroduce_et;
    RelativeLayout buyArea_rl;
    RelativeLayout readAera_rl;
    TextView buyLabel_tv;
    TextView readLabel_tv;
    Button addBook_btn;

    BookBean book;
    //true:have cover
    boolean hasCover=false;
    String bookName;
    String bookWriter;
    String bookIntroduce;
    int buyType;
    int readType;
    //true:book is new
    boolean bookIsNew=false;
    boolean changeRelationShip=false;
    String relationShipId;
    int currentReadType=0;
    int currentBuyType=0;
    UIDialog uiDialog;
    Bitmap  coverBitmap;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        initViews();
        uiDialog = new UIDialog(this);
        bookIsNew = getIntent().getBooleanExtra("bookIsNew", false);
        changeRelationShip = getIntent().getBooleanExtra("changeRelationShip", false);
        relationShipId = getIntent().getStringExtra("relationShipId");
        currentBuyType = getIntent().getIntExtra("buyType", 0);
        currentReadType = getIntent().getIntExtra("readType", 0);
        if (currentBuyType==1) {
            buyType=1;
            buyLabel_tv.setText("已买");
        }
        if (currentReadType==1) {
            readType=1;
            readLabel_tv.setText("已读");
        }
        //如果是changebook   那么book一定是已经存在的了，所以这里两种跳转路径都可以使用bookIsNew来判断
        if (bookIsNew) {
            book = new BookBean();
        } else {
            book = GlobalData.book;
            if (book.getCoverImage()!=null&&book.getCoverImage().getUrl()!=null) {
                GlideHelper.loadImage(this,book.getCoverImage().getUrl(),bookCover_img,R.drawable.book);
            }
            editname_et.setText(book.getName());
            editWriter_et.setText(book.getWriter());
            editIntroduce_et.setText(book.getIntroduce());
            bookCover_img.setEnabled(false);
            editname_et.setEnabled(false);
            editWriter_et.setEnabled(false);
            editIntroduce_et.setEnabled(false);
        }

    }

    public void initViews() {
        back_img = (ImageView) findViewById(R.id.add_book_back_img);
        bookCover_img = (ImageView) findViewById(R.id.add_book_cover_img);
        editname_et = (EditText) findViewById(R.id.add_book_name_et);
        editWriter_et = (EditText) findViewById(R.id.add_book_writer_et);
        editIntroduce_et = (EditText) findViewById(R.id.add_book_introduce_et);
        buyArea_rl = (RelativeLayout) findViewById(R.id.add_book_buy_rl);
        readAera_rl = (RelativeLayout) findViewById(R.id.add_book_read_rl);
        buyLabel_tv = (TextView) findViewById(R.id.add_book_buy_label_tv);
        readLabel_tv = (TextView) findViewById(R.id.add_book_read_label_tv);
        addBook_btn = (Button) findViewById(R.id.add_book_add_btn);

        back_img.setOnClickListener(this);
        bookCover_img.setOnClickListener(this);
        buyArea_rl.setOnClickListener(this);
        readAera_rl.setOnClickListener(this);
        addBook_btn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.add_book_back_img:
                finish();
                break;
            case R.id.add_book_cover_img:
                takePhoto();
                break;
            case R.id.add_book_buy_rl:
                uiDialog.showChooseTypeDialog("未买", "已买", new UIDialog.CustomDialogListener() {
                    @Override
                    public void onItemClick(int index) {
                        if (index==0) {
                            buyType=0;
                            buyLabel_tv.setText("未买");
                        }
                        if (index==1) {
                            buyType=1;
                            buyLabel_tv.setText("已买");
                        }
                    }
                });
                break;
            case R.id.add_book_read_rl:
                uiDialog.showChooseTypeDialog("未读", "已读", new UIDialog.CustomDialogListener() {
                    @Override
                    public void onItemClick(int index) {
                        if (index==0) {
                            readType=0;
                            readLabel_tv.setText("未读");
                        }
                        if (index==1) {
                            readType=1;
                            readLabel_tv.setText("已读");
                        }
                    }
                });
                break;
            case R.id.add_book_add_btn:
                if (changeRelationShip) {
                    updateRelationShip();
                }else {
                    if (bookIsNew) {
                        addToBookAndShelf();
                    } else {
                        justAddtoShelf();
                    }
                }

                break;
        }

    }


    public   void takePhoto() {
        Util.takePhoto(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BitmapUtil.PHOTO_PICKED_WITH_DATA:
                Util.toast(this,"从相册里选");
                Uri photo_uri = data.getData();
                try {
                    coverBitmap = Bitmap.createScaledBitmap(BitmapUtil.getThumbnail(photo_uri, this),
                            400, 400, true);

                    if (coverBitmap!=null) {
                        bookCover_img.setImageBitmap(coverBitmap);
                        hasCover=true;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case BitmapUtil.CAMERA_WITH_DATA:
                Util.toast(this,"拍照的");
                final File file = new File(BitmapUtil.HEAD_IMAGE_PATH + "temp.jpg");
                try {
                    coverBitmap = Bitmap.createScaledBitmap(BitmapUtil.getThumbnail(file, this), 400,
                            400, true);
                    if (coverBitmap!=null) {
                        bookCover_img.setImageBitmap(coverBitmap);
                        hasCover=true;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void justAddtoShelf() {
        RelationShip relationShip = new RelationShip();
        relationShip.setBuyType(buyType);
        relationShip.setReadType(readType);
        relationShip.setOwner(Util.getUser());
        relationShip.setBook(book);
        requsetBuilder
                .build()
                .addRelationShip(relationShip,new BmobListener(){
            @Override
            public void onSuccess(Object object) {
                Util.toast(AddBookActivity.this,"添加成功 relationship");
                book.setOwnNumber(book.getOwnNumber()+buyType);
                book.setReadNumber(book.getReadNumber()+readType);
                requsetBuilder
                        .enableDialog(false)
                        .build()
                        .updateBook(book, new BmobListener() {
                            @Override
                            public void onSuccess(Object object) {
                                Util.toast(AddBookActivity.this,"添加成功 end end");
                                finishWithResult();
                            }

                            @Override
                            public void onError(BmobException e) {

                            }
                        });
            }

            @Override
            public void onError(BmobException e) {
                Util.toast(AddBookActivity.this,"添加失败 relationship");
            }
        });

    }
    public void addToBookAndShelf() {
        bookName = editname_et.getText().toString().trim();
        bookWriter = editWriter_et.getText().toString().trim();
        bookIntroduce = editIntroduce_et.getText().toString().trim();
        if(checkNotNull(bookName,bookWriter,bookIntroduce)){
            book.setName(bookName);
            book.setWriter(bookWriter);
            book.setIntroduce(bookIntroduce);
            book.setReadNumber(readType);
            book.setOwnNumber(buyType);
            book.setRecommendPerson(Util.getUser());
            book.setShowType(0);
            final BmobServer bmobServer= requsetBuilder.build();
            if (hasCover) {
                bmobServer.upFile(CropUtil.makeTempFile(coverBitmap, "book_cover.jpg"), new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        book.setCoverImage((BmobFile) object);
                        bmobServer.addBook(book, new BmobListener() {
                            @Override
                            public void onSuccess(Object object) {
                                Util.toast(AddBookActivity.this, "添加成功 book");
                                RelationShip relationShip = new RelationShip();
                                relationShip.setBuyType(buyType);
                                relationShip.setReadType(readType);
                                relationShip.setOwner(Util.getUser());
                                relationShip.setBook(book);
                                bmobServer.addRelationShip(relationShip, new BmobListener() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        Util.toast(AddBookActivity.this, "添加成功 relationship");
                                        finishWithResult();
                                    }

                                    @Override
                                    public void onError(BmobException e) {
                                        Util.toast(AddBookActivity.this, "添加失败 relationship");
                                    }
                                });

                            }

                            @Override
                            public void onError(BmobException e) {
                                Util.toast(AddBookActivity.this, "添加失败");
                            }
                        });
                    }

                    @Override
                    public void onError(BmobException e) {
                        Util.toast(AddBookActivity.this, "添加失败");
                    }
                });
            } else {//没有封面，直接上传book，并插入relationship
                bmobServer.addBook(book, new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        Util.toast(AddBookActivity.this,"添加成功 book");
                        RelationShip relationShip = new RelationShip();
                        relationShip.setBuyType(buyType);
                        relationShip.setReadType(readType);
                        relationShip.setOwner(Util.getUser());
                        relationShip.setBook(book);
                        bmobServer.addRelationShip(relationShip,new BmobListener(){
                            @Override
                            public void onSuccess(Object object) {
                                Util.toast(AddBookActivity.this,"添加成功 relationship");
                                finishWithResult();
                            }

                            @Override
                            public void onError(BmobException e) {
                                Util.toast(AddBookActivity.this,"添加失败 relationship");
                            }
                        });

                    }

                    @Override
                    public void onError(BmobException e) {
                        Util.toast(AddBookActivity.this,"添加失败");
                    }
                });
            }
        }
    }

    /**
     *
     * @param params
     * @return  null:false
     *           not null :true
     */
    public boolean checkNotNull(String ... params) {
        Util.L("btn click");
        String strs[] = params.clone();
        for (String s:strs) {
            if (s==null||s.equals("")) {
                return false;
            }
        }
        return true;

    }


    public void updateRelationShip() {
        RelationShip relationShip = new RelationShip();
        relationShip.setObjectId(relationShipId);
        relationShip.setBuyType(buyType);
        relationShip.setReadType(readType);
        requsetBuilder.build()
                .updateRelationShip(relationShip, new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        //更新成功后 同时修改bookbean 的数量统计
                        int r=readType-currentReadType;
                        int b=buyType-currentBuyType;
                        book.setReadNumber(book.getReadNumber()+r);
                        book.setOwnNumber(book.getOwnNumber()+b);
                        requsetBuilder.build()
                                .updateBook(book, new BmobListener() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        Util.toast(AddBookActivity.this,"更新成功");
                                        finishWithResult();
                                    }

                                    @Override
                                    public void onError(BmobException e) {
//                                        Util.toast(AddBookActivity.this,"更新成功");
                                    }
                                });

                    }

                    @Override
                    public void onError(BmobException e) {
                        Util.toast(AddBookActivity.this,"更新失败");
                    }
                });
    }

    public void finishWithResult() {
        Intent ii = new Intent();
        ii.putExtra("ok", true);
        setResult(RESULT_OK, ii);
        finish();
    }
}
