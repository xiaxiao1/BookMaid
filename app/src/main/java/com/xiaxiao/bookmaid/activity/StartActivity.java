package com.xiaxiao.bookmaid.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.util.BmobIniter;
import com.xiaxiao.bookmaid.util.GlobalData;
import com.xiaxiao.bookmaid.util.Util;

public class StartActivity extends BaseActivity {
RelativeLayout bg_rl;
    TextView appName_tv;
    TextView app1;
    TextView app2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobIniter.init(this);
        setContentView(R.layout.activity_start);
        bg_rl = (RelativeLayout) findViewById(R.id.bg_rl);
        appName_tv = (TextView) findViewById(R.id.app_name_tv);
        app1 = (TextView) findViewById(R.id.app1);
        app2 = (TextView) findViewById(R.id.app2);
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
                    //根据bmob api。只要缓存用户不为空 就可以认为登录，但是直接get name和password是空的
                    if (Util.isLogin()) {
                        Util.goMainPage(StartActivity.this);
                        StartActivity.this.finish();

                    } else {
//                        Util.goLoginPage(StartActivity.this);
                        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
                        Pair<View, String> pair1 = new Pair<View, String>(appName_tv,"app_name");
                        Pair<View, String> pair2 = new Pair<View, String>(app1,"app1");
                        Pair<View, String> pair3 = new Pair<View, String>(app2,"app2");
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(StartActivity.this,pair1,pair2,pair3).toBundle());
                        GlobalData.activity=StartActivity.this;
//                        StartActivity.this.finish();
                        Util.L("3");
                    }

                }
            }
        });
        value.start();
    }

}
