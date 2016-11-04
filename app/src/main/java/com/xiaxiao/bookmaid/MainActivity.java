package com.xiaxiao.bookmaid;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
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
    List<Book> tempBooks;
    List<Book> havedBooks;
    List<Book> willbuyBooks;
    UIDialog uiDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobIniter.init(this);
        setContentView(R.layout.activity_main);
        initViews();
        tempBooks = new ArrayList<>();
        havedBooks = new ArrayList<>();
        willbuyBooks = new ArrayList<>();
        uiDialog = new UIDialog(this);
        uiDialog.showDialog();
        bookManager = new BookManager(this);
        bookManager.getBooks(-1, new OnResultListener() {
            @Override
            public void onResult(Object object) {
                books=(List<Book>)object;
                bookAdapter = new BookAdapter(MainActivity.this, books, 0);
                listview.setAdapter(bookAdapter);
                uiDialog.dismissDialog();
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
                String edittedName = edit_et.getText().toString().trim();
                tempBooks=bookManager.query(edittedName);
                bookAdapter.updateDatas(tempBooks);
                bookAdapter.notifyDataSetChanged();
                break;
            case R.id.add_img:
                Intent intent=new Intent(this,AddBookActivity.class);
                startActivityForResult(intent,101);
                break;
            case R.id.have_btn:
                have_btn.setBackgroundResource(R.color.blue);
                buy_btn.setBackgroundResource(R.color.blue_dark);
                havedBooks=bookManager.getBooksInLocal(1);
                bookAdapter.updateDatas(havedBooks);
                bookAdapter.notifyDataSetChanged();
                break;
            case R.id.buy_btn:
                buy_btn.setBackgroundResource(R.color.blue);
                have_btn.setBackgroundResource(R.color.blue_dark);
                willbuyBooks=bookManager.getBooksInLocal(0);
                bookAdapter.updateDatas(willbuyBooks);
                bookAdapter.notifyDataSetChanged();
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
