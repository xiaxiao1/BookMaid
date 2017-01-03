package com.xiaxiao.bookmaid.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.control.IdeaAdapter;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;


public class Fragment2 extends BaseFragment {
    public static final int LIST_TYPE_ALL_NOTES=1;
    public static final int LIST_TYPE_PERSON_NOTES=0;

    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout appTitle_rl;
    ListView listView;
    IdeaAdapter ideaAdapter;
    List<BookNote> datas;
    UIDialog waitHttpDialog;

    int noteType;

    public Fragment2() {
        // Required empty public constructor
    }

    public static Fragment2 newInstance(int noteType) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putInt("noteType", noteType);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noteType = getArguments().getInt("noteType");
        }
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
        appTitle_rl = (RelativeLayout) view.findViewById(R.id.app_title);
        if (noteType==LIST_TYPE_PERSON_NOTES) {
            appTitle_rl.setVisibility(View.GONE);
        }
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
        BmobQuery<BookNote> query = null;
        if (noteType==LIST_TYPE_PERSON_NOTES) {
            query = new BmobQuery<>();
            List<BmobQuery<BookNote>> queryList = new ArrayList<>();
            BmobQuery<BookNote> query1 = new BmobQuery<>();
            BmobQuery<BookNote> query2 = new BmobQuery<>();
            query1.addWhereEqualTo("whoWrite", Util.getUser());
            query2.addWhereEqualTo("replyWhos", Util.getUser());
            queryList.add(query1);
            queryList.add(query2);
            query.or(queryList);
        }
        getBuilder()
                .enableDialog(false)
                .addBmobQuery(query)
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
