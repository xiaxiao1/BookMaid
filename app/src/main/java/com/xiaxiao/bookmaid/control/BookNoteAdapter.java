package com.xiaxiao.bookmaid.control;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.util.Util;

import java.util.List;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class BookNoteAdapter extends BaseAdapter{
    List<BookNote> list;
    Context context;
    int type;
    int proirIndex=-1;

    public BookNoteAdapter(Context context, List<BookNote> list, int type) {
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

    public void updateDatas(List<BookNote> books) {
        this.list=books;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        BookNote bookNote = (BookNote) list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.booknote_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder=(Holder)convertView.getTag();
        }
        holder.index.setText((position+1)+"");
        holder.index.setTextColor(Color.parseColor(Util.getRandomColor()));
        holder.user.setText(bookNote.getUserName());
        holder.content.setText(bookNote.getContent());

        holder.createdTime.setText(bookNote.getCreatedAt()+"2016.12.12");



       /* if (position > proirIndex) {
            convertView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.item_show_fly));
        } else {
            convertView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.item_show_drop));
        }*/
        proirIndex=position;
        return convertView;
    }

    class Holder{
        TextView content;
        TextView user;
        TextView createdTime;
        TextView index;

        public Holder(View view) {
            this.content = (TextView) view.findViewById(R.id.item_book_name);
            this.index = (TextView) view.findViewById(R.id.item_book_index_tv);
            this.user = (TextView) view.findViewById(R.id.item_book_id);
            this.createdTime = (TextView) view.findViewById(R.id.item_book_type);
        }
    }
}
