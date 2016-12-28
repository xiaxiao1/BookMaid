package com.xiaxiao.bookmaid.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
    public void showWaitDialog() {
        progressDialog = ProgressDialog.show(context, "", "等会哦", false, true, null);
        isShow=true;
    }

    public void dismissWaitDialog() {
        if (isShow) {
            progressDialog.dismiss();
        }
    }
    public void dismissCustomDialog() {
        if (dialog!=null&&dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showChooseDialog(final CustomDialogListener customDialogListener) {
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
        if (customDialogListener!=null) {

            cancle_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customDialogListener.onItemClick(2);
                }
            });
            takePhoto_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.toast(context,"take photo");
                    customDialogListener.onItemClick(0);
                }
            });
            fromGallery_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Util.toast(context,"from gallery");
                    customDialogListener.onItemClick(1);
                }
            });
        }
        dialog=new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.view_white_bg);
        dialog.show();
        //一定得在show完dialog后来set属性
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = context.getResources().getDimensionPixelSize(R.dimen.dialog_width);
        dialog.getWindow().setAttributes(lp);
    }


    public interface CustomDialogListener{
        public void onItemClick(int index);
    }
}
