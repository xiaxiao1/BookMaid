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
public class IdeaAdapter extends MyBaseAdapter{

    int type;
    int proirIndex=-2;

    public IdeaAdapter(Context context, List list, int type) {
       super(context,list);
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        BookNote bookNote = (BookNote) list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.idea_item, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder=(Holder)convertView.getTag();
        }
        if (bookNote.getBook().getCoverImage() != null) {
            GlideHelper.loadImage(context, bookNote.getBook().getCoverImage().getUrl(), holder.bookCover_img,R.drawable.book);
        } else {
            holder.bookCover_img.setImageResource(R.drawable.book);
        }
        holder.bookName_tv.setText(bookNote.getBook().getName());
        if (bookNote.getWhoWrite().getHeadImage() != null) {
            GlideHelper.loadImage(context, bookNote.getWhoWrite().getHeadImage().getUrl(), holder.writerHeadImg_cimg, R.drawable.app_head_gray);
        } else {
            holder.writerHeadImg_cimg.setImageResource(R.drawable.app_icon_1);
        }
        holder.writerName_tv.setText(bookNote.getWhoWrite().getUsername());
        if (bookNote.getReplyWhos()!=null&&!bookNote.getReplyWhos().getObjectId().equals("")) {
            holder.toWhoArea_ll.setVisibility(View.VISIBLE);
            if (bookNote.getReplyWhos().getHeadImage() != null) {
                GlideHelper.loadImage(context, bookNote.getReplyWhos().getHeadImage().getUrl(), holder.toWhoHeadImg_cimg, R.drawable.app_head_gray);
            } else {
                holder.toWhoHeadImg_cimg.setImageResource(R.drawable.app_icon_1);
            }
            holder.toWhoName_tv.setText(bookNote.getReplyWhos().getUsername());
        } else {
            holder.toWhoArea_ll.setVisibility(View.GONE);
        }
        holder.ideaContent_tv.setText(bookNote.getContent());
        holder.createTime_tv.setText(bookNote.getCreatedAt());



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
        private ImageView bookCover_img;
        private TextView bookName_tv;

        private CircleImageView writerHeadImg_cimg;
        private TextView writerName_tv;
        private LinearLayout toWhoArea_ll;
        private CircleImageView toWhoHeadImg_cimg;
        private TextView toWhoName_tv;
        private TextView ideaContent_tv;
        private TextView createTime_tv;


        public Holder(View view) {
            bookCover_img=(ImageView)view.findViewById(R.id.idea_item_book_cover_img);
            bookName_tv = (TextView) view.findViewById(R.id.idea_item_book_name_tv);
            writerHeadImg_cimg = (CircleImageView) view.findViewById(R.id.idea_item_fromwho_head_cmig);
            writerName_tv = (TextView) view.findViewById(R.id.idea_item_fromwho_name_tv);
            toWhoArea_ll = (LinearLayout) view.findViewById(R.id.idea_item_towho_ll);
            toWhoHeadImg_cimg = (CircleImageView) view.findViewById(R.id.idea_item_towho_head_cimg);
            toWhoName_tv = (TextView) view.findViewById(R.id.idea_item_towho_name_tv);
            ideaContent_tv = (TextView) view.findViewById(R.id.idea_item_content_tv);
            createTime_tv = (TextView) view.findViewById(R.id.idea_item_time_tv);

        }
    }
}
