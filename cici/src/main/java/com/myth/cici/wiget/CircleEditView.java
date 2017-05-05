package com.myth.cici.wiget;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.myth.cici.R;
import com.myth.poetrycommon.utils.ResizeUtils;


public class CircleEditView extends View
{

    private int mColor;

    private Context mContext;

    public int getColor()
    {
        return mColor;
    }

    public void setColor(int mColor)
    {
        this.mColor = mColor;
        invalidate();
    }

    public CircleEditView(Context context)
    {
        super(context);
        mContext = context;
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(mColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(ResizeUtils.getInstance().dip2px(50), ResizeUtils.getInstance().dip2px(38),
                ResizeUtils.getInstance().dip2px(36), paint);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.edit_white),
                ResizeUtils.getInstance().dip2px(22), ResizeUtils.getInstance().dip2px(10), paint);
    }

}
