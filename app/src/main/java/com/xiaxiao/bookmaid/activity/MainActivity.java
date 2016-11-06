package com.xiaxiao.bookmaid.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaxiao.bookmaid.util.BmobIniter;
import com.xiaxiao.bookmaid.control.BookAdapter;
import com.xiaxiao.bookmaid.control.BookManager;
import com.xiaxiao.bookmaid.listener.OnResultListener;
import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.util.GlobalData;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;
import com.xiaxiao.bookmaid.bean.Book;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    ImageView searchBook_img;
    ImageView addBook_img;
    EditText edit_et;
    ListView listview;
    ImageView all_img;
    ImageView have_img;
    ImageView buy_img;
    TextView allLabel_tv;
    TextView haveLabel_tv;
    TextView buyLabel_tv;
    LinearLayout all_ll;
    LinearLayout have_ll;
    LinearLayout buy_ll;

    TextView change_tv;
    View dialogView;

    BookManager bookManager;
    BookAdapter bookAdapter;
    Book currentBook;
    List<Book> allBooks;
    List<Book> tempBooks;
    List<Book> havedBooks;
    List<Book> willbuyBooks;
    List<Book> currentList;
    int currentType=-1;
    UIDialog uiDialog;
    AlertDialog a;
    String lightColor = "#ff4081";
    String darkColor = "#8a8a8a";

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobIniter.init(this);
        setContentView(R.layout.activity_main);
        initViews();
        a=new AlertDialog.Builder(this).setView(dialogView,0,0,0,0).create();
        tempBooks = new ArrayList<>();
        havedBooks = new ArrayList<>();
        willbuyBooks = new ArrayList<>();
        uiDialog = new UIDialog(this);
        bookManager = new BookManager(this);

        uiDialog.showDialog();
        bookManager.getBooks(-1, new OnResultListener() {
            @Override
            public void onResult(Object object) {
                allBooks=(List<Book>)object;
                currentList=allBooks;
                bookAdapter = new BookAdapter(MainActivity.this, currentList, 0);
                listview.setAdapter(bookAdapter);
                uiDialog.dismissDialog();
            }
            @Override
            public void onSuccess(String objectId) {

            }

            @Override
            public void onError(BmobException e) {
                allBooks = bookManager.getBooksInLocal(-1);
                currentList=allBooks;
                bookAdapter = new BookAdapter(MainActivity.this, currentList, 0);
                listview.setAdapter(bookAdapter);
                uiDialog.dismissDialog();
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position>=currentList.size()) {
                    return false;
                }
                currentBook = currentList.get(position);
                if (currentBook.getType() == 1) {
                    change_tv.setText("改为未买");
                } else {
                    change_tv.setText("改为已有");
                }

               a.show();

                return true;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* Animation a = AnimationUtils.loadAnimation(MainActivity.this, R.anim.doudong);
                a.setRepeatMode(Animation.RESTART);
                a.setRepeatCount(3);
                view.startAnimation(a);*/
                if (position>=currentList.size()) {
                    return;
                }
                goBookInfo(currentList.get(position));
            }
        });

    }

    public void goBookInfo(Book book) {
        GlobalData.book=book;
        Intent i=new Intent(this,BookInfoActivity.class);
        startActivity(i);
    }

    public void initViews() {
        searchBook_img = (ImageView) findViewById(R.id.search_img);
        addBook_img = (ImageView) findViewById(R.id.add_img);
        edit_et = (EditText) findViewById(R.id.edit_et);
        listview = (ListView) findViewById(R.id.listview);
        all_img = (ImageView) findViewById(R.id.all_img);
        have_img = (ImageView) findViewById(R.id.have_img);
        buy_img = (ImageView) findViewById(R.id.buy_img);
        allLabel_tv = (TextView) findViewById(R.id.all_label_tv);
        haveLabel_tv = (TextView) findViewById(R.id.have_label_tv);
        buyLabel_tv = (TextView) findViewById(R.id.buy_label_tv);
        all_ll = (LinearLayout) findViewById(R.id.all_ll);
        have_ll = (LinearLayout) findViewById(R.id.have_ll);
        buy_ll = (LinearLayout) findViewById(R.id.buy_ll);

        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_view, null);
        change_tv = (TextView)dialogView.findViewById(R.id.change_tv);

        View footer = View.inflate(this, R.layout.footer, null);
        listview.addFooterView(footer);
        searchBook_img.setOnClickListener(this);
        addBook_img.setOnClickListener(this);
        all_ll.setOnClickListener(this);
        have_ll.setOnClickListener(this);
        buy_ll.setOnClickListener(this);

        dialogView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==dialogView) {
            if (currentBook.getType() == 1) {
                currentBook.setType(0);
            } else {
                currentBook.setType(1);
            }
            final Book bv=new Book(currentBook);
            bookManager.update(bv, new OnResultListener() {
                @Override
                public void onSuccess(String objectId) {
                    if (currentType==1||currentType==0) {
                        currentList.remove(currentBook);
                    }
                    bookAdapter.notifyDataSetChanged();

                    a.dismiss();
                }

                @Override
                public void onError(BmobException e) {
                    a.dismiss();
                    Util.toast(MainActivity.this,"修改失败");
                }
            });
            return;
        }
        int id=v.getId();
        switch (id) {
            case R.id.search_img:

                String edittedName = edit_et.getText().toString().trim();
                if (edittedName.equals("")) {
                    return;
                }
                currentType=2;
                changeViewColorAndImg(all_img,R.drawable.all_off,allLabel_tv,Color.parseColor(darkColor));
                changeViewColorAndImg(have_img,R.drawable.have_off2,haveLabel_tv,Color.parseColor(darkColor));
                changeViewColorAndImg(buy_img,R.drawable.buy_off2,buyLabel_tv,Color.parseColor(darkColor));

                tempBooks=bookManager.query(edittedName);
                currentList=tempBooks;
                bookAdapter.updateDatas(tempBooks);
                bookAdapter.notifyDataSetChanged();
                break;
            case R.id.add_img:
                Intent intent=new Intent(this,AddBookActivity.class);
                startActivityForResult(intent,101);
                break;
            case R.id.have_ll:
                currentType=1;
                changeViewColorAndImg(have_img,R.drawable.have_on2,haveLabel_tv,Color.parseColor(lightColor));
                changeViewColorAndImg(all_img,R.drawable.all_off,allLabel_tv,Color.parseColor(darkColor));
                changeViewColorAndImg(buy_img,R.drawable.buy_off2,buyLabel_tv,Color.parseColor(darkColor));


                havedBooks=bookManager.getBooksInLocal(1);
                currentList=havedBooks;
                bookAdapter.updateDatas(havedBooks);
                bookAdapter.notifyDataSetChanged();
                break;
            case R.id.all_ll:
                currentType=-1;
                changeViewColorAndImg(all_img,R.drawable.all_on,allLabel_tv,Color.parseColor(lightColor));
                changeViewColorAndImg(have_img,R.drawable.have_off2,haveLabel_tv,Color.parseColor(darkColor));
                changeViewColorAndImg(buy_img,R.drawable.buy_off2,buyLabel_tv,Color.parseColor(darkColor));
                allBooks=bookManager.getBooksInLocal(-1);
                currentList=allBooks;
                bookAdapter.updateDatas(allBooks);
                bookAdapter.notifyDataSetChanged();
                break;
            case R.id.buy_ll:
                currentType=0;
                changeViewColorAndImg(buy_img,R.drawable.buy_on2,buyLabel_tv,Color.parseColor(lightColor));
                changeViewColorAndImg(have_img,R.drawable.have_off2,haveLabel_tv,Color.parseColor(darkColor));
                changeViewColorAndImg(all_img,R.drawable.all_off,allLabel_tv,Color.parseColor(darkColor));
                willbuyBooks=bookManager.getBooksInLocal(0);
                currentList=willbuyBooks;
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
           /* if ((book.getType()==1&&currentType!=0)||) {
            }*/
            currentList.add(0,book);
            bookAdapter.notifyDataSetChanged();
            listview.smoothScrollToPosition(0);


        }
    }

    public void changeViewColorAndImg(ImageView img,int sourceId,TextView view,int color) {
        img.setImageResource(sourceId);
        view.setTextColor(color);

    }
}
