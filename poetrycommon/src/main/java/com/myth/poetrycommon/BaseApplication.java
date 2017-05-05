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
import com.umeng.socialize.PlatformConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by AndyMao on 17-4-18.
 */

public class BaseApplication extends Application {

    public static boolean needBackup;
    public List<ColorEntity> colorList;

    private HashMap<Integer, ColorEntity> colorMap;

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

    protected void openDB() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setTypeface(getApplicationContext(), getDefaultTypeface(this));

        PlatformConfig.setWeixin("wx96110a1e3af63a39", "c60e3d3ff109a5d17013df272df99199");
        PlatformConfig.setSinaWeibo("2655542749", "d3c6e64eb912183bdf2ecc299ddfe3a7");
        PlatformConfig.setQQZone("1104396282", "KEYwA42NSJxWzHJjHRe");

        ResizeUtils.getInstance().init(this);


        if (getResources().getConfiguration().locale.getCountry().equals("TW") || getResources().getConfiguration().locale.getCountry().equals("hk")) {
            defaultTypeface = 1;
        }
    }

    public int getColorByPos(int pos) {
        ColorEntity colorEntity = getColorEntityByPos(pos);
        int color;
        if (colorEntity != null) {
            color = Color.rgb(colorEntity.getRed(), colorEntity.getGreen(), colorEntity.getBlue());
        } else {
            color = Color.rgb(0, 0, 0);
        }
        return color;
    }

    public ColorEntity getColorEntityByPos(int pos) {
        if (colorList == null) {
            colorList = ColorDatabaseHelper.getAll();
        }
        pos--;
        if (pos >= 0 && pos < colorList.size()) {
            return colorList.get(pos);
        } else {
            return null;
        }
    }


    public  int getRandomColor() {
        if (colorList == null) {
            colorList = ColorDatabaseHelper.getAll();
        }
        return colorList.get(new Random().nextInt(colorList.size())).toColor();
    }


    public int getColorById(int id) {
        ColorEntity colorEntity = getColorEntityById(id);
        int color;
        if (colorEntity != null) {
            color = Color.rgb(colorEntity.getRed(), colorEntity.getGreen(), colorEntity.getBlue());
        } else {
            color = Color.rgb(0, 0, 0);
        }
        return color;
    }

    public ColorEntity getColorEntityById(int id) {
        if (colorMap == null) {
            colorMap = new HashMap();
            ArrayList<ColorEntity> colorList = ColorDatabaseHelper.getAll();
            for (ColorEntity color : colorList) {
                colorMap.put(color.getId(), color);
            }
        }
        return colorMap.get(id);
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

    public static int[] bgimgList = {R.drawable.dust, R.drawable.bg001, R.drawable.bg002, R.drawable.bg004,
            R.drawable.bg006, R.drawable.bg007, R.drawable.bg011, R.drawable.bg013, R.drawable.bg072, R.drawable.bg084,
            R.drawable.bg096, R.drawable.bg118};

    public static int[] bgSmallimgList = {R.drawable.dust, R.drawable.bg001_small, R.drawable.bg002_small,
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
}
