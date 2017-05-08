package com.myth.shishi;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.Constant;
import com.myth.shishi.db.DBManager;
import com.umeng.comm.core.sdkmanager.LocationSDKManager;
import com.umeng.community.location.DefaultLocationImpl;
import com.umeng.socialize.PlatformConfig;

public class MyApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDatabase(getApplicationContext());
        LocationSDKManager.getInstance().addAndUse(new DefaultLocationImpl());
        Constant.init("shishi", "sqlite.db");

        PlatformConfig.setWeixin("wx96110a1e3af63a39", "c60e3d3ff109a5d17013df272df99199");
        PlatformConfig.setSinaWeibo("944955993", "4b6e97140e9417bec7b225bc4477262d");
        PlatformConfig.setQQZone("1104581811", "KEYlj8gnlPCd4j4vA22");
    }

    @Override
    protected void openDB() {
        dataDB = DBManager.getNewDatabase();
        writingDB = DBManager.getDatabase();
    }

    public static int getDefaultDynasty(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(
                "dynasty", 0);
    }

    public static void setDefaultDynasty(Context context, int dynasty) {
        Editor edit = PreferenceManager.getDefaultSharedPreferences(context)
                .edit();
        edit.putInt("dynasty", dynasty);
        edit.commit();
    }
}
