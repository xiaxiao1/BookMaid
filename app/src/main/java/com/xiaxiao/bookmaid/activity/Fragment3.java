package com.xiaxiao.bookmaid.activity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.control.BmobServer;
import com.xiaxiao.bookmaid.control.BookAdapter;
import com.xiaxiao.bookmaid.control.BookNoteAdapter;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;


public class Fragment3 extends BaseFragment {
    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    TextView noDataTip_tv;
    TextView noLoginTip_tv;
    BookAdapter bookAdapter;
    List<BookBean> datas;
    UIDialog waitHttpDialog;

    final String NO_DATA = "暂无数据";
    final String NO_LOGIN = "去登录";

    public Fragment3() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        datas = new ArrayList();
        waitHttpDialog = new UIDialog(getActivity());
        View view= inflater.inflate(R.layout.fragment_fragment3, container, false);
        initViews(view);
        waitHttpDialog.showDialog();
        getInfos();
        return view;
    }




    public void initViews(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        listView = (ListView) view.findViewById(R.id.listview);
        noDataTip_tv = (TextView) view.findViewById(R.id.no_data_tip_tv);
        noLoginTip_tv = (TextView) view.findViewById(R.id.no_login_tip_tv);
        noLoginTip_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.goLoginPage(getActivity(),LoginActivity.class);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInfos();
            }
        });
    }

    public void getInfos() {
        if (!Util.isLogin()) {
            waitHttpDialog.dismissDialog();
            swipeRefreshLayout.setRefreshing(false);
            noDataTip(true);
            noLoginTip(true);
            Util.toast(getActivity(),"请先登录");
            Util.goLoginPage(getActivity(),LoginActivity.class);
            return;
        }
        noLoginTip(false);
        getBuilder()
                .build()
                .getMyBooks(new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        if (bookAdapter == null) {
                            datas = (List<BookBean>) object;
                            bookAdapter = new BookAdapter(getActivity(), datas);
                            listView.setAdapter(bookAdapter);
                        } else {
                            bookAdapter.notifyDataSetChanged();
                        }

                        waitHttpDialog.dismissDialog();
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        if (datas.size() == 0) {
                            noDataTip(true);
//                            noLoginTip(true);
                        } else {
                            noDataTip(false);
                        }
                    }

                    @Override
                    public void onError(BmobException e) {
                        waitHttpDialog.dismissDialog();
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                        noDataTip(true);
                    }
                });

    }

    public void noDataTip(boolean show) {
        if (show) {
            noDataTip_tv.setVisibility(View.VISIBLE);
        } else {
            noDataTip_tv.setVisibility(View.GONE);
        }
    }

    public void noLoginTip(boolean show) {
        if (show) {
            noLoginTip_tv.setVisibility(View.VISIBLE);
        } else {
            noLoginTip_tv.setVisibility(View.GONE);
        }
    }

}
