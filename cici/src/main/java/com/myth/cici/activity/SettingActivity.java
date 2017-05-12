package com.myth.cici.activity;

import com.myth.cici.R;
import com.myth.poetrycommon.activity.BaseSettingActivity;

/**
 * Created by AndyMao on 17-5-12.
 */

public class SettingActivity extends BaseSettingActivity {
    @Override
    public Class getAboutClass() {
        return AboutActivity.class;
    }

    @Override
    public String getAboutTitle() {
        return getString(R.string.setting_about);
    }
}
