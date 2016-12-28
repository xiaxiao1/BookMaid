package com.xiaxiao.bookmaid.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.util.BmobIniter;
import com.xiaxiao.bookmaid.control.BookAdapter;
import com.xiaxiao.bookmaid.control.BookManager;
import com.xiaxiao.bookmaid.listener.OnResultListener;
import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.util.GlobalData;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;

public class MainActivity extends BaseActivity  implements View.OnClickListener{

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
    BookBean currentBook;
    List<BookBean> allBooks;
    List<BookBean> tempBooks;
    List<BookBean> havedBooks;
    List<BookBean> willbuyBooks;
    List<BookBean> currentList;
    int currentType=-1;
    UIDialog uiDialog;
    AlertDialog a;
    String lightColor = "#ff4081";
    String darkColor = "#8a8a8a";
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        userId = getIntent().getStringExtra("userId");
        BmobIniter.init(this);
        uiDialog = new UIDialog(this);
        setContentView(R.layout.activity_main);
        changeListener = new ChangeListener();
        initViews();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                bookManager.getBooks(-1, new OnResultListener() {
                    @Override
                    public void onResult(Object object) {
                        swipeRefreshLayout.setRefreshing(false);
                        allBooks=(List<BookBean>)object;
                        currentList=allBooks;
                        bookAdapter = new BookAdapter(MainActivity.this, currentList);
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
        uiDialog.showWaitDialog();
        bookManager.getBooks(-1, new OnResultListener() {
            @Override
            public void onResult(Object object) {
                allBooks=(List<BookBean>)object;
                currentList=allBooks;
                bookAdapter = new BookAdapter(MainActivity.this, currentList);
                listview.setAdapter(bookAdapter);
                uiDialog.dismissWaitDialog();

            }
            @Override
            public void onSuccess(String objectId) {

            }

            @Override
            public void onError(BmobException e) {
                allBooks = bookManager.getBooksInLocal(-1);
                currentList=allBooks;
                bookAdapter = new BookAdapter(MainActivity.this, currentList);
                listview.setAdapter(bookAdapter);
                uiDialog.dismissWaitDialog();
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position>=currentList.size()) {
                    return false;
                }
                currentBook = currentList.get(position);
//                if (currentBook.getType() == 1) {
//                    change_tv.setText("改为未买");
//                } else {
//                    change_tv.setText("改为已有");
//                }

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
//                goBookInfo(currentList.get(position));
            }
        });

    }

    public void goBookInfo(BookBean book) {
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
       /* switch (id) {
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
            case R.id.user_img:
                GlobalData.activity=MainActivity.this;
                Intent intent2=new Intent(this,UserActivity.class);
                startActivity(intent2);
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
        }*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            Bundle b=data.getExtras();
            BookBean book = new BookBean();
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

    class ChangeListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            uiDialog.showWaitDialog();
            a.dismiss();
//            int read=currentBook.getReadStatus();
//            if (v==change_tv) {
//                if (currentBook.getType() == 1) {
//                    currentBook.setType(0);
//                } else {
//                    currentBook.setType(1);
//                }
//                final Book bv=new Book(currentBook);
//                bookManager.update(bv, new OnResultListener() {
//                    @Override
//                    public void onSuccess(String objectId) {
//                        if (currentType==1||currentType==0) {
//                            currentList.remove(currentBook);
//                        }
//                        bookAdapter.notifyDataSetChanged();
//
//                        uiDialog.dismissWaitDialog();
//                    }
//
//                    @Override
//                    public void onError(BmobException e) {
//                        uiDialog.dismissWaitDialog();
//                        Util.toast(MainActivity.this,"修改失败");
//                    }
//                });
                return;
            }
//            if (v==readYes) {
//                if (read==1) {
//                    return;
//                }
//                currentBook.setReadStatus(1);
//            }
//            if (v==readOn) {
//                if (read==2) {
//                    return;
//                }
//                currentBook.setReadStatus(2);
//            }
//            if (v==readNo) {
//                if (read==0) {
//                    return;
//                }
//                currentBook.setReadStatus(0);
//            }
//            final Book bv=new Book(currentBook);
//            bookManager.update(bv, new OnResultListener() {
//                @Override
//                public void onSuccess(String objectId) {
//
//                    bookAdapter.notifyDataSetChanged();
//
//                    uiDialog.dismissWaitDialog();
//                }
//
//                @Override
//                public void onError(BmobException e) {
//                    uiDialog.dismissWaitDialog();
//                    Util.toast(MainActivity.this,"修改失败");
//                }
//            });

        }
    }

