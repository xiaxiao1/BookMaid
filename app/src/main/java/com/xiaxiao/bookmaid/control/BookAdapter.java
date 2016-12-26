package com.xiaxiao.bookmaid.control;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.util.Util;

import java.util.List;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class BookAdapter extends MyBaseAdapter{

    int proirIndex=-1;

    public BookAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        BookBean book = (BookBean) list.get(position);
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
        holder.bookId.setText(book.getObjectId());
        /*if (book.getType() == 1) {
            holder.bookType.setText(R.string.book_type_buy);
            holder.bookType.setTextColor(Color.parseColor("#1296db"));
        } else {
            holder.bookType.setText(R.string.book_type_buy_no);
            holder.bookType.setTextColor(Color.parseColor("#8a8a8a"));

        }
        if (book.getReadStatus() == 1) {
            holder.bookRead.setText(R.string.book_read_yes);
            holder.bookRead.setTextColor(Color.parseColor("#1296db"));
        } else if (book.getReadStatus() == 0) {
            holder.bookRead.setText(R.string.book_read_no);
            holder.bookRead.setTextColor(Color.parseColor("#8a8a8a"));
        } else {
            holder.bookRead.setText(R.string.book_read_on);
            holder.bookRead.setTextColor(Color.parseColor("#8a8a8a"));
        }*/

       /* if (position > proirIndex) {
            convertView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.item_show_fly));
        } else {
            convertView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.item_show_drop));
        }*/
        proirIndex=position;
        return convertView;
    }

    class Holder{
        TextView bookName;
        TextView bookIndex;
        TextView bookId;
        TextView bookType;
        TextView bookRead;

        public Holder(View view) {
            this.bookName = (TextView) view.findViewById(R.id.item_book_name);
            this.bookIndex = (TextView) view.findViewById(R.id.item_book_index_tv);
            this.bookId = (TextView) view.findViewById(R.id.item_book_id);
            this.bookType = (TextView) view.findViewById(R.id.item_book_type);
            this.bookRead = (TextView) view.findViewById(R.id.item_book_read);
        }
    }
}
