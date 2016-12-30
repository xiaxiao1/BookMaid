package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.control.BookManager;
import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.BitmapUtil;
import com.xiaxiao.bookmaid.util.CropUtil;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import java.io.File;
import java.io.IOException;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        initViews();
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
                break;
            case R.id.add_book_read_rl:
                break;
            case R.id.add_book_add_btn:
                break;
        }

    }


    public   void takePhoto() {
        Util.toast(this,"takephoyo");
        Util.takePhoto(this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case BitmapUtil.PHOTO_PICKED_WITH_DATA:
                Util.toast(this,"从相册里选");
                Uri photo_uri = data.getData();
                try {
                    final Bitmap bitmap = Bitmap.createScaledBitmap(BitmapUtil.getThumbnail(photo_uri, this),
                            400, 400, true);

                    final File tempFile = CropUtil.makeTempFile(bitmap, "temp_file.jpg");
                    if (tempFile==null) {
                        return;
                    }
                    bookCover_img.setImageBitmap(bitmap);
                    /*requsetBuilder.build()
                            .updateUserheadImage(tempFile, Util.getUser(), new BmobListener() {
                                @Override
                                public void onSuccess(Object object) {
                                    userHead_cimg.setImageBitmap(bitmap);
                                    tempFile.delete();
                                    Util.toast(getActivity(),"上传头像成功");
                                }

                                @Override
                                public void onError(BmobException e) {
                                    Util.toast(getActivity(),"上传头像失败");
                                    Util.L("上传头像失败"+e.getErrorCode()+e.getMessage());
                                }
                            });*/
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case BitmapUtil.CAMERA_WITH_DATA:
                Util.toast(this,"拍照的");
                final File file = new File(BitmapUtil.HEAD_IMAGE_PATH + "temp.jpg");
                try {
                    final Bitmap bitmap = Bitmap.createScaledBitmap(BitmapUtil.getThumbnail(file, this), 400,
                            400, true);
                    final File tempFile = CropUtil.makeTempFile(bitmap, "temp_file.jpg");
                    bookCover_img.setImageBitmap(bitmap);
                    /*requsetBuilder.build()
                            .updateUserheadImage(tempFile, Util.getUser(), new BmobListener() {
                                @Override
                                public void onSuccess(Object object) {
                                    userHead_cimg.setImageBitmap(bitmap);
                                    tempFile.delete();
                                    file.delete();
                                    Util.toast(getActivity(),"上传头像成功");
                                }

                                @Override
                                public void onError(BmobException e) {
                                    Util.toast(getActivity(),"上传头像失败");
                                }
                            });*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
