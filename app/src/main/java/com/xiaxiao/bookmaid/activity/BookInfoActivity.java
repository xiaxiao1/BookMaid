package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.Book;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.control.BookNoteAdapter;
import com.xiaxiao.bookmaid.control.BookNoteServer;
import com.xiaxiao.bookmaid.listener.OnResultListener;
import com.xiaxiao.bookmaid.util.GlobalData;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class BookInfoActivity extends BaseActivity implements View.OnClickListener{

    ImageView bookImg;
    TextView bookName;
    TextView bookWriter;
    TextView bookStatus;
    TextView bookRead;
    TextView addNote_tv;
    ListView listView;

    List<BookNote> notes;
    BookNoteServer bookNoteServer;
    BookNoteAdapter bookNoteAdapter;
    Book b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        initViews();
        b= GlobalData.book;
        notes = new ArrayList<>();
        bookName.setText(b.getName());
        bookWriter.setText(b.getId());
        bookStatus.setText("未买");
        if (b.getType()==1) {
            bookStatus.setText("已有");
        }

        bookRead.setText("未读");
        if (b.getReadStatus()==1) {
            bookRead.setText("已读");
        }
        if (b.getReadStatus()==2) {
            bookRead.setText("在读");
        }

        bookNoteServer = new BookNoteServer(this);
        bookNoteServer.getBookNotes(b.getId(), new OnResultListener() {
            @Override
            public void onResult(Object obj) {
                notes = (List<BookNote>) obj;
                if (bookNoteAdapter==null) {
                    bookNoteAdapter = new BookNoteAdapter(BookInfoActivity.this, notes,0);
                    listView.setAdapter(bookNoteAdapter);
                }
            }
            @Override
            public void onSuccess(String objectId) {

            }

            @Override
            public void onError(BmobException e) {

            }
        });

    }

    public void initViews() {
        bookImg = (ImageView) findViewById(R.id.book_img);
        bookName = (TextView) findViewById(R.id.book_name_tv);
        bookWriter = (TextView) findViewById(R.id.book_writer_tv);
        bookStatus = (TextView) findViewById(R.id.book_status_tv);
        bookRead = (TextView) findViewById(R.id.book_read_tv);
        addNote_tv = (TextView) findViewById(R.id.add_note_tv);
        listView = (ListView) findViewById(R.id.listview);
        addNote_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i=new Intent(this,AddNoteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("bookId",b.getId());
        i.putExtras(bundle);
        startActivityForResult(i,101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            BookNote bookNote2 = GlobalData.bookNote;
            if (bookNoteAdapter==null) {
                bookNoteAdapter = new BookNoteAdapter(this, notes, 0);
            }
            notes.add(0,bookNote2);
            bookNoteAdapter.notifyDataSetChanged();
        }
    }
}
