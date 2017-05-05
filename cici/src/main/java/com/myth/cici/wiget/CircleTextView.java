package com.myth.cici.wiget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.poetrycommon.utils.ResizeUtils;


public class CircleTextView extends View {

    private MyApplication myApplication;
    private int mColor;

    private String mText;

    private Context mContext;

    public CircleTextView(Context context, String text, int color) {
        super(context);
        mContext = context;
        myApplication = (MyApplication) ((Activity) mContext).getApplication();
        mColor = color;
        mText = text;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(mColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(ResizeUtils.getInstance().dip2px(32), ResizeUtils.getInstance().dip2px(32),
                ResizeUtils.getInstance().dip2px(30), paint);
        paint.setTextSize(ResizeUtils.getInstance().dip2px(16));
        paint.setTypeface(myApplication.getTypeface());
        paint.setColor(getResources().getColor((R.color.white)));
        canvas.drawText(mText, ResizeUtils.getInstance().dip2px(21), ResizeUtils.getInstance().dip2px(37), paint);
    }

}
