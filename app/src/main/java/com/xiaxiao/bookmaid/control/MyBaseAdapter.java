package com.xiaxiao.bookmaid.control;

import android.content.Context;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by xiaxi on 2016/11/2.
 */
public abstract  class MyBaseAdapter<T> extends BaseAdapter{
    public List<T> list;
    public Context context;

    public MyBaseAdapter(Context context, List<T> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        if (list==null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateDatas(List<T> datas) {
        this.list=datas;
    }




}
