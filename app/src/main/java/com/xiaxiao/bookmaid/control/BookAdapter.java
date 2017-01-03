package com.xiaxiao.bookmaid.control;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.util.GlideHelper;
import com.xiaxiao.bookmaid.util.Util;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
        GlideHelper.loadImage(context,"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2847828995,2260978804&fm=58",holder.bookItemCoverImg);
        holder.bookItemNameTv.setText(book.getName());
        holder.bookItemWriterTv.setText(book.getWriter());
        holder.bookItemIntroduceTv.setText(book.getIntroduce());
        holder.bookItemBuyView.setBackgroundColor(Color.parseColor(Util.getRandomColor()));
        holder.bookItemReadView.setBackgroundColor(Color.parseColor(Util.getRandomColor()));
//        holder.bookItemTuijianzheNameTv.setText(book.getRecommendPerson().getUsername());
        GlideHelper.loadImage(context,"https://static.oschina.net/uploads/user/518/1036767_100.jpg?t=1477302684000",holder.bookItemTuijianzheHeadCimg);
        /*if (book.getBuyType() == 1) {
            holder.bookType.setText(R.string.book_type_buy);
            holder.bookType.setTextColor(Color.parseColor("#1296db"));
        } else {
            holder.bookType.setText(R.string.book_type_buy_no);
            holder.bookType.setTextColor(Color.parseColor("#8a8a8a"));

        }
        if (book.getReadType() == 1) {
            holder.bookRead.setText(R.string.book_read_yes);
            holder.bookRead.setTextColor(Color.parseColor("#1296db"));
        } else if (book.getReadType() == 0) {
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
        private ImageView bookItemCoverImg;
        private TextView bookItemNameTv;
        private TextView bookItemWriterTv;
        private TextView bookItemIntroduceTv;
        private View bookItemBuyView;
        private View bookItemReadView;
        private TextView bookItemTuijianTv;
        private CircleImageView bookItemTuijianzheHeadCimg;
        private TextView bookItemTuijianzheNameTv;


        public Holder(View view) {
            bookItemCoverImg = (ImageView) view.findViewById(R.id.book_item_cover_img);
            bookItemNameTv = (TextView) view.findViewById(R.id.book_item_name_tv);
            bookItemWriterTv = (TextView) view.findViewById(R.id.book_item_writer_tv);
            bookItemIntroduceTv = (TextView) view.findViewById(R.id.book_item_introduce_tv);
            bookItemBuyView = (View) view.findViewById(R.id.book_item_buy_view);
            bookItemReadView = (View) view.findViewById(R.id.book_item_read_view);
            bookItemTuijianTv = (TextView) view.findViewById(R.id.book_item_tuijian_tv);
            bookItemTuijianzheHeadCimg = (CircleImageView) view.findViewById(R.id.book_item_tuijianzhe_head_cimg);
            bookItemTuijianzheNameTv = (TextView) view.findViewById(R.id.book_item_tuijianzhe_name_tv);

        }
    }
}
