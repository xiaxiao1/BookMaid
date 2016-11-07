package com.xiaxiao.bookmaid.activity;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    EditText name;
    EditText mima;
    Button login_btn;
    UIDialog uiDialog;
    String nameStr;
    String mimaStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        uiDialog = new UIDialog(this);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameStr = name.getText().toString();
                mimaStr = mima.getText().toString();
                if (nameStr.equals("")||mimaStr.equals("")) {
                    Util.toast(LoginActivity.this,"呼呼，密码和名字没写全吧");
                    return;
                }
                final BmobUser bu = new BmobUser();
                bu.setUsername(nameStr);
                bu.setPassword(mimaStr);
                uiDialog.showDialog();

                bu.login(new SaveListener<BmobUser>() {

                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if(e==null){
//                            toast("登录成功:");
                            Util.toast(LoginActivity.this,"登录成功");
                            uiDialog.dismissDialog();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            LoginActivity.this.finish();
                        }else{
                            Util.L(e.toString());
                            if (e.getErrorCode()==101) {
                                //用户名和密码不匹配  但是不知道是错了还是没有这个账号，所以尝试注册
                                //注意：不能用save方法进行注册
                                bu.signUp(new SaveListener<BmobUser>() {
                                    @Override
                                    public void done(BmobUser s, BmobException e) {
                                        if(e==null){
                                           uiDialog.dismissDialog();
                                            Util.toast(LoginActivity.this,"注册 成功");
                                            Util.L(s.toString());
                                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                            LoginActivity.this.finish();
                                        }else{
                                            if (e.getErrorCode()==202) {
                                                uiDialog.dismissDialog();
                                                Util.toast(LoginActivity.this,"账户已存在或密码错误");
                                            }
                                        }
                                    }
                                });
                                /*uiDialog.dismissDialog();
                                Util.toast(LoginActivity.this,"用户名或密码错了哦");*/
                            }
                        }
                    }
                });


            }
        });
    }

    public void initViews() {
        name = (EditText) findViewById(R.id.edit_name);
        mima = (EditText) findViewById(R.id.edit_mima);
        login_btn = (Button) findViewById(R.id.login_btn);


    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK) {
            System.exit(0);
        }
        return super.onKeyDown(keyCode, event);
    }*/
}
