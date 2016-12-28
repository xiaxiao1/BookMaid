package com.xiaxiao.bookmaid.control;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.util.GlideHelper;
import com.xiaxiao.bookmaid.util.Util;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xiaxi on 2016/11/2.
 */
public class BookNoteAdapter extends MyBaseAdapter{

    int type;
    int proirIndex=-2;

    public BookNoteAdapter(Context context, List list, int type) {
       super(context,list);
        this.type = type;
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
        GlideHelper.loadImage(context,"https://static.oschina.net/uploads/user/518/1036767_100.jpg?t=1477302684000",holder.booknoteItemFromwhoHeadCmig);
        holder.booknoteItemFromwhoNameTv.setText(bookNote.getWhoWrite().getUsername());
        if (!bookNote.getReplyWhos().getObjectId().equals("")) {
            holder.booknoteItemTowhoLl.setVisibility(View.VISIBLE);
            GlideHelper.loadImage(context,"https://static.oschina.net/uploads/user/518/1036767_100.jpg?t=1477302684000",holder.booknoteItemTowhoHeadCimg);
            holder.booknoteItemTowhoNameTv.setText(bookNote.getReplyWhos().getUsername());
        } else {
            holder.booknoteItemTowhoLl.setVisibility(View.GONE);
        }
        holder.booknoteItemContentTv.setText(bookNote.getContent());
        holder.booknoteItemTimeTv.setText(bookNote.getCreatedAt());



        if (position > proirIndex) {
            convertView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.item_show_fly));
        } else {
            convertView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.item_show_drop));
        }
//        convertView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.open_alpha));
        proirIndex=position;
        return convertView;
    }

    class Holder{
        private CircleImageView booknoteItemFromwhoHeadCmig;
        private TextView booknoteItemFromwhoNameTv;
        private LinearLayout booknoteItemTowhoLl;
        private CircleImageView booknoteItemTowhoHeadCimg;
        private TextView booknoteItemTowhoNameTv;
        private TextView booknoteItemContentTv;
        private ImageView booknoteItemRightimg;
        private TextView booknoteItemTimeTv;


        public Holder(View view) {
            booknoteItemFromwhoHeadCmig = (CircleImageView) view.findViewById(R.id.booknote_item_fromwho_head_cmig);
            booknoteItemFromwhoNameTv = (TextView) view.findViewById(R.id.booknote_item_fromwho_name_tv);
            booknoteItemTowhoLl = (LinearLayout) view.findViewById(R.id.booknote_item_towho_ll);
            booknoteItemTowhoHeadCimg = (CircleImageView) view.findViewById(R.id.booknote_item_towho_head_cimg);
            booknoteItemTowhoNameTv = (TextView) view.findViewById(R.id.booknote_item_towho_name_tv);
            booknoteItemContentTv = (TextView) view.findViewById(R.id.booknote_item_content_tv);
            booknoteItemRightimg = (ImageView) view.findViewById(R.id.booknote_item_rightimg);
            booknoteItemTimeTv = (TextView) view.findViewById(R.id.booknote_item_time_tv);

        }
    }
}
