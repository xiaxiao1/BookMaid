package com.xiaxiao.bookmaid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class AddBookActivity extends AppCompatActivity {

    EditText edit;
    TextView label;
    ImageView have_img;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        initViews();
    }

    public void initViews() {
        edit = (EditText) findViewById(R.id.addbook_name_et);
        label = (TextView) findViewById(R.id.addbook_have_label_tv);
        have_img = (ImageView) findViewById(R.id.addbook_have_img);
        submit = (Button) findViewById(R.id.addbook_submit);

        have_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
