package com.xiaxiao.bookmaid.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

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
    public static void loadImageWithFitHeight(Context context, String url, final ImageView imageView, int defaultResId, final int fixedWidth) {

        SimpleTarget simpleTarget=new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                Util.L(resource.getHeight()+"");
                ViewGroup.LayoutParams p=imageView.getLayoutParams();
//                p.width=w;
                p.height=Util.getSelfAdaptionHeight(fixedWidth,resource);
//重置ImageView的宽高度
                imageView.setLayoutParams(p);
                imageView.setImageBitmap(resource);
            }
        };
        Glide.with(context)
                .load(url)
                .asBitmap()
//                .placeholder(defaultResId)
                .error(defaultResId)
//                .crossFade()
                .into(simpleTarget);



    }
}
