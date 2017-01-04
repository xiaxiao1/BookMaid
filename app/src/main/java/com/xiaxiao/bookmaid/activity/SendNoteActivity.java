package com.xiaxiao.bookmaid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.BookBean;
import com.xiaxiao.bookmaid.bean.BookNote;
import com.xiaxiao.bookmaid.bean.MyUser;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.GlideHelper;
import com.xiaxiao.bookmaid.util.Util;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import de.hdodenhof.circleimageview.CircleImageView;

public class SendNoteActivity extends BaseActivity {

    private LinearLayout replyWho_ll;
    private CircleImageView towhoHead_cimg;
    private TextView towhoName_tv;
    private EditText addedNote_tv;
    private LinearLayout sendNote_ll;
    private ImageView back_img;

    private  String bookId;
    private  String replyWhoId;
    private  String replyWhoName;
    private  String replyWhoHeadImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_note);
        initViews();
        bookId = getIntent().getStringExtra("bookId");
        replyWhoId = getIntent().getStringExtra("replyWhoId");
        replyWhoName = getIntent().getStringExtra("replyWhoName");
        replyWhoHeadImg = getIntent().getStringExtra("replyWhoHeadImg");
        if (replyWhoId != null && !replyWhoId.equals("")) {
            replyWho_ll.setVisibility(View.VISIBLE);
            GlideHelper.loadImage(this, replyWhoHeadImg, towhoHead_cimg,R.drawable.app_head_gray);
            towhoName_tv.setText(replyWhoName);
        } else {
            replyWho_ll.setVisibility(View.GONE);
        }



    }

    public void initViews() {
        back_img = (ImageView) findViewById(R.id.sendnote_back_img);
        replyWho_ll = (LinearLayout) findViewById(R.id.sendnote_towho_ll);
        towhoHead_cimg = (CircleImageView) findViewById(R.id.sendnote_towho_head_cimg);
        towhoName_tv = (TextView) findViewById(R.id.sendnote_towho_name_tv);
        addedNote_tv = (EditText) findViewById(R.id.sendnote_addnote_et);
        sendNote_ll = (LinearLayout) findViewById(R.id.send_note_send_ll);

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendNoteActivity.this.finish();
            }
        });
        sendNote_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=addedNote_tv.getText().toString();
                if (str==null||str.equals("")) {
                    return;
                }
                Util.L(str);
                sendNote(str,bookId,Util.getUser().getObjectId(),replyWhoId);
            }
        });
    }

    public void sendNote(String content, String bookId, String writerId, String replyWhoId) {
        BookNote bookNote = new BookNote();
        bookNote.setContent(content);
        BookBean bookBean = new BookBean();
        bookBean.setObjectId(bookId);
        MyUser writer = new MyUser();
        writer.setObjectId(writerId);
        if (replyWhoId!=null&&!replyWhoId.equals("")) {
            MyUser replyWho = new MyUser();
            replyWho.setObjectId(replyWhoId);
            bookNote.setReplyWhos(replyWho);
        }
        bookNote.setBook(bookBean);
        bookNote.setWhoWrite(writer);
        requsetBuilder.build()
                .addBookNote(bookNote, new BmobListener() {
                    @Override
                    public void onSuccess(Object object) {
                        Util.toast(SendNoteActivity.this,"发送成功");
                        Util.hideSoftInput(SendNoteActivity.this,addedNote_tv);
                        SendNoteActivity.this.setResult(RESULT_OK,null);
                        SendNoteActivity.this.finish();
                    }

                    @Override
                    public void onError(BmobException e) {
                        Util.L("error:"+e.getMessage());
                        Util.toast(SendNoteActivity.this,"发送失败，请重试");
                    }
                });
    }
}
