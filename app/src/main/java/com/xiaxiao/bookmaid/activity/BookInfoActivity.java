package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.control.BookNoteAdapter;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.GlideHelper;
import com.xiaxiao.bookmaid.util.GlobalData;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import de.hdodenhof.circleimageview.CircleImageView;

public class BookInfoActivity extends BaseActivity implements View.OnClickListener{

    private ImageView bookInfoCoverImg;
    private TextView bookInfoNameTv;
    private TextView bookInfoWriterTv;
    private CircleImageView bookItemTuijianzheHeadCimg;
    private TextView bookItemTuijianzheNameTv;
    private TextView bookInfoIntroduceTv;
    private ListView listview;
    private View headerView;


    List<BookNote> notes;
    BookNoteAdapter bookNoteAdapter;
    BookBean b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        initViews();
        b= GlobalData.book;
        notes = new ArrayList<>();
        GlideHelper.loadImage(this,"https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2847828995,2260978804&fm=58",bookInfoCoverImg);
        bookInfoNameTv.setText(b.getName());
        bookInfoWriterTv.setText(b.getWriter());
        GlideHelper.loadImage(this,"https://static.oschina.net/uploads/user/518/1036767_100.jpg?t=1477302684000",bookItemTuijianzheHeadCimg);
        bookItemTuijianzheNameTv.setText(b.getRecommendPerson().getUsername());
        bookInfoIntroduceTv.setText(b.getIntroduce());

        getInfos();


    }

    public void initViews() {
        headerView = getLayoutInflater().inflate(R.layout.book_info_head_view, null);
        bookInfoCoverImg = (ImageView) headerView.findViewById(R.id.book_info_cover_img);
        bookInfoNameTv = (TextView) headerView.findViewById(R.id.book_info_name_tv);
        bookInfoWriterTv = (TextView) headerView.findViewById(R.id.book_info_writer_tv);
        bookItemTuijianzheHeadCimg = (CircleImageView) headerView.findViewById(R.id.book_item_tuijianzhe_head_cimg);
        bookItemTuijianzheNameTv = (TextView) headerView.findViewById(R.id.book_item_tuijianzhe_name_tv);
        bookInfoIntroduceTv = (TextView) headerView.findViewById(R.id.book_info_introduce_tv);
        listview = (ListView) findViewById(R.id.listview);
        listview.addHeaderView(headerView);

    }
    public void getInfos() {
        requsetBuilder.build()
                .getBookNotes("", new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        notes = (List<BookNote>) object;
                        if (bookNoteAdapter==null) {
                            bookNoteAdapter = new BookNoteAdapter(BookInfoActivity.this, notes,0);
                            listview.setAdapter(bookNoteAdapter);
                        }
                    }

                    @Override
                    public void onError(BmobException e) {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        Intent i=new Intent(this,AddNoteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("bookId",b.getObjectId());
        i.putExtras(bundle);
        startActivityForResult(i,101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            BookNote bookNote2 = GlobalData.bookNote;
            if (bookNoteAdapter==null) {
                bookNoteAdapter = new BookNoteAdapter(this, notes, 0);
            }
            notes.add(0,bookNote2);
            bookNoteAdapter.notifyDataSetChanged();
        }
    }
}
