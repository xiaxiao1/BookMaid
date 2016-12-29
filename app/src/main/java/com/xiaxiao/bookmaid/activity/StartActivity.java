package com.xiaxiao.bookmaid.activity;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;
import com.xiaxiao.bookmaid.bean.MyUser;
import com.xiaxiao.bookmaid.util.BmobIniter;
import com.xiaxiao.bookmaid.util.Util;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

public class StartActivity extends BaseActivity {
RelativeLayout bg_rl;
    TextView appName_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BmobIniter.init(this);
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
                    if (Util.isLogin()) {
                        Util.setUser(BmobUser.getCurrentUser(MyUser.class));
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                    StartActivity.this.supportFinishAfterTransition();
                    StartActivity.this.startActivity(intent, ActivityOptions
 .makeSceneTransitionAnimation(StartActivity.this,appName_tv,"xiaxiao").toBundle());
                        StartActivity.this.startActivity(intent);
                        StartActivity.this.overridePendingTransition(R.anim.open_alpha,R.anim.exit_alpha);
                        StartActivity.this.finish();
                    } else {
                        StartActivity.this.startActivity(new Intent(StartActivity.this, LoginActivity.class));
                        StartActivity.this.overridePendingTransition(R.anim.open_alpha,R.anim.exit_alpha);
                        StartActivity.this.finish();
                    }

                }
            }
        });
        value.start();
    }

    List<BmobObject> bookBeens=new ArrayList<>();
    public void gogo1(View view){
//        UIDialog uiDialog = new UIDialog(this);
////        uiDialog.showWaitDialog();
//        BmobQuery<Book> query=new BmobQuery<Book>();
//        query.setLimit(50);
//        query.setSkip(50);
//        BmobHelper.getLists(query,new OnResultListener() {
//            @Override
//            public void onResult(Object object) {
//                List<Book> books = (List<Book>) object;
//                for (Book b:books) {
//                    BookBean bookBean = new BookBean(b.getName(), "writer", "a good book", 1, 1,"imgurl");
//                    bookBeens.add(bookBean);
//                }
//                BmobHelper.inserList(bookBeens, new OnResultListener() {
//                    @Override
//                    public void onSuccess(String objectId) {
//
//                    }
//
//                    @Override
//                    public void onError(BmobException e) {
//
//                    }
//                });
//            }
//            @Override
//            public void onSuccess(String objectId) {
//
//            }
//
//            @Override
//            public void onError(BmobException e) {
//
//            }
//        });

    }

    public void gogo(View view) {
//        BmobQuery<RelationShip> relationShipBmobQuery = new BmobQuery<>();
//        relationShipBmobQuery.findObjects(new FindListener<RelationShip>() {
//            @Override
//            public void done(List<RelationShip> list, BmobException e) {
//                if (e==null) {
//
//                    for (final RelationShip r:list) {
//                        BmobQuery<BookBean> bookBeanBmobQuery = new BmobQuery<BookBean>();
//                        bookBeanBmobQuery.addWhereEqualTo("name", r.getName());
//                        bookBeanBmobQuery.findObjects(new FindListener<BookBean>() {
//                            @Override
//                            public void done(List<BookBean> list, BmobException e) {
//                                if (e==null) {
//                                    r.setBookId(list.get(0).getObjectId());
//                                    r.update(new UpdateListener() {
//                                        @Override
//                                        public void done(BmobException e) {
//                                            if (e==null) {
//                                                Util.L("ok :"+num+r.getName());
//                                                num++;
//                                            }
//                                        }
//                                    });
//                                }
//                            }
//                        });
//                    }
//                }
//            }
//        });
    }

    int num=0;
}
