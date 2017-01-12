package com.xiaxiao.bookmaid.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xiaxiao.bookmaid.listener.OnGlideListener;

/**
 * Created by xiaxiao on 2016/12/28.
 */
public class GlideHelper {
    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .crossFade()
                .into(imageView);

    }
    public static void loadImage(Context context, String url, ImageView imageView,int defaultResId) {
        Glide.with(context)
                .load(url)
//                .placeholder(defaultResId)
                .error(defaultResId)
//                .crossFade()
                .into(imageView);

    }
    public static void loadImageWithFitHeight(Context context, String url,  int defaultResId, final OnGlideListener onGlideListener) {

        SimpleTarget simpleTarget=new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                if (onGlideListener!=null) {
                    onGlideListener.onResourceReady(resource,glideAnimation);
                }
                Util.L(resource.getHeight()+"");
            }
        };
        Glide.with(context)
                .load(url)
                .asBitmap()
//                .placeholder(defaultResId)
//                .error(defaultResId)
//                .crossFade()
                .into(simpleTarget);



    }
}
