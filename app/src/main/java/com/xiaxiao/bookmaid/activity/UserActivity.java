package com.xiaxiao.bookmaid.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.FamousWord;
import com.xiaxiao.bookmaid.bean.FeedBack;
import com.xiaxiao.bookmaid.control.BookServer;
import com.xiaxiao.bookmaid.listener.OnResultListener;
import com.xiaxiao.bookmaid.util.GlobalData;
import com.xiaxiao.bookmaid.util.Util;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class UserActivity extends BaseActivity implements View.OnClickListener{

    ImageView back;
    ImageView tuijian_wenhao_img;
    Button exit;
    TextView mingyan;
    TextView zuozhe;
    TextView jianjie_tv;
    TextView tuijianzhe;
    LinearLayout feedBack_ll;
    LinearLayout feedBackLabel_ll;
    LinearLayout guanyuApp_ll;
    EditText editFeedbackMsg_et;
    Button sendFeedbackMsg_btn;

    /*编辑推荐名言*/
    EditText tuijianmingyan_neirong_et;
    EditText tuijianmingyan_zuozhe_et;
    Button sendTuijianmingyan_btn;
    LinearLayout tuijianmingyan_label_ll;
    LinearLayout tuijianmingyan_area_ll;

    /*展示推荐名言*/
    TextView showMingyan_tv;
    TextView showMingyanWriter_tv;
    TextView showMingyanTuijianren_tv;

    boolean show=false;
    int jianjieHeight=180;
    int tuijianmingyanHeight=330;
    LinearLayout.LayoutParams params;
    LinearLayout.LayoutParams tuijianMingyan_params;
    BookServer bookServer;
    FamousWord famousWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initViews();
        bookServer = new BookServer(this);
        params = (LinearLayout.LayoutParams) jianjie_tv.getLayoutParams();
        tuijianMingyan_params = (LinearLayout.LayoutParams) tuijianmingyan_area_ll.getLayoutParams();
        showFamousWord();

    }

    public void initViews() {
        back = (ImageView) findViewById(R.id.back_img);
        tuijian_wenhao_img = (ImageView) findViewById(R.id.tuijian_wenhao_img);
        exit = (Button) findViewById(R.id.exit_btn);

        sendFeedbackMsg_btn = (Button) findViewById(R.id.send_feedback_btn);
        editFeedbackMsg_et = (EditText) findViewById(R.id.feedback_message_et);
        feedBack_ll = (LinearLayout) findViewById(R.id.feedback_ll);
        feedBackLabel_ll = (LinearLayout) findViewById(R.id.feedback_label_ll);
        guanyuApp_ll = (LinearLayout) findViewById(R.id.guanyu_app_ll);
        mingyan = (TextView) findViewById(R.id.mingyan_tv);
        zuozhe = (TextView) findViewById(R.id.zuozhe_tv);
        tuijianzhe = (TextView) findViewById(R.id.tuijian_ren_tv);
        jianjie_tv = (TextView) findViewById(R.id.jianjie_tv);

        /*tuijian mingyan */
        tuijianmingyan_neirong_et = (EditText) findViewById(R.id.tuijianmingyan_et);
        tuijianmingyan_zuozhe_et = (EditText) findViewById(R.id.tuijianmingyan_zuozhe_et);
        tuijianmingyan_label_ll = (LinearLayout) findViewById(R.id.tuijianmingyan_label_ll);
        tuijianmingyan_area_ll = (LinearLayout) findViewById(R.id.tuijianmingyan_ll);
        sendTuijianmingyan_btn = (Button) findViewById(R.id.send_tuijianmingyan_btn);

        /*show  tuijian mingyan*/
        showMingyan_tv = (TextView) findViewById(R.id.mingyan_tv);
        showMingyanWriter_tv = (TextView) findViewById(R.id.zuozhe_tv);
        showMingyanTuijianren_tv= (TextView) findViewById(R.id.tuijian_ren_tv);

        back.setOnClickListener(this);
        exit.setOnClickListener(this);
        tuijian_wenhao_img.setOnClickListener(this);
        mingyan.setOnClickListener(this);
        feedBackLabel_ll.setOnClickListener(this);
        sendFeedbackMsg_btn.setOnClickListener(this);
        guanyuApp_ll.setOnClickListener(this);
        tuijianmingyan_label_ll.setOnClickListener(this);
        sendTuijianmingyan_btn.setOnClickListener(this);
       /* editFeedbackMsg_et.setOnClickListener(this);
        feedBack_ll.setOnClickListener(this);*/
    }

    public void logout() {
        BmobUser.logOut();   //清除缓存用户对象
        BmobUser currentUser = BmobUser.getCurrentUser(); // 现在的currentUser是null了
    }

    @Override
    public void onClick(View v) {
        if (v==exit) {
            logout();
            GlobalData.activity.finish();
            startActivity(new Intent(UserActivity.this,LoginActivity.class));
            finish();
        }
        if (v==back) {
            finish();
        }
        if (v==tuijian_wenhao_img) {
            /*TextView t = new TextView(this);
            t.setText("sdfsfsfddsdfsdf");*/
            AlertDialog a=new AlertDialog.Builder(this).setCancelable(true).setView(R.layout.mingyan_toast).create();
            a.show();
        }
        if (v==feedBackLabel_ll) {
            if (show) {
                Animation anim = AnimationUtils.loadAnimation(UserActivity.this, R.anim.out_to_top);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        feedBack_ll.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                feedBack_ll.startAnimation(anim);
            }else {
                Animation anim = AnimationUtils.loadAnimation(this, R.anim.in_from_top);
                anim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        feedBack_ll.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                feedBack_ll.startAnimation(anim);
            }
            show=!show;
        }
        if (v==sendFeedbackMsg_btn) {
            String msg = editFeedbackMsg_et.getText().toString().trim();
            if (msg.equals("")) {
                return;
            }
            FeedBack feedBack = new FeedBack(msg, Util.getUserId());
            feedBack.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        Util.toast(UserActivity.this, "收到咯");
                        Util.hideSoftInput(UserActivity.this,editFeedbackMsg_et);
                        editFeedbackMsg_et.setText("");
                        Animation anim = AnimationUtils.loadAnimation(UserActivity.this, R.anim.out_to_top);
                        anim.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                feedBack_ll.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        feedBack_ll.startAnimation(anim);
                    } else {
                        Util.hideSoftInput(UserActivity.this,editFeedbackMsg_et);
                        Util.toast(UserActivity.this, "啊！发送失败了");
                    }
                }
            });

        }
       if (v==guanyuApp_ll) {
           ValueAnimator valueAnimator;
           if (jianjie_tv.getHeight() != jianjieHeight) {
               valueAnimator= ValueAnimator.ofInt(0, jianjieHeight);

           } else {
               valueAnimator= ValueAnimator.ofInt(jianjieHeight,0);
           }
           valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
               @Override
               public void onAnimationUpdate(ValueAnimator animation) {
                   params.height=(int)animation.getAnimatedValue();
                   jianjie_tv.setLayoutParams(params);
               }
           });
           valueAnimator.setDuration(1000);
           valueAnimator.start();

        }
        if (v==tuijianmingyan_label_ll) {
            ValueAnimator valueAnimator;
            if (tuijianmingyan_area_ll.getHeight() != tuijianmingyanHeight) {
                valueAnimator= ValueAnimator.ofInt(0, tuijianmingyanHeight);

            } else {
                valueAnimator= ValueAnimator.ofInt(tuijianmingyanHeight,0);
            }
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    tuijianMingyan_params.height=(int)animation.getAnimatedValue();
                    tuijianmingyan_area_ll.setLayoutParams(tuijianMingyan_params);
                }
            });
            valueAnimator.setDuration(1000);
            valueAnimator.start();

        }
        if (v==sendTuijianmingyan_btn) {
            String msg = tuijianmingyan_neirong_et.getText().toString().trim();
            String wirter = tuijianmingyan_zuozhe_et.getText().toString().trim();
            if (msg.equals("")||wirter.equals("")) {
                return;
            }
            FamousWord famousWord = new FamousWord(msg,wirter,Util.getUserId(),BmobUser.getCurrentUser().getUsername());
            famousWord.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    Util.hideSoftInput(UserActivity.this,tuijianmingyan_neirong_et);
                    Util.hideSoftInput(UserActivity.this,tuijianmingyan_zuozhe_et);
                    if (e == null) {
                        Util.toast(UserActivity.this, "收到咯");
                        tuijianmingyan_neirong_et.setText("");
                        tuijianmingyan_zuozhe_et.setText("");
                        ValueAnimator valueAnimator;

                        valueAnimator= ValueAnimator.ofInt(tuijianmingyanHeight,0);
                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                tuijianMingyan_params.height=(int)animation.getAnimatedValue();
                                tuijianmingyan_area_ll.setLayoutParams(tuijianMingyan_params);
                            }
                        });
                        valueAnimator.setDuration(1000);
                        valueAnimator.start();
                    } else {
                        Util.toast(UserActivity.this, "啊！发送失败了");
                    }
                }
            });
        }
    }


    public void loadMingyan() {
        bookServer.getFamousWords(new OnResultListener() {
            @Override
            public void onResult(Object object) {
            GlobalData.famousWords=(List<FamousWord>)object;
                Util.L("famousword size:"+GlobalData.famousWords.size());
                famousWord = Util.pickOneFrom(GlobalData.famousWords);
                if (famousWord!=null) {
                    showMingyan_tv.setText(famousWord.getContent());
                    showMingyanWriter_tv.setText(famousWord.getWriter());
                    showMingyanTuijianren_tv.setText(famousWord.getUsername());
                }
            }
            @Override
            public void onSuccess(String objectId) {

            }

            @Override
            public void onError(BmobException e) {
                Util.L("查名言 出错了"+e.getMessage()+e.getErrorCode());
            }
        });
    }

    public void showFamousWord() {
        if (GlobalData.famousWords == null) {
            loadMingyan();
        } else {
            famousWord = Util.pickOneFrom(GlobalData.famousWords);
            if (famousWord!=null) {
                showMingyan_tv.setText(famousWord.getContent());
                showMingyanWriter_tv.setText(famousWord.getWriter());
                showMingyanTuijianren_tv.setText(famousWord.getUsername());
            }
        }
    }

    /*public void showView(final boolean isShow, final View view, int animId) {
        Animation anim = AnimationUtils.loadAnimation(UserActivity.this, R.anim.out_to_top);
        if (isShow) {
            anim = AnimationUtils.loadAnimation(UserActivity.this, R.anim.in_from_top);
        }
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isShow) {
                    view.setVisibility(View.VISIBLE);
                }else {
                    view.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(anim);
        isShow=!isShow;
    }*/
}
