package com.myth.poetrycommon.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.myth.poetrycommon.BaseActivity;
import com.myth.poetrycommon.Constant;
import com.myth.poetrycommon.R;

/**
 * Created by AndyMao on 17-5-8.
 */

public class BackupActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup);
        initView();
    }

    private void initView() {
        ((TextView) findViewById(R.id.backup_step)).setText(String.format(getString(R.string.backup_step), Constant.ROOT_DIR, Constant.DBName));
    }
}
