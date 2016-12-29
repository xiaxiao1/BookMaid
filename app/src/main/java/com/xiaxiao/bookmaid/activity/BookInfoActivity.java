package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.control.BookNoteAdapter;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.GlideHelper;
import com.xiaxiao.bookmaid.util.GlobalData;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
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

    private LinearLayout addNote_ll;
    /*private TextView addNote_tv;
    private ImageView addNote_img;*/
    private LinearLayout addShelf_ll;
    private TextView addShelf_tv;
    private ImageView addShelf_img;


    List<BookNote> notes;
    BookNoteAdapter bookNoteAdapter;
    BookBean b;
    boolean isAddedShelf=false;
    UIDialog uiDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        initViews();
        uiDialog = new UIDialog(this);
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

        addNote_ll = (LinearLayout) findViewById(R.id.bookinfo_add_note_ll);
        addShelf_ll = (LinearLayout) findViewById(R.id.bookinfo_add_shelf_ll);
        addShelf_tv = (TextView) findViewById(R.id.bookinfo_add_note_tv);
        addShelf_img = (ImageView) findViewById(R.id.bookinfo_add_shelf_img);
        listview = (ListView) findViewById(R.id.listview);
        listview.addHeaderView(headerView);

        addNote_ll.setOnClickListener(this);
        addShelf_ll.setOnClickListener(this);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                uiDialog.showAddNoteDialog(new UIDialog.CustomDialogListener() {
                    @Override
                    public void onItemClick(int index) {

                        addNote(b.getObjectId(),notes.get(position-listview.getHeaderViewsCount()).getWhoWrite());
                    }
                });
            }
        });

    }
    public void getInfos() {
        requsetBuilder.build()
                .getBookNotes("", new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        notes = (List<BookNote>) object;
                        if (bookNoteAdapter == null) {
                            bookNoteAdapter = new BookNoteAdapter(BookInfoActivity.this, notes, 0);
                            listview.setAdapter(bookNoteAdapter);
                        } else {
                            bookNoteAdapter.updateDatas(notes);
                            bookNoteAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(BmobException e) {

                    }
                });
    }

    @Override
    public void onClick(View v) {
        /*Intent i=new Intent(this,AddNoteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("bookId",b.getObjectId());
        i.putExtras(bundle);
        startActivityForResult(i,101);*/
        int id = v.getId();
        switch (id) {
            case R.id.bookinfo_add_note_ll:
                addNote(b.getObjectId(),null);
                break;
            case R.id.bookinfo_add_shelf_ll:
                addShelf();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK) {
            if (requestCode==101) {//添加小纸条
                getInfos();
                listview.smoothScrollToPosition(0);
            }
        }
    }


    public void addNote(String bookId,BmobUser replyWho) {
        if (bookId==null) {
            return;
        }
        if (!Util.isLogin()) {
            Util.goLoginPage(this);
            return;
        }
        Intent intent = new Intent(this, SendNoteActivity.class);
        intent.putExtra("bookId", bookId);
        if (replyWho==null) {
            Util.L("zhijie huifu");
            intent.putExtra("replyWhoId", "");
            intent.putExtra("replyWhoName", "");
            intent.putExtra("replyWhoHeadImg", "");
        } else {
            intent.putExtra("replyWhoId", replyWho.getObjectId());
            intent.putExtra("replyWhoName", replyWho.getUsername());
            intent.putExtra("replyWhoHeadImg", "asd123");
            Util.L("huifu mougeren ");
        }
        startActivityForResult(intent,101);

    }

    public void addShelf() {
        if (!Util.isLogin()) {
            Util.goLoginPage(this);
            return;
        }
        Util.toast(this,"add shelf");
        addShelf_ll.setEnabled(false);
    }
}
