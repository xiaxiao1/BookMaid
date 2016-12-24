package com.xiaxiao.bookmaid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.util.Util;
import com.xiaxiao.bookmaid.widget.BottomView;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        BottomView bottomView = (BottomView) findViewById(R.id.bottom);
        bottomView.setBottomClickListener(new BottomView.BottomClickListener(){
            @Override
            public void onBottomClick(int index) {
                Util.L("bottom index: "+index);
            }
        });
    }
}
