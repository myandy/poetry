package com.myth.shishi.activity;

import com.myth.poetrycommon.activity.BaseSettingActivity;
import com.myth.shishi.R;

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
