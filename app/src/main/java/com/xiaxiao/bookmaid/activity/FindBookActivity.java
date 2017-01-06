package com.xiaxiao.bookmaid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.control.BookAdapter;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.GlobalData;
import com.xiaxiao.bookmaid.util.Util;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;

public class FindBookActivity extends BaseActivity {

    private ImageView sousuoBackImg;
    private EditText bookname_et;
    private TextView searchImg;
    private TextView titlename_tv;
    private ListView listview;
    private LinearLayout noDataLl;
    private TextView noDataGoAddTv;
    String bookName;
    BookAdapter bookAdapter;
    List<BookBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_book);
        initViews();
        datas = new ArrayList<>();
        if (GlobalData.findPageType == 0) {
            titlename_tv.setText("搜索");
            searchImg.setText("搜索");
        } else {
            titlename_tv.setText("添加");
            searchImg.setText("确定");
        }

    }


    public void initViews() {
        sousuoBackImg = (ImageView) findViewById(R.id.sousuo_back_img);
        bookname_et=(EditText) findViewById(R.id.edit_et);
        searchImg = (TextView) findViewById(R.id.search_img);
        titlename_tv = (TextView) findViewById(R.id.title_name_tv);
        listview = (ListView) findViewById(R.id.listview);
        noDataLl = (LinearLayout) findViewById(R.id.no_data_ll);
        noDataGoAddTv = (TextView) findViewById(R.id.no_data_go_add_tv);

        sousuoBackImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FindBookActivity.this.finish();
                Util.hideSoftInput(FindBookActivity.this,bookname_et);
            }
        });
        noDataGoAddTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.goAddBookPage(FindBookActivity.this, true, 0);
                FindBookActivity.this.finish();
            }
        });

        searchImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookName = bookname_et.getText().toString().trim();
                if (bookName==null||bookName.equals("")) {
                    return;
                }
                getInfos();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Util.goBookInfoPage(FindBookActivity.this,datas.get(position));
            }
        });

    }


    public void getInfos() {
        noDataLl.setVisibility(View.GONE);
        datas.clear();
        BmobQuery<BookBean> query = new BmobQuery<>();
        query.include("recommendPerson");
        requsetBuilder
                .addBmobQuery(query)
                .addBmobListener(new BmobListener() {
            @Override
            public void onSuccess(Object object) {
                List<BookBean> list = (List<BookBean>) object;
                for (BookBean bb:list) {
                    if (bb.getName().contains(bookName)) {
                        datas.add(bb);
                    }
                }
                if (datas.size() > 0) {
                    if (bookAdapter == null) {
                        bookAdapter = new BookAdapter(FindBookActivity.this, datas);
                        listview.setAdapter(bookAdapter);
                    } else {
                        bookAdapter.updateDatas(datas);
                        bookAdapter.notifyDataSetChanged();
                    }
                } else {
                    noDataLl.setVisibility(View.VISIBLE);
                }

                Util.hideSoftInput(FindBookActivity.this,bookname_et);
            }

            @Override
            public void onError(BmobException e) {
                noDataLl.setVisibility(View.VISIBLE);
                Util.hideSoftInput(FindBookActivity.this,bookname_et);
            }
        })
                .build()
                .getBooks();
        /*
        模糊查询 只有付费用户可以用
        BmobQuery<BookBean> query = new BmobQuery<>();
        query.addWhereContains("name", bookName);
        requsetBuilder.addBmobQuery(query)
                .addBmobListener(new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        List<BookBean> list = (List<BookBean>) object;
                        if (list != null && list.size() > 0) {
                            if (bookAdapter == null) {
                                bookAdapter = new BookAdapter(FindBookActivity.this, list);
                                listview.setAdapter(bookAdapter);
                            } else {
                                bookAdapter.updateDatas(list);
                                bookAdapter.notifyDataSetChanged();
                            }
                        } else {
                            noDataLl.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onError(BmobException e) {

                    }
                })
                .build();
//                .getBooks();*/
    }


}
