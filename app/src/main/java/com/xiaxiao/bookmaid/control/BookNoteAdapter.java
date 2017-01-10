package com.xiaxiao.bookmaid.control;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.util.GlideHelper;

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
        if (bookNote.getWhoWrite().getHeadImage() != null) {
            GlideHelper.loadImage(context, bookNote.getWhoWrite().getHeadImage().getUrl(), holder.booknoteItemFromwhoHeadCmig);
        } else {
            holder.booknoteItemFromwhoHeadCmig.setImageResource(R.drawable.app_head_gray);
        }
        holder.booknoteItemFromwhoNameTv.setText(bookNote.getWhoWrite().getUsername());
        if (bookNote.getReplyWhos()!=null&&!bookNote.getReplyWhos().getObjectId().equals("")) {
            holder.booknoteItemTowhoLl.setVisibility(View.VISIBLE);
            if (bookNote.getReplyWhos().getHeadImage() != null) {
                GlideHelper.loadImage(context, bookNote.getReplyWhos().getHeadImage().getUrl(), holder.booknoteItemTowhoHeadCimg);
            } else {
                holder.booknoteItemTowhoHeadCimg.setImageResource(R.drawable.app_head_gray);
            }
            holder.booknoteItemTowhoNameTv.setText(bookNote.getReplyWhos().getUsername());
        } else {
            holder.booknoteItemTowhoLl.setVisibility(View.GONE);
        }
        holder.booknoteItemContentTv.setText(bookNote.getContent());

        if (bookNote.getNotePic() != null && bookNote.getNotePic().getUrl() != null) {
            GlideHelper.loadImage(context, bookNote.getNotePic().getUrl(), holder.booknotePic);
        } else {
            
        }
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
        private ImageView booknotePic;
        private TextView booknoteItemTimeTv;


        public Holder(View view) {
            booknoteItemFromwhoHeadCmig = (CircleImageView) view.findViewById(R.id.booknote_item_fromwho_head_cmig);
            booknoteItemFromwhoNameTv = (TextView) view.findViewById(R.id.booknote_item_fromwho_name_tv);
            booknoteItemTowhoLl = (LinearLayout) view.findViewById(R.id.booknote_item_towho_ll);
            booknoteItemTowhoHeadCimg = (CircleImageView) view.findViewById(R.id.booknote_item_towho_head_cimg);
            booknoteItemTowhoNameTv = (TextView) view.findViewById(R.id.booknote_item_towho_name_tv);
            booknoteItemContentTv = (TextView) view.findViewById(R.id.booknote_item_content_tv);
            booknotePic = (ImageView) view.findViewById(R.id.note_pic_img);
            booknoteItemTimeTv = (TextView) view.findViewById(R.id.booknote_item_time_tv);

        }
    }
}
