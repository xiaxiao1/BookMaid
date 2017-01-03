package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.bean.RelationShip;
import com.xiaxiao.bookmaid.control.BookAdapter;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.GlobalData;
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
    List<RelationShip> relationShips;
    UIDialog uiDialog;

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
        relationShips = new ArrayList();
        uiDialog = new UIDialog(getActivity());
        View view= inflater.inflate(R.layout.fragment_fragment3, container, false);
        initViews(view);
        setListViewListener();
        uiDialog.showWaitDialog();
        getInfos();
        return view;
    }




    public void initViews(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeColors(swipeSchemeColors);
        listView = (ListView) view.findViewById(R.id.listview);
        noDataTip_tv = (TextView) view.findViewById(R.id.no_data_tip_tv);
        noLoginTip_tv = (TextView) view.findViewById(R.id.no_login_tip_tv);
        noLoginTip_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.goLoginPage(getActivity());
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
            uiDialog.dismissWaitDialog();
            swipeRefreshLayout.setRefreshing(false);
            noDataTip(true);
            noLoginTip(true);
            Util.toast(getActivity(),"请先登录");
            Util.goLoginPage(getActivity());
            return;
        }
        noLoginTip(false);
        getBuilder()
                .build()
                .getMyBooks(new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        relationShips = (List<RelationShip>) object;
                        for (RelationShip r : relationShips) {
                            datas.add(r.getBook());
                        }
                        if (bookAdapter == null) {
                            bookAdapter = new BookAdapter(getActivity(), datas);
                            listView.setAdapter(bookAdapter);
                        } else {
                            bookAdapter.updateDatas(datas);
                            bookAdapter.notifyDataSetChanged();
                        }

                        uiDialog.dismissWaitDialog();
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
                        uiDialog.dismissWaitDialog();
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

    public void setListViewListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Util.goBookInfoPage(getActivity(),datas.get(position));
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                uiDialog.showChooseTypeDialog("编辑", "删除", new UIDialog.CustomDialogListener() {
                    @Override
                    public void onItemClick(int index) {
                        if (index==0) {
                            Intent intent = new Intent(getActivity(),AddBookActivity.class);
                            intent.putExtra("changeRelationShip", true);
                            intent.putExtra("relationShipId", relationShips.get(position).getObjectId());
                            intent.putExtra("buyType", relationShips.get(position).getBuyType());
                            intent.putExtra("readType", relationShips.get(position).getReadType());
                            GlobalData.book = datas.get(position);
                            startActivity(intent);
                        }
                        if (index==1) {

                        }
                    }
                });
                return true;
            }
        });
    }

}
