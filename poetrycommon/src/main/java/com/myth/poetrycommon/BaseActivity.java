package com.myth.poetrycommon;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.myth.poetrycommon.utils.ResizeUtils;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends Activity {

    protected Activity mActivity;

    /**
     * 内容区
     */
    protected FrameLayout mContentLayout = null;

    /**
     * 底部区
     */
    protected FrameLayout mBottomLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 隐藏应用程序的标题栏，即当前activity的label
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.setContentView(R.layout.activity_base);
        mActivity = this;
        mBottomLayout = (FrameLayout) findViewById(R.id.bottom_layout);
        findViewById(R.id.bottom_left).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mContentLayout = (FrameLayout) findViewById(R.id.content_layout);
        setBottomGone();
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    protected ImageView getBottomLeftView() {
        return (ImageView) findViewById(R.id.bottom_left);
    }

    public void setBottomGone() {
        mBottomLayout.setVisibility(View.GONE);
    }

    public void setBottomVisible() {
        mBottomLayout.setVisibility(View.VISIBLE);
    }

    protected void addBottomRightView(View view, LayoutParams lps) {
        ((ViewGroup) findViewById(R.id.bottom_right)).addView(view, lps);
    }

    protected void addBottomRightView(View view) {
        ((ViewGroup) findViewById(R.id.bottom_right)).addView(view, new LayoutParams(ResizeUtils.getInstance().dip2px(50),
                ResizeUtils.getInstance().dip2px(50)));
    }


    protected void addBottomCenterView(View view, LayoutParams lps) {
        ((ViewGroup) findViewById(R.id.bottom_center)).addView(view, lps);
    }

    protected void addBottomCenterView(View view) {
        ((ViewGroup) findViewById(R.id.bottom_center)).addView(view);
    }

    /**
     * 通过layout名称构建视图
     *
     * @param
     * @see [类、类#方法、类#成员]
     */
    public void setContentView(int layoutId) {
        getLayoutInflater().inflate(layoutId, mContentLayout);
    }

}
