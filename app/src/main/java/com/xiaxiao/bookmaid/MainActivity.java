package com.xiaxiao.bookmaid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    ImageView searchBook_img;
    ImageView addBook_img;
    EditText edit_et;
    ListView listview;
    Button have_btn;
    Button buy_btn;

    BookManager bookManager;
    BookAdapter bookAdapter;
    List<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        bookManager = new BookManager(this);
        books = bookManager.getBooks(0);
        bookAdapter = new BookAdapter(this, books, 0);
        listview.setAdapter(bookAdapter);
    }

    public void initViews() {
        searchBook_img = (ImageView) findViewById(R.id.search_img);
        addBook_img = (ImageView) findViewById(R.id.add_img);
        edit_et = (EditText) findViewById(R.id.edit_et);
        listview = (ListView) findViewById(R.id.listview);
        have_btn = (Button) findViewById(R.id.have_btn);
        buy_btn = (Button) findViewById(R.id.buy_btn);

        searchBook_img.setOnClickListener(this);
        addBook_img.setOnClickListener(this);
        have_btn.setOnClickListener(this);
        buy_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id) {
            case R.id.search_img:
                books=bookManager.getBooks(0);
                for (Book book:books) {
                    book.setName(""+book.getId()+book.getName()+book.getType());
                }
                bookAdapter.updateDatas(books);
                bookAdapter.notifyDataSetChanged();
                break;
            case R.id.add_img:
                Book b = new Book("name:" + System.currentTimeMillis());
                b.setAddedTime(System.currentTimeMillis());
                b.setType(0);
                boolean result=bookManager.add(b);
                if (result) {
                    Toast.makeText(MainActivity.this, "add ok", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.have_btn:
                startActivity(new Intent(this,AddBookActivity.class));
                break;
            case R.id.buy_btn:
                break;
            default:
                break;
        }

    }
}
