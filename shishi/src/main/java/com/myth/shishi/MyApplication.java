package com.myth.shishi;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.Constant;
import com.myth.shishi.db.DBManager;

public class MyApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDatabase(getApplicationContext());
        Constant.init("shishi", "sqlite.db");
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
