package com.xiaxiao.bookmaid.listener;

import android.graphics.Bitmap;

import com.bumptech.glide.request.animation.GlideAnimation;

/**
 * Created by xiaxiao on 2017/1/12.
 */

public abstract class OnGlideListener implements MyListener {
    public abstract  void onResourceReady(Bitmap resource, GlideAnimation glideAnimation);
}
