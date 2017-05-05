package com.myth.shishi;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.myth.poetrycommon.*;
import com.myth.shishi.db.DBManager;
import com.umeng.comm.core.sdkmanager.LocationSDKManager;
import com.umeng.community.location.DefaultLocationImpl;

public class MyApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDatabase(getApplicationContext());
        LocationSDKManager.getInstance().addAndUse(new DefaultLocationImpl());
        com.myth.poetrycommon.Constant.init("shishi");
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
