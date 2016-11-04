package com.xiaxiao.bookmaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bmob.v3.exception.BmobException;

public class AddBookActivity extends AppCompatActivity {

    EditText edit;
    TextView label;
    ImageView have_img;
    Button submit;

    boolean haved=false;
    int type=0;
    String name="";
    BookManager bookManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        initViews();
        bookManager = new BookManager(this);
    }

    public void initViews() {
        edit = (EditText) findViewById(R.id.addbook_name_et);
        label = (TextView) findViewById(R.id.addbook_have_label_tv);
        have_img = (ImageView) findViewById(R.id.addbook_have_img);
        submit = (Button) findViewById(R.id.addbook_submit);

        have_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                haved=!haved;
                if (haved) {
                    have_img.setImageResource(R.drawable.have_on);
                    label.setText("已经有了哦");
                    type=1;
                } else {
                    have_img.setImageResource(R.drawable.have_off);
                    label.setText("还没有这本书呢");
                    type=0;
                }
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
                final Book book = new Book(edit.getText().toString(), type, System.currentTimeMillis());
                bookManager.add(book, new BookManager.OnResultListener() {
                    @Override
                    public void onSuccess(String objectId) {
                        Intent intent=new Intent();
                        Bundle b=new Bundle();
                        b.putInt("type",book.getType());
                        b.putString("name",book.getName());
                        b.putLong("addtime",book.getAddedTime());
                        b.putString("id",book.getId());
                        intent.putExtras(b);
                        AddBookActivity.this.setResult(RESULT_OK,intent);
                        finish();
                    }

                    @Override
                    public void onError(BmobException e) {
                        Util.toast(AddBookActivity.this,"添加失败，请重试");
                    }
                });

            }
        });
    }

}
