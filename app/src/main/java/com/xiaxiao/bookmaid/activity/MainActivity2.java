package com.xiaxiao.bookmaid.activity;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.Book;
import com.xiaxiao.bookmaid.control.BookAdapter;
import com.xiaxiao.bookmaid.control.BookManager;
import com.xiaxiao.bookmaid.listener.OnResultListener;
import com.xiaxiao.bookmaid.util.BmobIniter;
import com.xiaxiao.bookmaid.util.GlobalData;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;
import com.xiaxiao.bookmaid.widget.BottomView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class MainActivity2 extends BaseActivity  implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener{

    ImageView searchBook_img;
    ImageView addBook_img;
    EditText edit_et;
    ListView listview;
    ImageView all_img;
    ImageView have_img;
    ImageView buy_img;
    ImageView toUser_img;
    TextView allLabel_tv;
    TextView haveLabel_tv;
    TextView buyLabel_tv;
    LinearLayout all_ll;
    LinearLayout have_ll;
    LinearLayout buy_ll;
    SwipeRefreshLayout swipeRefreshLayout;

    TextView change_tv;
    TextView readYes;
    TextView readNo;
    TextView readOn;
    View dialogView;
    ChangeListener changeListener;

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
    String userId;
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    Fragment fragment4;
    Fragment currentPage;
    android.app.FragmentTransaction mFragmentTransaction;
    BottomView bottomView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        userId = getIntent().getStringExtra("userId");
        BmobIniter.init(this);
        uiDialog = new UIDialog(this);
        setContentView(R.layout.activity_main2);
        changeListener = new ChangeListener();

        bottomView = (BottomView) findViewById(R.id.bottom_ll);
        bottomView.setBottomClickListener(new BottomView.BottomClickListener() {
            @Override
            public void onBottomClick(int index) {
                switchFragment(index);
            }
        });
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.swipeLayout, fragment1).commit();
        currentPage = fragment1;
        /*initViews();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bookManager.getBooks(-1, new OnResultListener() {
                    @Override
                    public void onResult(Object object) {
                        swipeRefreshLayout.setRefreshing(false);
                        allBooks=(List<Book>)object;
                        currentList=allBooks;
                        bookAdapter = new BookAdapter(MainActivity2.this, currentList, 0);
                        listview.setAdapter(bookAdapter);

                        currentType=-1;
                        changeViewColorAndImg(all_img,R.drawable.all_on,allLabel_tv,Color.parseColor(lightColor));
                        changeViewColorAndImg(have_img,R.drawable.have_off2,haveLabel_tv,Color.parseColor(darkColor));
                        changeViewColorAndImg(buy_img,R.drawable.buy_off2,buyLabel_tv,Color.parseColor(darkColor));
                    }
                    @Override
                    public void onSuccess(String objectId) {

                    }

                    @Override
                    public void onError(BmobException e) {
                       swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        a=new AlertDialog.Builder(this).setView(dialogView).create();
        tempBooks = new ArrayList<>();
        havedBooks = new ArrayList<>();
        willbuyBooks = new ArrayList<>();
        bookManager = new BookManager(this);
        uiDialog.showDialog();
        bookManager.getBooks(-1, new OnResultListener() {
            @Override
            public void onResult(Object object) {
                allBooks=(List<Book>)object;
                currentList=allBooks;
                bookAdapter = new BookAdapter(MainActivity2.this, currentList, 0);
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
                bookAdapter = new BookAdapter(MainActivity2.this, currentList, 0);
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
               *//* Animation a = AnimationUtils.loadAnimation(MainActivity.this, R.anim.doudong);
                a.setRepeatMode(Animation.RESTART);
                a.setRepeatCount(3);
                view.startAnimation(a);*//*
                if (position>=currentList.size()) {
                    return;
                }
                goBookInfo(currentList.get(position));
            }
        });
*/
    }

    //是否是点击的当前的item,这个判断已经在bottomview中判断了，
    // 弹出index了，说明一定是点击了别的item
    public void switchFragment(int index) {
        mFragmentTransaction = getFragmentManager().beginTransaction();
        if (index==0) {
            if (fragment1 == null) {
                fragment1 = new Fragment1();
            }
            if(!fragment1.isAdded()){
                mFragmentTransaction.hide(currentPage).add(R.id.swipeLayout, fragment1).commit();
            } else {
                mFragmentTransaction.hide(currentPage).show(fragment1).commit();
            }
            currentPage = fragment1;
        }
        if (index==1) {
            if (fragment2 == null) {
                fragment2 = new Fragment2();
            }
            if(!fragment2.isAdded()){
                mFragmentTransaction.hide(currentPage).add(R.id.swipeLayout, fragment2).commit();
            } else {
                mFragmentTransaction.hide(currentPage).show(fragment2).commit();
            }
            currentPage = fragment2;
        }
        if (index==2) {
            if (fragment3 == null) {
                fragment3 = new Fragment3();
            }
            if(!fragment3.isAdded()){
                mFragmentTransaction.hide(currentPage).add(R.id.swipeLayout, fragment3).commit();
            } else {
                mFragmentTransaction.hide(currentPage).show(fragment3).commit();
            }
            currentPage = fragment3;
        }
        if (index==3) {
            if (fragment4 == null) {
                fragment4 = new Fragment4();
            }
            if(!fragment4.isAdded()){
                mFragmentTransaction.hide(currentPage).add(R.id.swipeLayout, fragment4).commit();
            } else {
                mFragmentTransaction.hide(currentPage).show(fragment4).commit();
            }
            currentPage = fragment4;
        }
//        mFragmentTransaction.commit();
    }

    public void switchFragment(Fragment fragment) {
        mFragmentTransaction = getFragmentManager().beginTransaction();
        if (fragment.isAdded()) {
            mFragmentTransaction.hide(currentPage).show(fragment);
        } else {
            mFragmentTransaction.hide(currentPage).add(R.id.swipeLayout, fragment).commit();
        }
        currentPage = fragment;
    }

    public void goBookInfo(Book book) {
        GlobalData.book=book;
        Intent i=new Intent(this,BookInfoActivity.class);
        startActivity(i);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void initViews() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeColors(new int[]{R.color.lan,R.color.hong});
        searchBook_img = (ImageView) findViewById(R.id.search_img);
        toUser_img = (ImageView) findViewById(R.id.user_img);
        addBook_img = (ImageView) findViewById(R.id.add_img);
        edit_et = (EditText) findViewById(R.id.edit_et);
        listview = (ListView) findViewById(R.id.listview);

        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_view, null);
        change_tv = (TextView)dialogView.findViewById(R.id.change_tv);
        readYes = (TextView)dialogView.findViewById(R.id.read_yes);
        readNo = (TextView)dialogView.findViewById(R.id.read_no);
        readOn = (TextView)dialogView.findViewById(R.id.read_on);
        change_tv.setOnClickListener(changeListener);
        readYes.setOnClickListener(changeListener);
        readNo.setOnClickListener(changeListener);
        readOn.setOnClickListener(changeListener);


        View footer = View.inflate(this, R.layout.footer, null);
        //是为了屏蔽listview的点击事件
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        listview.addFooterView(footer);
        searchBook_img.setOnClickListener(this);
        addBook_img.setOnClickListener(this);
        all_ll.setOnClickListener(this);
        have_ll.setOnClickListener(this);
        buy_ll.setOnClickListener(this);
        toUser_img.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int id=v.getId();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            Bundle b=data.getExtras();
            Book book = new Book(b.getString("name"),b.getString("id"),b.getInt("type"),b.getLong("addtime"),b.getInt("readstatus"));
            Util.L(book.toString());
           /* if ((book.getType()==1&&currentType!=0)||) {
            }*/
            if (currentList==null) {
                currentList = new ArrayList<>();
            }
            currentList.add(0,book);
            bookAdapter.notifyDataSetChanged();
            listview.smoothScrollToPosition(0);


        }
    }

    public void changeViewColorAndImg(ImageView img,int sourceId,TextView view,int color) {
        img.setImageResource(sourceId);
        view.setTextColor(color);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ChangeListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            uiDialog.showDialog();
            a.dismiss();
            int read=currentBook.getReadStatus();
            if (v==change_tv) {
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

                        uiDialog.dismissDialog();
                    }

                    @Override
                    public void onError(BmobException e) {
                        uiDialog.dismissDialog();
                        Util.toast(MainActivity2.this,"修改失败");
                    }
                });
                return;
            }
            if (v==readYes) {
                if (read==1) {
                    return;
                }
                currentBook.setReadStatus(1);
            }
            if (v==readOn) {
                if (read==2) {
                    return;
                }
                currentBook.setReadStatus(2);
            }
            if (v==readNo) {
                if (read==0) {
                    return;
                }
                currentBook.setReadStatus(0);
            }
            final Book bv=new Book(currentBook);
            bookManager.update(bv, new OnResultListener() {
                @Override
                public void onSuccess(String objectId) {

                    bookAdapter.notifyDataSetChanged();

                    uiDialog.dismissDialog();
                }

                @Override
                public void onError(BmobException e) {
                    uiDialog.dismissDialog();
                    Util.toast(MainActivity2.this,"修改失败");
                }
            });

        }
    }
}