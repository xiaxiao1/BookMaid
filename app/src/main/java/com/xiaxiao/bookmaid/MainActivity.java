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

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

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
        BmobIniter.init(this);
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
                /*Book b = new Book("name:" + System.currentTimeMillis());
                b.setAddedTime(System.currentTimeMillis());
                b.setType(0);
                boolean result=bookManager.add(b);
                if (result) {
                    Toast.makeText(MainActivity.this, "add ok", Toast.LENGTH_SHORT).show();
                }*/
                break;
            case R.id.have_btn:
                Intent intent=new Intent(this,AddBookActivity.class);
                startActivityForResult(intent,101);
                break;
            case R.id.buy_btn:
                Book book = new Book("intert book", null, 0, System.currentTimeMillis());
                book.getObjectId();
                book.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e==null) {
                            Util.L("add ok! "+s);
                        }
                    }
                });
                break;
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            Bundle b=data.getExtras();
            Book book = new Book(b.getString("name"),b.getString("id"),b.getInt("type"),b.getLong("addtime"));
            Util.L(book.toString());
            books.add(book);
            bookAdapter.notifyDataSetChanged();


        }
    }
}
