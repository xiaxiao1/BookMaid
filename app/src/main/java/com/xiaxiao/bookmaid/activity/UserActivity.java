package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.util.GlobalData;

import cn.bmob.v3.BmobUser;

public class UserActivity extends AppCompatActivity {

    ImageView back;
    Button exit;
    TextView mingyan;
    TextView zuozhe;
    TextView tuijianzhe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initViews();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
                GlobalData.activity.finish();
                startActivity(new Intent(UserActivity.this,LoginActivity.class));
                finish();
            }
        });
    }

    public void initViews() {
        back = (ImageView) findViewById(R.id.back_img);
        exit = (Button) findViewById(R.id.exit_btn);
        mingyan = (TextView) findViewById(R.id.mingyan_tv);
        zuozhe = (TextView) findViewById(R.id.zuozhe_tv);
        tuijianzhe = (TextView) findViewById(R.id.tuijian_ren_tv);
    }

    public void logout() {
        BmobUser.logOut();   //清除缓存用户对象
        BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
    }
}
