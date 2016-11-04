package com.xiaxiao.bookmaid;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
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
    int proirIndex=-1;

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
        holder.bookIndex.setText((position+1)+"");
        holder.bookIndex.setTextColor(Color.parseColor(Util.getRandomColor()));
        holder.bookName.setText(book.getName());
        holder.bookId.setText(book.getId());
        if (book.getType() == 1) {
            holder.bookType.setText(R.string.book_type_buy);
            holder.bookType.setTextColor(Color.parseColor("#1296db"));
        } else {
            holder.bookType.setText(R.string.book_type_buy_no);
            holder.bookType.setTextColor(Color.parseColor("#8a8a8a"));

        }

        if (position > proirIndex) {
            convertView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.item_show_fly));
        } else {
            convertView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.item_show_drop));
        }
        proirIndex=position;
        return convertView;
    }

    class Holder{
        TextView bookName;
        TextView bookIndex;
        TextView bookId;
        TextView bookType;

        public Holder(View view) {
            this.bookName = (TextView) view.findViewById(R.id.item_book_name);
            this.bookIndex = (TextView) view.findViewById(R.id.item_book_index_tv);
            this.bookId = (TextView) view.findViewById(R.id.item_book_id);
            this.bookType = (TextView) view.findViewById(R.id.item_book_type);
        }
    }
}
