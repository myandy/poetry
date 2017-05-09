package com.myth.poetrycommon.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.myth.poetrycommon.utils.ResizeUtils;


public class CircleImageView extends View {

    private int mColor;

    private int mImage;

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
        invalidate();
    }

    public CircleImageView(Context context, int color, int image) {
        super(context);
        this.mColor = color;
        this.mImage = image;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(mColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(ResizeUtils.getInstance().dip2px(50), ResizeUtils.getInstance().dip2px(38),
                ResizeUtils.getInstance().dip2px(36), paint);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), mImage),
                ResizeUtils.getInstance().dip2px(22), ResizeUtils.getInstance().dip2px(10), paint);
    }

}
