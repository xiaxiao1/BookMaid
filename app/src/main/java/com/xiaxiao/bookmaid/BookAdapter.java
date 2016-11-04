package com.xiaxiao.bookmaid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class BookAdapter extends BaseAdapter{
    List<Book> list;
    Context context;
    int type;

    public BookAdapter(Context context, List<Book> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
    }
    @Override
    public int getCount() {
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

    public void updateDatas(List<Book> books) {
        this.list=books;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        Book book = (Book) list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.book_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder=(Holder)convertView.getTag();
        }
        holder.bookName.setText(book.getName());


        return convertView;
    }

    class Holder{
        TextView bookName;

        public Holder(View view) {
            this.bookName = (TextView) view.findViewById(R.id.item_book_name);
        }
    }
}
