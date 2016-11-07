package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.control.BookNoteServer;
import com.xiaxiao.bookmaid.listener.OnResultListener;
import com.xiaxiao.bookmaid.util.GlobalData;
import com.xiaxiao.bookmaid.util.Util;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class AddNoteActivity extends AppCompatActivity {
EditText content;
    Button add_btn;
    BookNoteServer bookNoteServer;
    BookNote bookNote;
    String bookId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        bookNoteServer = new BookNoteServer(this);
        bookId = getIntent().getExtras().getString("bookId");
        content = (EditText) findViewById(R.id.edit);
        add_btn = (Button) findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contentStr = content.getText().toString();
                if (contentStr.equals("")) {
                    return ;
                }
                bookNote = new BookNote(contentStr, "0", bookId, BmobUser.getCurrentUser().getUsername());
                bookNote.setOwnerId(Util.getUserId());
                bookNoteServer.add(bookNote, new OnResultListener() {
                    @Override
                    public void onSuccess(String objectId) {
                        bookNote.setId(objectId);
                        GlobalData.bookNote=bookNote;
                        Intent intent=new Intent();
                        /*Bundle b=new Bundle();
                        b.putString("content",bookNote.getContent());
                        intent.putExtras(b);*/
                        setResult(RESULT_OK,intent);
                        finish();
                        Util.toast(AddNoteActivity.this,"添加成功");
                    }

                    @Override
                    public void onError(BmobException e) {
                        Util.toast(AddNoteActivity.this,"添加失败");
                    }
                });

            }
        });
    }
}
