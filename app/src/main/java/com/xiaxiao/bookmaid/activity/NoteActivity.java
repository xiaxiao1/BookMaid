package com.xiaxiao.bookmaid.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xiaxiao.bookmaid.R;

public class NoteActivity extends BaseActivity {

    Fragment fragment2;
    private ImageView back_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initViews();
        fragment2 = Fragment2.newInstance(Fragment2.LIST_TYPE_PERSON_NOTES);
        FragmentTransaction mFragmentTransaction = getFragmentManager().beginTransaction();
        mFragmentTransaction.add(R.id.frame, fragment2).show(fragment2).commit();
    }


    public void initViews() {
        back_img = (ImageView) findViewById(R.id.note_back_img);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteActivity.this.finish();
            }
        });
    }
}
