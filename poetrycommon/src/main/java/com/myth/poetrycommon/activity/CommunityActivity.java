package com.myth.poetrycommon.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.myth.poetrycommon.BaseActivity;
import com.myth.poetrycommon.Constant;
import com.myth.poetrycommon.R;
import com.myth.poetrycommon.utils.FileUtils;

import java.io.File;

public class CommunityActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);
        setBottomGone();
        initView();
    }

    private void initView() {
        findViewById(R.id.iv_community).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mActivity).setItems(new String[]{"保存图片"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    String filePath = saveImage();
                                    if (!TextUtils.isEmpty(filePath)) {
                                        Toast.makeText(mActivity, "图片已保存在：" + filePath, Toast.LENGTH_SHORT).show();
                                    }
                                }
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    private String saveImage() {
        String filename = "community.jpg";
        File file = new File(Constant.SHARE_DIR, filename);
        FileUtils.saveBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pic_community), file);
        FileUtils.updateMediaFile(mActivity, file.getAbsolutePath());
        return file.getAbsolutePath();
    }
}
