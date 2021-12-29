package com.myth.shishi;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.Constant;
import com.myth.shishi.ai.PoetryStyle;
import com.myth.shishi.db.DBManager;

public class MyApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDatabase(getApplicationContext());
        Constant.init(getApplicationContext(), "shishi", "sqlite.db");
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

    public static boolean getAIPoetryTabAcrostic() {
        return PreferenceManager.getDefaultSharedPreferences(instance).getBoolean("ai_poetry_tab_acrostic", true);
    }

    public static void setAIPoetryTabAcrostic(boolean acrostic) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(instance).edit();
        edit.putBoolean("ai_poetry_tab_acrostic", acrostic);
        edit.commit();
    }

    public static String getAIPoetryAcrostic() {
        return PreferenceManager.getDefaultSharedPreferences(instance).getString("ai_poetry_acrostic", "智能写诗");
    }

    public static void setAIPoetryAcrostic(String acrostic) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(instance).edit();
        edit.putString("ai_poetry_acrostic", acrostic);
        edit.commit();
    }

    public static String getAIPoetryStyle() {
        return PreferenceManager.getDefaultSharedPreferences(instance).getString("ai_poetry_style", PoetryStyle.INSTANCE.getRandomStyle());
    }

    public static void setAIPoetryStyle(String acrostic) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(instance).edit();
        edit.putString("ai_poetry_style", acrostic);
        edit.commit();
    }
}
