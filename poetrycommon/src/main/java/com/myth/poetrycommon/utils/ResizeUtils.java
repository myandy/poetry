package com.myth.poetrycommon.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ResizeUtils {
    /**
     * 宽720
     */
    private static final int W720 = 720;

    public static float sysWidth;

    private float density;

    public static int resize(float origin) {
        return (int) (origin * sysWidth / W720);
    }


    private static ResizeUtils instance;

    public static ResizeUtils getInstance() {
        if (instance == null) {
            synchronized (ResizeUtils.class) {
                if (instance == null) {
                    instance = new ResizeUtils();
                }
            }
        }
        return instance;
    }


    public void init(Context context) {
        if (sysWidth == 0) {
            sysWidth = OthersUtils.getDisplayWidth(context);
        }
        density = context.getResources().getDisplayMetrics().density;
    }

    public int dip2px(double dipValue) {
        return (int) (dipValue * density + 0.5f);
    }

    public int px2dip(double pxValue) {
        return (int) (pxValue / density + 0.5f);
    }


    public void layoutSquareView(View itemContainer) {
        ViewGroup.LayoutParams params = itemContainer.getLayoutParams();
        params.width = resize(540);
        params.height = resize(540);
        itemContainer.setLayoutParams(params);
    }

    public void layoutSquareView(View itemContainer, int width, int height) {
        ViewGroup.LayoutParams params = itemContainer.getLayoutParams();
        params.width = resize(width);
        params.height = resize(height);
        itemContainer.setLayoutParams(params);
    }
}
