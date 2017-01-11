package com.xiaxiao.bookmaid.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;


import com.xiaxiao.bookmaid.R;

import java.text.DecimalFormat;


/**
 * Created by xiaxiao on 2016/12/21.
 */
public class RingView extends View {

    private final Paint paint;
    int length;
    int startAngle=-90;
    int sweepAngle=0;
    int fullAngle=0;
    int backgroundSweepAngle=0;

    int backGroundCircleColor;
    int foreGroundCircleColor;
    int circleWidth;
    int padding;
    DecimalFormat df;
    String title;

    int radius=60;
    int rx;
    int ry;
    boolean runOnetime=true;

    public RingView(Context context) {

        // TODO Auto-generated constructor stub
        this(context, null);
    }


    public RingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        df   = new DecimalFormat("######00.0000");
        this.paint = new Paint();
        this.paint.setAntiAlias(true); //消除锯齿
        this.paint.setStyle(Paint.Style.STROKE); //绘制空心圆

        TypedArray a=context.getTheme().obtainStyledAttributes(attrs,R.styleable.CircleLoading,defStyle,0);
        int n = a.getIndexCount();
        for (int i=0;i<n;i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.CircleLoading_backGroundColor:
                    backGroundCircleColor = a.getColor(attr, Color.parseColor("#88f56857"));
                    break;
                case R.styleable.CircleLoading_foreGroundColor:
                    foreGroundCircleColor = a.getColor(attr, Color.parseColor("#f56857"));
                    break;
                case R.styleable.CircleLoading_circleWidth:
                    circleWidth = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension
                            (TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics()));
                    padding=circleWidth/2;
                    break;
                case R.styleable.CircleLoading_percent:
                    fullAngle=(int)(360*a.getFloat(attr,0.0f));
                    break;
                case R.styleable.CircleLoading_circletTitle:
                    title = a.getString(attr);
                    break;

            }
        }
//        fullAngle=210;
        a.recycle();
        if (fullAngle!=0) {
            runProgress();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);

//        canvas.drawRect(0,0,getWidth(),getWidth(),paint);

        if (runOnetime) {
            drawProgress(sweepAngle, canvas);
        } else {
            backgroundSweepAngle=360;
            drawProgress(fullAngle,canvas);
        }
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        length = MeasureSpec.getSize(widthMeasureSpec);
        radius=(length-circleWidth)/2;
        rx=length/2;
        ry=length/2;
    }



    private void drawProgress(int sweepAngle,Canvas canvas) {
        this.paint.setStyle(Paint.Style.STROKE); //绘制空心圆
        paint.setColor(backGroundCircleColor);
        paint.setStrokeWidth(circleWidth);
        paint.setAntiAlias(true);
        RectF oval2 = new RectF(padding,padding, length-padding, length-padding);// 设置个新的长方形，扫描测量
        canvas.drawArc(oval2, startAngle, backgroundSweepAngle, false, paint);
        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(rx+radius*((float)Math.sin(backgroundSweepAngle*Math.PI/180)),ry-radius*((float)Math.cos(backgroundSweepAngle*Math.PI/180)),circleWidth/2,paint);
        paint.setColor(foreGroundCircleColor);

        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(length/2,circleWidth/2,circleWidth/2,paint);

        this.paint.setStyle(Paint.Style.STROKE); //绘制空心圆
        paint.setStrokeWidth(circleWidth);
        canvas.drawArc(oval2, startAngle, sweepAngle, false, paint);

        paint.setStrokeWidth(0);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle((float)(rx+radius*(Math.sin(sweepAngle*Math.PI/180))),(float)(ry-radius*(Math.cos(sweepAngle*Math.PI/180))),circleWidth/2,paint);
        Log.i("radius","radius: "+((float)Math.sin(sweepAngle*Math.PI))+"     y:"+((float)Math.cos(sweepAngle*Math.PI))+"  sweep:"+sweepAngle);
//        paint.setStrokeWidth(1);
        paint.setTextSize(length/4);
        double d=sweepAngle/360d*100;

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        String s=df.format(d).substring(0,5)+"%";

        paint.setTextSize(length/8);
        canvas.drawText(s,length/2-paint.measureText(s)/2,length/2,paint);
        canvas.drawText(title,length/2-paint.measureText(title)/2,length/2+length/6,paint);
        this.paint.setStyle(Paint.Style.STROKE);

    }

    private void runProgress() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                /*int t=1;
                if (fullAngle != 0) {
                    t = 360 / fullAngle;
                    if (fullAngle * t < 360) {
                        t++;
                    }
                } else {
                    t=360;
                }*/
                while (backgroundSweepAngle<360) {
                    backgroundSweepAngle=backgroundSweepAngle+2;
                    postInvalidate();

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                while (sweepAngle<fullAngle) {
                    sweepAngle++;
//                    backgroundSweepAngle=backgroundSweepAngle+t;
                    postInvalidate();

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if (runOnetime) {
                    runOnetime=false;
                }
            }
        }).start();
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public void runPercent(int angle) {
        if (angle>360) {
            angle=360;
        }
        fullAngle=angle;
        backgroundSweepAngle=0;
        sweepAngle=0;
        runProgress();
    }

    public void runPercent(float percent) {
        runPercent((int)( 360*percent));
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRunOneTime(boolean time) {
        runOnetime=time;
    }
}