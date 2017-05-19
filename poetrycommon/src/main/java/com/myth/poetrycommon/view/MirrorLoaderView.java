package com.myth.poetrycommon.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class MirrorLoaderView extends View {

    Bitmap mBitmap = null;
    Shader mBitmapShader = null;

    public void setDrawableId(int drawableId) {
        mBitmap = BitmapFactory.decodeResource(getResources(), drawableId);
        invalidate();
    }

    public MirrorLoaderView(Context context) {
        super(context);
    }

    public MirrorLoaderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MirrorLoaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBitmap == null) {
            return;
        }
        Matrix matrix = new Matrix();
        float scale = getWidth() * 1.0f / mBitmap.getWidth();
        matrix.setScale(scale, scale);
        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),
                mBitmap.getHeight(), matrix, true);
        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR,
                Shader.TileMode.MIRROR);

        Paint mPaint = new Paint();
        mPaint.setShader(mBitmapShader);
        canvas.drawRect(new RectF(0, 0, getWidth(),
                getHeight()), mPaint);

    }
}