package com.xiaxiao.bookmaid.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xiaxiao.bookmaid.control.GlideRoundTransform;
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
                    Bitmap result =Bitmap.createBitmap(resource.getWidth(), resource.getHeight(), Bitmap.Config.ARGB_8888);

                    Canvas canvas = new Canvas(result);
                    Paint paint = new Paint();
                    paint.setShader(new BitmapShader(resource, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
                    paint.setAntiAlias(true);
                    RectF rectF = new RectF(0f, 0f, resource.getWidth()-10, resource.getHeight()-10);
                    canvas.drawRoundRect(rectF, 6, 6, paint);
//                    canvas.drawRect(rectF,paint);
                    resource.recycle();
                    onGlideListener.onResourceReady(result,glideAnimation);
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
//                .transform(new GlideRoundTransform(context,10))
                .into(simpleTarget);



    }
}
