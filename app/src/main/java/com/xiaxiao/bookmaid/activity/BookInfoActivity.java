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
import com.xiaxiao.bookmaid.util.GlobalData;

import java.util.ArrayList;
import java.util.List;

public class BookInfoActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView bookImg;
    TextView bookName;
    TextView bookWriter;
    TextView bookStatus;
    TextView addNote_tv;
    ListView listView;

    List<BookNote> notes;
    BookNoteAdapter bookNoteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        initViews();
        Book b= GlobalData.book;
        notes = new ArrayList<>();
        bookName.setText(b.getName());
        bookWriter.setText(b.getId());
        bookStatus.setText("未买");
        if (b.getType()==1) {
            bookStatus.setText("已有");
        }

        for (int i=0;i<20;i++) {
            BookNote bookNote = new BookNote("真本书不错哦" + i,"0", "0", "xiaxiao");
            notes.add(bookNote);
        }
        bookNoteAdapter = new BookNoteAdapter(this, notes,0);
        listView.setAdapter(bookNoteAdapter);
    }

    public void initViews() {
        bookImg = (ImageView) findViewById(R.id.book_img);
        bookName = (TextView) findViewById(R.id.book_name_tv);
        bookWriter = (TextView) findViewById(R.id.book_writer_tv);
        bookStatus = (TextView) findViewById(R.id.book_status_tv);
        addNote_tv = (TextView) findViewById(R.id.add_note_tv);
        listView = (ListView) findViewById(R.id.listview);
        addNote_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivityForResult(new Intent(this,AddNoteActivity.class),101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        BookNote bookNote2 = new BookNote(data.getExtras().getString("content"), "0", "0", "xiaxiao");
        notes.add(bookNote2);
        bookNoteAdapter.notifyDataSetChanged();
    }
}
