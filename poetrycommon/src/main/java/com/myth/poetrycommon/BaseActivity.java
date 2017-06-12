package com.myth.poetrycommon;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.myth.poetrycommon.utils.ResizeUtils;
import com.umeng.analytics.MobclickAgent;

public class BaseActivity extends Activity {

    protected String TAG;

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
        TAG = getClass().getSimpleName();
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

        requestPermission();
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

    public boolean isBottomVisible() {
        return mBottomLayout.getVisibility() == View.VISIBLE;
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

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0;

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(mActivity, "您没有授权文件访问权限，这可能会使应用运行产生问题，请在设置中打开授权", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
