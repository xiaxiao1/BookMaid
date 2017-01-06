package com.xiaxiao.bookmaid.control;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.bean.RelationShip;
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

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        //数据源来自两个页面，类型不一样  一个是bookbean  主页，一个是RelationShip  我的书架  进行相关判断

        Object o=list.get(position);
        BookBean book;
        RelationShip relationShip;


        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.book_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder=(Holder)convertView.getTag();
        }

        if (o instanceof RelationShip) {
            relationShip = (RelationShip) o;
            book = relationShip.getBook();
            holder.buylabel_tv.setVisibility(View.VISIBLE);
            holder.readLabel_tv.setVisibility(View.VISIBLE);
            Util.setBuyLabelStyle(context,holder.buylabel_tv,relationShip.getBuyType()==1);
            Util.setReadLabelStyle(context,holder.readLabel_tv,relationShip.getReadType()==1);
        } else {
            book=(BookBean)o;
        }

        if (book.getCoverImage() != null) {
            GlideHelper.loadImage(context, book.getCoverImage().getUrl(), holder
                    .bookItemCoverImg, R.drawable.book);
        } else {
            holder.bookItemCoverImg.setImageResource(R.drawable.book);
        }
        holder.bookItemNameTv.setText(book.getName());
        holder.bookItemWriterTv.setText(book.getWriter());
        holder.bookItemIntroduceTv.setText(book.getIntroduce());
        /*holder.bookItemBuyView.setBackgroundColor(Color.parseColor(Util.getRandomColor()));
        holder.bookItemReadView.setBackgroundColor(Color.parseColor(Util.getRandomColor()));*/
        holder.bookItemTuijianzheNameTv.setText(book.getRecommendPerson().getUsername());
        if (book.getRecommendPerson().getHeadImage() != null) {
            GlideHelper.loadImage(context, book.getRecommendPerson().getHeadImage().getUrl(),
                    holder.bookItemTuijianzheHeadCimg, R.drawable.app_head_gray);
        } else {
            holder.bookItemTuijianzheHeadCimg.setImageResource(R.drawable.app_head_gray);
        }


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
        private TextView buylabel_tv;
        private TextView readLabel_tv;
        private TextView bookItemIntroduceTv;
        /*private View bookItemBuyView;
        private View bookItemReadView;*/
        private CircleImageView bookItemTuijianzheHeadCimg;
        private TextView bookItemTuijianzheNameTv;


        public Holder(View view) {
            bookItemCoverImg = (ImageView) view.findViewById(R.id.book_item_cover_img);
            bookItemNameTv = (TextView) view.findViewById(R.id.book_item_name_tv);
            bookItemWriterTv = (TextView) view.findViewById(R.id.book_item_writer_tv);
            buylabel_tv = (TextView) view.findViewById(R.id.bookitem_buy_label_tv);
            readLabel_tv = (TextView) view.findViewById(R.id.bookitem_read_label_tv);
            bookItemIntroduceTv = (TextView) view.findViewById(R.id.book_item_introduce_tv);
            /*bookItemBuyView = (View) view.findViewById(R.id.book_item_buy_view);
            bookItemReadView = (View) view.findViewById(R.id.book_item_read_view);*/
            bookItemTuijianzheHeadCimg = (CircleImageView) view.findViewById(R.id.book_item_tuijianzhe_head_cimg);
            bookItemTuijianzheNameTv = (TextView) view.findViewById(R.id.book_item_tuijianzhe_name_tv);

        }
    }
}
