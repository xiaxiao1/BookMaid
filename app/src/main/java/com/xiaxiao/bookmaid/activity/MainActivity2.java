package com.xiaxiao.bookmaid.activity;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.listener.OnFragmentResultListener;
import com.xiaxiao.bookmaid.util.BmobIniter;
import com.xiaxiao.bookmaid.widget.BottomView;

public class MainActivity2 extends BaseActivity  implements View.OnClickListener,BaseFragment.OnFragmentInteractionListener{

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
        BmobIniter.init(this);
        setContentView(R.layout.activity_main2);
        initViews();
        getRuntimePermissionManager(this);
        runtimePermissionsManager.requestPermissions();
        fragment1 = new Fragment1();
        currentPage = fragment1;
        mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.swipeLayout, fragment1).show(fragment1).commit();
//        switchFragment(0);

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


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void initViews() {
        bottomView = (BottomView) findViewById(R.id.bottom_ll);
        bottomView.setBottomClickListener(new BottomView.BottomClickListener() {
            @Override
            public void onBottomClick(int index) {
                switchFragment(index);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (currentPage instanceof OnFragmentResultListener) {
            OnFragmentResultListener onFragmentResultListener = (OnFragmentResultListener) currentPage;
            onFragmentResultListener.OnFragmentResult(requestCode,resultCode,data);
        }
        /*if (resultCode==RESULT_OK) {
            Bundle b=data.getExtras();
            Book book = new Book(b.getString("name"),b.getString("id"),b.getInt("type"),b.getLong("addtime"),b.getInt("readstatus"));
            Util.L(book.toString());
           *//* if ((book.getBuyType()==1&&currentType!=0)||) {
            }*//*
            if (currentList==null) {
                currentList = new ArrayList<>();
            }
            currentList.add(0,book);
            bookAdapter.notifyDataSetChanged();
            listview.smoothScrollToPosition(0);


        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        runtimePermissionsManager.handle(requestCode, permissions, grantResults);
    }

    /**
     * fragment的回调方法
     * @param uri
     */
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
