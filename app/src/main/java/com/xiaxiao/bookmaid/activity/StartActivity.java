package com.xiaxiao.bookmaid.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.MyUser;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.BmobIniter;
import com.xiaxiao.bookmaid.util.Util;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

public class StartActivity extends BaseActivity {
RelativeLayout bg_rl;
    TextView appName_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobIniter.init(this);
        setContentView(R.layout.activity_start);
        bg_rl = (RelativeLayout) findViewById(R.id.bg_rl);
        appName_tv = (TextView) findViewById(R.id.app_name_tv);
        ValueAnimator value = ValueAnimator.ofInt(0, 150);
        value.setDuration(2000);
        value.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int v=(int)animation.getAnimatedValue();
                Util.L(v+"");
                bg_rl.setAlpha(v/100.0f);
                if (v==150) {
                    if (Util.isLogin()) {
                        requsetBuilder.build()
                                .login(BmobUser.getCurrentUser(MyUser.class), new BmobListener() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        Util.setUser((BmobUser)object);
                                        Util.goMainPage(StartActivity.this);
                                        StartActivity.this.finish();
                                    }

                                    @Override
                                    public void onError(BmobException e) {
                                        Util.goLoginPage(StartActivity.this);
                                    }
                                });

                        /*Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    *//*StartActivity.this.supportFinishAfterTransition();
                    StartActivity.this.startActivity(intent, ActivityOptions
 .makeSceneTransitionAnimation(StartActivity.this,appName_tv,"xiaxiao").toBundle());
                        StartActivity.this.startActivity(intent);
                        StartActivity.this.overridePendingTransition(R.anim.open_alpha,R.anim.exit_alpha);*//*
                        StartActivity.this.finish();*/
                    } else {
                        Util.goLoginPage(StartActivity.this);
                        StartActivity.this.finish();
                    }

                }
            }
        });
        value.start();
    }

}
