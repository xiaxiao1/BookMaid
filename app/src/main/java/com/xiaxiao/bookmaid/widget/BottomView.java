package com.xiaxiao.bookmaid.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaxiao.bookmaid.R;

/**
 * Created by xiaxiao on 2016/12/23.
 */
public class BottomView extends RelativeLayout implements View.OnClickListener{
    LinearLayout[] taps;
    TextView[] tapTexts;
    ImageView[] tapImgs;
    int[] onResId={R.drawable.all_on,
            R.drawable.have_on2,
            R.drawable.buy_on,
            R.drawable.buy_on};
    int[] offResId={R.drawable.all_off,
            R.drawable.have_off2,
            R.drawable.buy_off,
            R.drawable.buy_off};
    static final int COLOR_ON = Color.BLUE;
    static final int COLOR_OFF = Color.GRAY;
    int currentIndex=0;
    LinearLayout currentTap;
    BottomClickListener mBottomClickListener;
    public BottomView(Context context) {
        this(context,null);
    }

    public BottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.main_bottom, this);
        taps = new LinearLayout[4];
        tapTexts = new TextView[4];
        tapImgs = new ImageView[4];
        taps[0] = (LinearLayout) findViewById(R.id.tap1_ll);
        taps[1] = (LinearLayout) findViewById(R.id.tap2_ll);
        taps[2] = (LinearLayout) findViewById(R.id.tap3_ll);
        taps[3] = (LinearLayout) findViewById(R.id.tap4_ll);

        tapTexts[0] = (TextView) findViewById(R.id.tap1_label_tv);
        tapTexts[1] = (TextView) findViewById(R.id.tap2_label_tv);
        tapTexts[2] = (TextView) findViewById(R.id.tap3_label_tv);
        tapTexts[3] = (TextView) findViewById(R.id.tap4_label_tv);

        tapImgs[0] = (ImageView) findViewById(R.id.tap1_img);
        tapImgs[1] = (ImageView) findViewById(R.id.tap2_img);
        tapImgs[2] = (ImageView) findViewById(R.id.tap3_img);
        tapImgs[3] = (ImageView) findViewById(R.id.tap4_img);
        taps[0].setOnClickListener(this);
        taps[1].setOnClickListener(this);
        taps[2].setOnClickListener(this);
        taps[3].setOnClickListener(this);

        currentIndex = 0;
        currentTap=taps[0];
    }


    protected void setTextColor(TextView textView, int color) {
        textView.setTextColor(color);
    }

    protected void setTapImg(ImageView imageView, int resId) {
        imageView.setImageResource(resId);
    }

    public void setBottomClickListener(BottomClickListener bottomClickListener) {
        mBottomClickListener=bottomClickListener;

    }

    @Override
    public void onClick(View v) {
        //如果点击时当前的tap，什么也不做
        if (v==currentTap) {
            return;
        }
        setTextColor(tapTexts[currentIndex],COLOR_OFF);
        setTapImg(tapImgs[currentIndex],offResId[currentIndex]);
        /*if (v==taps[0]) {
            setTextColor(tapTexts[0],COLOR_ON);
            setTapImg(tapImgs[0],onResId[0]);
            currentIndex=0;
        }
        if (v==taps[1]) {
            setTextColor(tapTexts[1],COLOR_ON);
            setTapImg(tapImgs[1],onResId[1]);
            currentIndex=1;
        }
        if (v==taps[2]) {
            setTextColor(tapTexts[2],COLOR_ON);
            setTapImg(tapImgs[2],onResId[2]);
            currentIndex=2;
        }
        if (v==taps[3]) {
            setTextColor(tapTexts[3],COLOR_ON);
            setTapImg(tapImgs[3],onResId[3]);
            currentIndex=3;
        }*/
        for (int i=0;i<4;i++) {
            if (v==taps[i]) {
                setTextColor(tapTexts[i],COLOR_ON);
                setTapImg(tapImgs[i],onResId[i]);
                currentIndex=i;
                currentTap=(LinearLayout) v;
                break;
            }
        }
        if (mBottomClickListener!=null) {
            mBottomClickListener.onBottomClick(currentIndex);
        }

    }

     public interface  BottomClickListener{
        public  void onBottomClick(int index);
    }
}
