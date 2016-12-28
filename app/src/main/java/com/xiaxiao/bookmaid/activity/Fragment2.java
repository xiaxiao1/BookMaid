package com.xiaxiao.bookmaid.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.control.BookNoteAdapter;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.UIDialog;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;


public class Fragment2 extends BaseFragment {
    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    BookNoteAdapter bookNoteAdapter;
    List<BookNote> datas;
    UIDialog waitHttpDialog;

    public Fragment2() {
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
        View view= inflater.inflate(R.layout.fragment_fragment2, container, false);
        initViews(view);
        waitHttpDialog.showWaitDialog();
        getInfos();
        return view;
    }




    public void initViews(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeColors(swipeSchemeColors);
        listView = (ListView) view.findViewById(R.id.listview);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInfos();
            }
        });
    }

    public void getInfos() {
        getBuilder()
                .enableDialog(false)
                .build()
                .getAllIdeas(new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        if (bookNoteAdapter == null) {
                            datas = (List<BookNote>) object;
                            bookNoteAdapter = new BookNoteAdapter(getActivity(), datas, 0);
                            listView.setAdapter(bookNoteAdapter);
                        } else {
                            bookNoteAdapter.notifyDataSetChanged();
                        }

                        waitHttpDialog.dismissWaitDialog();
                        if (swipeRefreshLayout.isRefreshing()) {
                            swipeRefreshLayout.setRefreshing(false);
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

}
