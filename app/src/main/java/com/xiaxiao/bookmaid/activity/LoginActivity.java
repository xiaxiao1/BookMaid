package com.xiaxiao.bookmaid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.MyUser;
import com.xiaxiao.bookmaid.util.UIDialog;
import com.xiaxiao.bookmaid.util.Util;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 确定好用户是否登陆的逻辑  并不只是判断是否有currentUser   需要提取出用户密码后调用login 显示登录
 */
public class LoginActivity extends BaseActivity {

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
                final BmobUser bu = new MyUser();
                bu.setUsername(nameStr);
                bu.setPassword(mimaStr);
                uiDialog.showWaitDialog();

                bu.login(new SaveListener<BmobUser>() {

                    @Override
                    public void done(BmobUser bmobUser, BmobException e) {
                        if(e==null){
//                            toast("登录成功:");
                            Util.toast(LoginActivity.this,"登录成功");
                            Util.setUser(BmobUser.getCurrentUser(MyUser.class));
                            uiDialog.dismissWaitDialog();
                            startActivity(new Intent(LoginActivity.this,MainActivity2.class));
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
                                           uiDialog.dismissWaitDialog();
                                            Util.toast(LoginActivity.this,"注册 成功");
                                            Util.L("注册成功："+s.toString()+s.getObjectId()+s.getUsername());
                                            Intent intent=new Intent(LoginActivity.this,MainActivity2.class);
//                                            intent.putExtra("userId", s.getObjectId());
                                            Util.setUser(s);
                                            startActivity(intent);
                                            LoginActivity.this.finish();
                                        }else{
                                            if (e.getErrorCode()==202) {
                                                uiDialog.dismissWaitDialog();
                                                Util.toast(LoginActivity.this,"账户已存在或密码错误");
                                            }
                                        }
                                    }
                                });
                                /*uiDialog.dismissWaitDialog();
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
