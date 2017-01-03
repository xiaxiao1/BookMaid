package com.xiaxiao.bookmaid.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.control.BookAdapter;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;


public class Fragment1 extends BaseFragment {
//    ImageView searchBook_img;
//    ImageView addBook_img;
//    EditText edit_et;
    public static final int LIST_TYPE_ALL_BOOKS=1;
    public static final int LIST_TYPE_PERSON_BOOKS=0;
    ListView listview;

    SwipeRefreshLayout swipeRefreshLayout;
    ImageView addBook_img;

    BookAdapter bookAdapter;
    List<BookBean> allBooks;
    UIDialog waitHttpDialog;

    public Fragment1() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    /**
     * 跳转到图书详情页
     * @param book
     */
    public void goBookInfo(BookBean book) {
        Util.goBookInfoPage(getActivity(),book);
       /* GlobalData.book=book;
        Intent i=new Intent(getActivity(),BookInfoActivity.class);
        startActivity(i);*/
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void initViews(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeColors(swipeSchemeColors);
        addBook_img = (ImageView) view.findViewById(R.id.f1_add_book_img);
        addBook_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.goFindBookPage(getActivity());
            }
        });
        listview = (ListView)view.findViewById(R.id.listview);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInfos();
            }
        });
        View footer = View.inflate(getActivity(), R.layout.footer, null);
        //是为了屏蔽listview的点击事件
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        listview.addFooterView(footer);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position>=allBooks.size()) {
                    return;
                }
                goBookInfo(allBooks.get(position));
            }
        });

    }

    public void getInfos() {

        getBuilder()
                .enableDialog(false)
                .build()
                .getBooksWithDefaultOptions(new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        waitHttpDialog.dismissWaitDialog();
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        allBooks = (List<BookBean>) object;
                        if (bookAdapter == null) {
                            bookAdapter = new BookAdapter(getActivity(), allBooks);
                            listview.setAdapter(bookAdapter);

                        } else {
                            bookAdapter.updateDatas(allBooks);
                            bookAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(BmobException e) {
                        waitHttpDialog.dismissWaitDialog();
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }
                });
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fragment1, container, false);
        initViews(view);
        allBooks = new ArrayList<>();
        waitHttpDialog = new UIDialog(getActivity());
        waitHttpDialog.showWaitDialog();
        getInfos();

        return view;
    }


}
