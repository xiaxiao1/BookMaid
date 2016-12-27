package com.xiaxiao.bookmaid.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;

/**
 * Created by Administrator on 2016/11/4.
 */
public class UIDialog {
    private boolean isShow=false;
    private ProgressDialog progressDialog;
    AlertDialog dialog;
    private Context context;
    public UIDialog(Context context) {
        this.context = context;
    }
    public void showDialog() {
        progressDialog = ProgressDialog.show(context, "", "等会哦", false, true, null);
        isShow=true;
    }

    public void dismissDialog() {
        if (isShow) {
            progressDialog.dismiss();
        }
    }

    public void showChooseDialog() {
        if (dialog!=null) {
            dialog.show();
            return;
        }
        TextView takePhoto_tv;
        TextView fromGallery_tv;
        TextView cancle_tv;
        View view = View.inflate(context, R.layout.dialog_view_choose_photo, null);
        takePhoto_tv = (TextView) view.findViewById(R.id.take_photo_tv);
        fromGallery_tv = (TextView) view.findViewById(R.id.from_gallery_tv);
        cancle_tv = (TextView) view.findViewById(R.id.cancle_tv);
        cancle_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        takePhoto_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.toast(context,"take photo");
                dialog.dismiss();
            }
        });
        fromGallery_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.toast(context,"from gallery");
                dialog.dismiss();
            }
        });
        dialog=new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();
        dialog.show();
    }
}
