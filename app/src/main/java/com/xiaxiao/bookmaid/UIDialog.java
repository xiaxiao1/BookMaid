package com.xiaxiao.bookmaid;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Administrator on 2016/11/4.
 */
public class UIDialog {
    private boolean isShow=false;
    private ProgressDialog progressDialog;
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
}
