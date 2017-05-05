package com.myth.poetrycommon.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.myth.poetrycommon.R;
import com.myth.poetrycommon.utils.ResizeUtils;


public class PingzeView extends View {

    private static final int TYPE_PING = 1;

    private static final int TYPE_ZHONG = 2;

    private static final int TYPE_ZE = 3;

    private static final int TYPE_PING_YUN = 4;

    private static final int TYPE_ZE_YUN = 6;

    private static final int TYPE_PING_YUN_CAN = 7;

    private static final int TYPE_ZE_YUN_CAN = 9;

    private int mType;

    private Context mContext;

    private Paint paint;

    public PingzeView(Context context, int type) {
        super(context);
        mType = type;
        mContext = context;
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mType == TYPE_PING) {
            paint.setColor(mContext.getResources().getColor(R.color.yun_white));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(ResizeUtils.getInstance().dip2px(1));
            canvas.drawCircle(ResizeUtils.getInstance().dip2px(10), ResizeUtils.getInstance().dip2px(10),
                    ResizeUtils.getInstance().dip2px(5), paint);
        } else if (mType == TYPE_ZE) {
            paint.setColor(mContext.getResources().getColor(R.color.black_light));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(ResizeUtils.getInstance().dip2px(10), ResizeUtils.getInstance().dip2px(10),
                    ResizeUtils.getInstance().dip2px(5), paint);
        } else if (mType == TYPE_ZHONG) {
            paint.setColor(mContext.getResources().getColor(R.color.yun_white));
            paint.setStrokeWidth(ResizeUtils.getInstance().dip2px(1));
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(ResizeUtils.getInstance().dip2px(10), ResizeUtils.getInstance().dip2px(10),
                    ResizeUtils.getInstance().dip2px(3), paint);
            canvas.drawCircle(ResizeUtils.getInstance().dip2px(10), ResizeUtils.getInstance().dip2px(10),
                    ResizeUtils.getInstance().dip2px(6), paint);
        } else if (mType == TYPE_ZE_YUN) {
            paint.setColor(mContext.getResources().getColor(R.color.pingze_red));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(ResizeUtils.getInstance().dip2px(10), ResizeUtils.getInstance().dip2px(10),
                    ResizeUtils.getInstance().dip2px(5), paint);
        } else if (mType == TYPE_PING_YUN) {
            paint.setColor(mContext.getResources().getColor(R.color.pingze_blue));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(ResizeUtils.getInstance().dip2px(10), ResizeUtils.getInstance().dip2px(10),
                    ResizeUtils.getInstance().dip2px(5), paint);
        } else if (mType == TYPE_PING_YUN_CAN) {
            paint.setColor(mContext.getResources().getColor(R.color.pingze_green));
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(ResizeUtils.getInstance().dip2px(1));
            canvas.drawCircle(ResizeUtils.getInstance().dip2px(10), ResizeUtils.getInstance().dip2px(10),
                    ResizeUtils.getInstance().dip2px(5), paint);
        } else if (mType == TYPE_ZE_YUN_CAN) {
            paint.setColor(mContext.getResources().getColor(R.color.pingze_green));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(ResizeUtils.getInstance().dip2px(10), ResizeUtils.getInstance().dip2px(10),
                    ResizeUtils.getInstance().dip2px(5), paint);
        }
    }

}
