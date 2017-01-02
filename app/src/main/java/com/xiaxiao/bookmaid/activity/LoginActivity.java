package com.xiaxiao.bookmaid.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.MyUser;
import com.xiaxiao.bookmaid.listener.BmobListener;
import com.xiaxiao.bookmaid.util.Util;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;

/**
 * 确定好用户是否登陆的逻辑
 */
public class LoginActivity extends BaseActivity {

    EditText name;
    EditText mima;
    Button login_btn;
    String nameStr;
    String mimaStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
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

                requsetBuilder.build()
                        .login(bu, new BmobListener() {
                            @Override
                            public void onSuccess(Object object) {
                                Util.toast(LoginActivity.this,"登录成功");
                                Util.goMainPage(LoginActivity.this);
                                LoginActivity.this.finish();
                            }

                            @Override
                            public void onError(BmobException e) {
                                Util.L(e.toString());
                                if (e.getErrorCode()==101) {
                                    //用户名和密码不匹配  但是不知道是错了还是没有这个账号，所以尝试注册
                                    //注意：不能用save方法进行注册
                                    requsetBuilder.build()
                                            .signUp(bu, new BmobListener() {
                                                @Override
                                                public void onSuccess(Object object) {
                                                    Util.toast(LoginActivity.this,"注册 成功");
                                                    Util.L("注册成功：");
                                                    Util.setUser((BmobUser)object);
                                                   Util.goMainPage(LoginActivity.this);
                                                    LoginActivity.this.finish();
                                                }

                                                @Override
                                                public void onError(BmobException e) {
                                                    if (e.getErrorCode() == 202) {
                                                        Util.toast(LoginActivity.this, "账户已存在或密码错误");
                                                    } else {
                                                        Util.toast(LoginActivity.this, "出现了一个很神奇的错误");
                                                    }
                                                }
                                            });
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
