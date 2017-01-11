package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.bean.MyUser;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.BitmapUtil;
import com.xiaxiao.bookmaid.util.CropUtil;
import com.xiaxiao.bookmaid.util.GlideHelper;
import com.xiaxiao.bookmaid.util.Util;

import java.io.File;
import java.io.IOException;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import de.hdodenhof.circleimageview.CircleImageView;

public class SendNoteActivity extends BaseActivity {

    private LinearLayout replyWho_ll;
    private CircleImageView towhoHead_cimg;
    private TextView towhoName_tv;
    private EditText addedNote_tv;
    private LinearLayout sendNote_ll;
    private ImageView back_img;

    private RelativeLayout showNoteImg_rl;
    private  ImageView noteImg_img;
    private  ImageView deleteNoteImg_img;
    private  ImageView addNoteImg_img;

    private  String bookId;
    private  String replyWhoId;
    private  String replyWhoName;
    private  String replyWhoHeadImg;
    Bitmap  coverBitmap;
    BmobFile noteFile=null;
    boolean hasCover=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_note);
        initViews();
        bookId = getIntent().getStringExtra("bookId");
        replyWhoId = getIntent().getStringExtra("replyWhoId");
        replyWhoName = getIntent().getStringExtra("replyWhoName");
        replyWhoHeadImg = getIntent().getStringExtra("replyWhoHeadImg");
        if (replyWhoId != null && !replyWhoId.equals("")) {
            replyWho_ll.setVisibility(View.VISIBLE);
            GlideHelper.loadImage(this, replyWhoHeadImg, towhoHead_cimg,R.drawable.app_head_gray);
            towhoName_tv.setText(replyWhoName);
        } else {
            replyWho_ll.setVisibility(View.GONE);
        }



    }

    public void initViews() {
        back_img = (ImageView) findViewById(R.id.sendnote_back_img);
        replyWho_ll = (LinearLayout) findViewById(R.id.sendnote_towho_ll);
        towhoHead_cimg = (CircleImageView) findViewById(R.id.sendnote_towho_head_cimg);
        towhoName_tv = (TextView) findViewById(R.id.sendnote_towho_name_tv);
        addedNote_tv = (EditText) findViewById(R.id.sendnote_addnote_et);
        sendNote_ll = (LinearLayout) findViewById(R.id.send_note_send_ll);

        showNoteImg_rl = (RelativeLayout) findViewById(R.id.show_note_img_rl);
        noteImg_img = (ImageView) findViewById(R.id.note_img);
        deleteNoteImg_img = (ImageView) findViewById(R.id.delete_note_img);
        addNoteImg_img= (ImageView) findViewById(R.id.add_note_img_img);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendNoteActivity.this.finish();
            }
        });
        sendNote_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=addedNote_tv.getText().toString();
                if (str==null||str.equals("")) {
                    return;
                }
                Util.L(str);
                uploadNotePicAndSendNote(str);
            }
        });

        addNoteImg_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        deleteNoteImg_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasCover=false;
                showNoteImg_rl.setVisibility(View.GONE);
                addNoteImg_img.setVisibility(View.VISIBLE);
                coverBitmap.recycle();
                coverBitmap=null;
            }
        });
    }

    public void sendNote(String content) {
        BookNote bookNote = new BookNote();
        bookNote.setContent(content);
        BookBean bookBean = new BookBean();
        bookBean.setObjectId(bookId);
        MyUser writer = new MyUser();
        writer.setObjectId(Util.getUser().getObjectId());
        if (replyWhoId!=null&&!replyWhoId.equals("")) {
            MyUser replyWho = new MyUser();
            replyWho.setObjectId(replyWhoId);
            bookNote.setReplyWhos(replyWho);
        }
        bookNote.setBook(bookBean);
        bookNote.setWhoWrite(writer);
        if (noteFile!=null) {
            bookNote.setNotePic(noteFile);
        }
        requsetBuilder.enableDialog(false)
                .build()
                .addBookNote(bookNote, new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        Util.toast(SendNoteActivity.this,"发送成功");
                        Util.hideSoftInput(SendNoteActivity.this,addedNote_tv);
                        SendNoteActivity.this.setResult(RESULT_OK,null);
                        SendNoteActivity.this.finish();
                    }

                    @Override
                    public void onError(BmobException e) {
                        Util.L("error:"+e.getMessage());
                        Util.toast(SendNoteActivity.this,"发送失败，请重试");
                    }
                });
    }

    public void uploadNotePicAndSendNote(final String str1) {
        if (hasCover) {
            requsetBuilder
                    .build()
                    .upFile(CropUtil.makeTempFile(coverBitmap, "notepic.jpg"), new BmobListener() {
                        @Override
                        public void onSuccess(Object object) {
                            noteFile = (BmobFile) object;
                            sendNote(str1);
                        }

                        @Override
                        public void onError(BmobException e) {

                        }
                    });
        } else {
            sendNote(str1);
        }
    }

    public   void takePhoto() {
        Util.takePhoto(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode!=RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case BitmapUtil.PHOTO_PICKED_WITH_DATA:
//                Util.toast(this,"从相册里选");
                Uri photo_uri = data.getData();
                try {
                   /* Point point = Util.getDisplay(SendNoteActivity.this);
                    coverBitmap = Bitmap.createScaledBitmap(tempb,
                            point.x, (int)(point.x*((float)tempb.getHeight()/tempb.getWidth())), true);*/
                    coverBitmap =BitmapUtil.getThumbnail(photo_uri, this);;

                    if (coverBitmap!=null) {
                        showNoteImg_rl.setVisibility(View.VISIBLE);
                        addNoteImg_img.setVisibility(View.GONE);
                        noteImg_img.setImageBitmap(coverBitmap);
                        hasCover=true;
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case BitmapUtil.CAMERA_WITH_DATA:
//                Util.toast(this,"拍照的");
                final File file = new File(BitmapUtil.HEAD_IMAGE_PATH + "temp.jpg");
                try {
                    Point point = Util.getDisplay(SendNoteActivity.this);
                    /*coverBitmap = Bitmap.createScaledBitmap(BitmapUtil.getThumbnail(file, this), 400,
                            (int)(400f*(point.y/point.x)), true);*/
                    coverBitmap=BitmapUtil.getThumbnail(file, this);
                    if (coverBitmap!=null) {
                        showNoteImg_rl.setVisibility(View.VISIBLE);
                        addNoteImg_img.setVisibility(View.GONE);
                        noteImg_img.setImageBitmap(coverBitmap);
                        hasCover=true;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
