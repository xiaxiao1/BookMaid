package com.xiaxiao.bookmaid.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.util.Util;

public class StartActivity extends AppCompatActivity {
RelativeLayout bg_rl;
    TextView appName_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    Intent intent = new Intent(StartActivity.this, MainActivity.class);
//                    StartActivity.this.supportFinishAfterTransition();
//                    StartActivity.this.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(StartActivity.this,appName_tv,"xiaxiao").toBundle());
                    StartActivity.this.startActivity(intent);
                    StartActivity.this.finish();
                //    StartActivity.this.overridePendingTransition(R.anim.open_alpha,R.anim.exit_alpha);
                }
            }
        });
        value.start();
    }
}
