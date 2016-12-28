package com.xiaxiao.bookmaid.activity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaxiao.bookmaid.control.BmobServer;
import com.xiaxiao.bookmaid.util.RuntimePermissionsManager;

public class BaseActivity extends AppCompatActivity {

    public BmobServer.Builder requsetBuilder;
    public RuntimePermissionsManager runtimePermissionsManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requsetBuilder = new BmobServer.Builder(this);

    }

    public RuntimePermissionsManager getRuntimePermissionManager(Activity activity) {
        if (runtimePermissionsManager==null) {
            runtimePermissionsManager = new RuntimePermissionsManager(activity);
        }
        return runtimePermissionsManager;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (runtimePermissionsManager!=null) {
            runtimePermissionsManager.handle(requestCode, permissions, grantResults);
        }
    }

}
