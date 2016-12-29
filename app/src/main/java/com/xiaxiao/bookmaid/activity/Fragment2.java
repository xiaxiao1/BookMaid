package com.xiaxiao.bookmaid.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.control.IdeaAdapter;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;


public class Fragment2 extends BaseFragment {
    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    IdeaAdapter ideaAdapter;
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Util.goBookInfoPage(getActivity(), datas.get(position).getBook());
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
                        if (ideaAdapter == null) {
                            datas = (List<BookNote>) object;
                            ideaAdapter = new IdeaAdapter(getActivity(), datas, 0);
                            listView.setAdapter(ideaAdapter);
                        } else {
                            ideaAdapter.updateDatas(datas);
                            ideaAdapter.notifyDataSetChanged();
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
