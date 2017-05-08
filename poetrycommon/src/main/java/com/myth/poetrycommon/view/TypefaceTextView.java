package com.myth.poetrycommon.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.myth.poetrycommon.BaseApplication;

/**
 * Created by AndyMao on 17-5-8.
 */

public class TypefaceTextView extends TextView {
    public TypefaceTextView(Context context) {
        super(context);
        setTypeface(BaseApplication.instance.getTypeface());
    }

    public TypefaceTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(BaseApplication.instance.getTypeface());
    }

    public TypefaceTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(BaseApplication.instance.getTypeface());
    }

}
