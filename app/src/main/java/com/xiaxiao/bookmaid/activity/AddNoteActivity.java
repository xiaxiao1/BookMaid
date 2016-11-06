package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;

public class AddNoteActivity extends AppCompatActivity {
EditText content;
    Button add_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        content = (EditText) findViewById(R.id.edit);
        add_btn = (Button) findViewById(R.id.add_btn);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contentStr = content.getText().toString();
                if (contentStr.equals("")) {
                    return ;
                }
                Intent intent=new Intent();
                Bundle b=new Bundle();
                b.putString("content",contentStr);
                intent.putExtras(b);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
