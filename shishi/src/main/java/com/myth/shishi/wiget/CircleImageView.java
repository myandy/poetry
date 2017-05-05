package com.myth.shishi.wiget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.myth.poetrycommon.utils.ResizeUtils;


public class CircleImageView extends View
{

    private int mColor;

    private Context mContext;
    
    private int image;

    public int getmColor()
    {
        return mColor;
    }

    public void setmColor(int color)
    {
        this.mColor = color;
        invalidate();
    }

    public CircleImageView(Context context, int color,int image)
    {
        super(context);
        this.mColor = color;
        mContext = context;
        this.image=image;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(mColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(ResizeUtils.getInstance().dip2px(48), ResizeUtils.getInstance().dip2px(48),
                ResizeUtils.getInstance().dip2px(46), paint);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), image),
                ResizeUtils.getInstance().dip2px(16), ResizeUtils.getInstance().dip2px(16), paint);
    }

}
