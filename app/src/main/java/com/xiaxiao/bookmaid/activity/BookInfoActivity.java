package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.graphics.Color;
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
import com.xiaxiao.bookmaid.bean.MyUser;
import com.xiaxiao.bookmaid.bean.RelationShip;
import com.xiaxiao.bookmaid.control.BmobServer;
import com.xiaxiao.bookmaid.control.BookNoteAdapter;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.GlideHelper;
import com.xiaxiao.bookmaid.util.GlobalData;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import de.hdodenhof.circleimageview.CircleImageView;

public class BookInfoActivity extends BaseActivity implements View.OnClickListener{

    private final int REQUSET_CODE_TIP=101;
    private final int REQUSET_CODE_SHELF=102;
    private ImageView back_img;
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
    private TextView title_tv;
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
        title_tv.setText(b.getName());
        if (b.getCoverImage()!=null) {
            GlideHelper.loadImage(this,b.getCoverImage().getUrl(),bookInfoCoverImg,R.drawable.book);
        }
        bookInfoNameTv.setText(b.getName());
        bookInfoWriterTv.setText(b.getWriter());
        if (b.getRecommendPerson().getHeadImage()!=null) {
            GlideHelper.loadImage(this,b.getRecommendPerson().getHeadImage().getUrl(),bookItemTuijianzheHeadCimg,R.drawable.app_head_gray);
        }
        bookItemTuijianzheNameTv.setText(b.getRecommendPerson().getUsername());
        bookInfoIntroduceTv.setText(b.getIntroduce());

        getInfos();
        checkBookIsAdded();


    }

    public void initViews() {
        back_img = (ImageView) findViewById(R.id.back_img);
        title_tv = (TextView) findViewById(R.id.bookinfo_title_name_tv);
        headerView = getLayoutInflater().inflate(R.layout.book_info_head_view, null);
        bookInfoCoverImg = (ImageView) headerView.findViewById(R.id.book_info_cover_img);
        bookInfoNameTv = (TextView) headerView.findViewById(R.id.book_info_name_tv);
        bookInfoWriterTv = (TextView) headerView.findViewById(R.id.book_info_writer_tv);
        bookItemTuijianzheHeadCimg = (CircleImageView) headerView.findViewById(R.id.book_item_tuijianzhe_head_cimg);
        bookItemTuijianzheNameTv = (TextView) headerView.findViewById(R.id.book_item_tuijianzhe_name_tv);
        bookInfoIntroduceTv = (TextView) headerView.findViewById(R.id.book_info_introduce_tv);

        addNote_ll = (LinearLayout) findViewById(R.id.bookinfo_add_note_ll);
        addShelf_ll = (LinearLayout) findViewById(R.id.bookinfo_add_shelf_ll);
        addShelf_tv = (TextView) findViewById(R.id.bookinfo_add_shelf_tv);
        addShelf_img = (ImageView) findViewById(R.id.bookinfo_add_shelf_img);
        listview = (ListView) findViewById(R.id.listview);
        listview.addHeaderView(headerView);

        addShelf_ll.setEnabled(false);
        addNote_ll.setOnClickListener(this);
        addShelf_ll.setOnClickListener(this);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookInfoActivity.this.finish();
            }
        });
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
                .getBookNotes(b, new BmobListener() {
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

    public void checkBookIsAdded() {
        new BmobServer.Builder(this).
                enableDialog(false)
                .build()
                .getShelf(Util.getUser(), new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        List<RelationShip> relationShipList = (List<RelationShip>) object;
                        if (relationShipList == null) {
                            ableAddShelf();
                        } else {
                            for (RelationShip r:relationShipList) {
                                //说明书架中已有
                                if (r.getBook().getObjectId().equals(b.getObjectId())) {
                                    enableAddShelf();
                                    return;
                                } else {
                                    ableAddShelf();
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(BmobException e) {
                        ableAddShelf();
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
            if (requestCode==REQUSET_CODE_TIP) {//添加小纸条
                getInfos();
                listview.smoothScrollToPosition(0);
            }
            if (requestCode==REQUSET_CODE_SHELF) {//添加到书架
                if (data.getBooleanExtra("ok", false)) {
                    enableAddShelf();
                } else {
                    ableAddShelf();
                }
            }
        }
    }


    public void addNote(String bookId,MyUser replyWho) {
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
            if (replyWho.getHeadImage()!=null) {
                intent.putExtra("replyWhoHeadImg", replyWho.getHeadImage().getUrl());
            }
            Util.L("huifu mougeren ");
        }
        startActivityForResult(intent,REQUSET_CODE_TIP);

    }

    public void addShelf() {
        if (!Util.isLogin()) {
            Util.goLoginPage(this);
            return;
        }
        Util.toast(this,"add shelf");
//        addShelf_ll.setEnabled(false);
        Util.goAddBookPage(this,false,REQUSET_CODE_SHELF);
    }

    public void enableAddShelf() {
        addShelf_ll.setEnabled(false);
        addShelf_tv.setText("已加入书架");
        addShelf_tv.setTextColor(Color.parseColor("#aaaaaa"));
        addShelf_img.setImageResource(R.drawable.shujia_yijiaru);
    }

    public void ableAddShelf() {
        addShelf_ll.setEnabled(true);
        addShelf_tv.setText("加入书架");
        addShelf_tv.setTextColor(Color.parseColor("#4a51f5"));
        addShelf_img.setImageResource(R.drawable.shujia_jiaru);
    }
}
