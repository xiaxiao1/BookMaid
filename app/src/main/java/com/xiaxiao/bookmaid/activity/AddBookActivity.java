package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.control.BookManager;
import com.xiaxiao.bookmaid.listener.OnResultListener;
import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class AddBookActivity extends BaseActivity {

    EditText edit;
    TextView label;
    ImageView have_img;
    ImageView back_img;
    ImageView read_img;
    TextView readlabel_tv;
    Button submit;

    boolean haved=false;
    int type=0;
    int readStatus=0;
    String name="";
    BookManager bookManager;
    UIDialog uiDialog;
    View readMenuView;
    AlertDialog chooseReadDialog;
    TextView readYes;
    TextView readNo;
    TextView readOn;
    ReadListener readListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        readListener = new ReadListener();
        bookManager = new BookManager(this);
        initViews();
        uiDialog = new UIDialog(this);
    }

    public void initViews() {
        readMenuView = View.inflate(this, R.layout.read_status_menu, null);
        readYes = (TextView) readMenuView.findViewById(R.id.read_yes);
        readNo = (TextView) readMenuView.findViewById(R.id.read_no);
        readOn = (TextView) readMenuView.findViewById(R.id.read_on);
        readYes.setOnClickListener(readListener);
        readNo.setOnClickListener(readListener);
        readOn.setOnClickListener(readListener);
        chooseReadDialog = new AlertDialog.Builder(this).setView(readMenuView).create();
        read_img = (ImageView) findViewById(R.id.addbook_read_img);
        readlabel_tv = (TextView) findViewById(R.id.addbook_read_label_tv);
        edit = (EditText) findViewById(R.id.addbook_name_et);
        label = (TextView) findViewById(R.id.addbook_have_label_tv);
        have_img = (ImageView) findViewById(R.id.addbook_have_img);
        submit = (Button) findViewById(R.id.addbook_submit);
        back_img = (ImageView) findViewById(R.id.back_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddBookActivity.this.finish();
            }
        });
        have_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                haved=!haved;
                if (haved) {
                    have_img.setImageResource(R.drawable.have_on);
                    label.setText("已有");
                    type=1;
                } else {
                    have_img.setImageResource(R.drawable.have_off);
                    label.setText("未买");
                    type=0;
                }
            }
        });
        read_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseReadDialog.show();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.getText().toString().equals("")) {
                    Util.toast(AddBookActivity.this,"书名还没有写呢");
                    return;
                }
                Util.L(edit.getText().toString()+" 有没有："+type);
                final BookBean book = new BookBean("","","",1,1,"");
//                book.setOwnerId(Util.getUser().getObjectId());
                uiDialog.showDialog();
                /*bookManager.add(book, new OnResultListener() {
                    @Override
                    public void onSuccess(String objectId) {
                        uiDialog.dismissDialog();
                        Intent intent=new Intent();
                        Bundle b=new Bundle();
                        b.putString("name",book.getName());
                        b.putString("id",book.getObjectId());
                        intent.putExtras(b);
                        AddBookActivity.this.setResult(RESULT_OK,intent);
                        finish();
                    }

                    @Override
                    public void onError(BmobException e) {
                        Util.toast(AddBookActivity.this,"添加失败，请重试");
                        uiDialog.dismissDialog();
                    }
                });*/

            }
        });
    }

    class ReadListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Util.L("read listener");
            if (v==readYes) {
                readStatus=1;
                readlabel_tv.setText("已读");

            }
            if (v==readNo) {
                readStatus=0;
                readlabel_tv.setText("未读");
            }
            if (v==readOn) {
                readStatus=2;
                readlabel_tv.setText("在读");
            }
            chooseReadDialog.dismiss();
        }
    }
}
