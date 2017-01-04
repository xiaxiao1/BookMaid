package com.xiaxiao.bookmaid.util;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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
                .crossFade()
                .into(imageView);

    }
}
