package com.myth.poetrycommon;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;

import com.myth.poetrycommon.db.ColorDatabaseHelper;
import com.myth.poetrycommon.entity.ColorEntity;
import com.myth.poetrycommon.utils.ResizeUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by AndyMao on 17-4-18.
 */

public abstract class BaseApplication extends Application {

    public static boolean needBackup;
    public List<ColorEntity> colorList;
    private Typeface typeface;

    public static final String TypefaceString[] = {"简体", "繁体", "系统字体"};

    public static BaseApplication instance;

    private static int defaultTypeface = 0;

    protected SQLiteDatabase dataDB;

    protected SQLiteDatabase writingDB;

    public SQLiteDatabase getWritingDB() {
        if (writingDB == null) {
            openDB();
        }
        return writingDB;
    }

    public SQLiteDatabase getDataDB() {
        if (dataDB == null) {
            openDB();
        }
        return dataDB;
    }

    protected abstract void openDB();

    public boolean isCiApp() {
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ResizeUtils.getInstance().init(this);
        if (getResources().getConfiguration().locale.getCountry().equals("TW") || getResources().getConfiguration().locale.getCountry().equals("hk")) {
            defaultTypeface = 1;
        }
        if (isFirstStart()) {
            setHasStart();
        }

//        LeakCanary.install(this);
    }

    public int getColorByPos(int pos) {
        ColorEntity colorEntity = getColorEntityByPos(pos);
        int color;
        if (colorEntity != null) {
            color = colorEntity.toColor();
        } else {
            color = Color.rgb(0, 0, 0);
        }
        return color;
    }

    private ColorEntity getColorEntityByPos(int pos) {
        if (colorList == null) {
            colorList = ColorDatabaseHelper.getAll();
        }
        if (pos >= 0 && pos < colorList.size()) {
            return colorList.get(pos);
        } else {
            return null;
        }
    }


    public int getRandomColor() {
        if (colorList == null) {
            colorList = ColorDatabaseHelper.getAll();
        }
        return colorList.get(new Random().nextInt(colorList.size() - 1) + 1).toColor();
    }

    public void setTypeface(Context context, int type) {
        if (type == 0) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fzqkyuesong.TTF");
        } else if (type == 1) {
            typeface = Typeface.createFromAsset(context.getAssets(), "fzsongkebenxiukai_fanti.ttf");
        } else {
            typeface = null;
        }
    }

    public static int[] bgImgList = {R.drawable.dust, R.drawable.bg001, R.drawable.bg002, R.drawable.bg004,
            R.drawable.bg006, R.drawable.bg007, R.drawable.bg011, R.drawable.bg013, R.drawable.bg072, R.drawable.bg084,
            R.drawable.bg096, R.drawable.bg118};

    public static int[] bgSmallImgList = {R.drawable.dust_small, R.drawable.bg001_small, R.drawable.bg002_small,
            R.drawable.bg004_small, R.drawable.bg006_small, R.drawable.bg007_small, R.drawable.bg011_small,
            R.drawable.bg013_small, R.drawable.bg072_small, R.drawable.bg084_small, R.drawable.bg096_small,
            R.drawable.bg118_small};


    public static boolean getDefaultListType(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("listType", true);
    }

    public static void setDefaultListType(Context context, boolean bool) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putBoolean("listType", bool);
        edit.commit();
    }

    public static boolean getCheckAble(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("check", true);
    }

    public static void setCheckAble(Context context, boolean bool) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putBoolean("check", bool);
        edit.commit();
    }

    public static int getDefaultTextSize(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("size", 18);
    }

    public static void setDefaultTextSize(Context context, int size) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putInt("size", size);
        edit.commit();
    }

    public static int getDefaultShareSize(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("share_size", 18);
    }

    public static void setDefaultShareSize(Context context, int size) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putInt("share_size", size);
        edit.commit();
    }

    public static boolean getDefaultShareGravity(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("share_gravity", true);
    }

    public static void setDefaultShareGravity(Context context, boolean iscenter) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putBoolean("share_gravity", iscenter);
        edit.commit();
    }

    public static boolean getDefaultShareAuthor(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("share_author", true);
    }

    public static void setDefaultShareAuthor(Context context, boolean iscenter) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putBoolean("share_author", iscenter);
        edit.commit();
    }

    public static int getDefaultSharePadding(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("share_padding", 20);
    }

    public static void setDefaultSharePadding(Context context, int size) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putInt("share_padding", size);
        edit.commit();
    }

    public static int getDefaultShareColor(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt("share_color", 0);
    }

    public static void setDefaultShareColor(Context context, int size) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        edit.putInt("share_color", size);
        edit.commit();
    }

    public static String getDefaultUserName() {
        return PreferenceManager.getDefaultSharedPreferences(instance).getString("username", "");
    }

    public static void setDefaultUserName(String size) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(instance).edit();
        edit.putString("username", size);
        edit.commit();
    }

    public static void setDefaultTypeface(Context context, int i) {
        if (i < TypefaceString.length && i >= 0) {
            SharedPreferences.Editor edit = PreferenceManager
                    .getDefaultSharedPreferences(context).edit();
            edit.putInt("typeface", i);
            edit.commit();
        }
    }


    public static int getDefaultTypeface(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(
                "typeface", defaultTypeface);
    }

    public Typeface getTypeface() {
        if (typeface == null && getDefaultTypeface(this) != 2) {
            setTypeface(getApplicationContext(), getDefaultTypeface(this));
        }
        return typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public static boolean isFirstStart() {
        return PreferenceManager.getDefaultSharedPreferences(instance).getBoolean("first_start", true);
    }

    public static void setHasStart() {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(instance).edit();
        edit.putBoolean("first_start", true);
        edit.commit();
    }
}
