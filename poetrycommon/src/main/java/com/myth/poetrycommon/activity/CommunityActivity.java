package com.myth.poetrycommon.activity;

import android.app.Activity;
import android.os.Bundle;

import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.impl.CommunityFactory;

/**
 * Created by AndyMao on 17-5-11.
 */

public class CommunityActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommunitySDK mCommSDK = CommunityFactory.getCommSDK(this);
        mCommSDK.openCommunity(this);
        finish();
    }
}
