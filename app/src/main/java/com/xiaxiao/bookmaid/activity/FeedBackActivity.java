package com.xiaxiao.bookmaid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.FeedBack;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.Util;

import cn.bmob.v3.exception.BmobException;

public class FeedBackActivity extends BaseActivity {

    EditText et;
    Button send_btn;
    ImageView back_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        et = (EditText) findViewById(R.id.feedback_message_et);
        send_btn = (Button) findViewById(R.id.feedback_send_btn);
        back_img = (ImageView) findViewById(R.id.back_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedBackActivity.this.finish();
            }
        });
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et.getText().toString().trim().equals("")) {
                    return;
                }
                FeedBack feedBack = new FeedBack(et.getText().toString().trim(), Util.getUser().getObjectId());

                requsetBuilder.build()
                        .sendFeedback(feedBack, new BmobListener() {
                            @Override
                            public void onSuccess(Object object) {
                                Util.toast(FeedBackActivity.this,"发送成功，谢谢");
                                FeedBackActivity.this.finish();
                            }

                            @Override
                            public void onError(BmobException e) {
                                Util.toast(FeedBackActivity.this,"发送失败,请重试");
                            }
                        });
            }
        });
    }
}
