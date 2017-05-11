package com.myth.poetrycommon.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.entity.ColorEntity;

import java.util.ArrayList;

public class ColorDatabaseHelper {
    private static String TABLE_NAME = "color";

    private static SQLiteDatabase getDB(){
        return BaseApplication.instance.getDataDB();
    }

    public static ArrayList<ColorEntity> getAll() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME +" order by id", null);
        return getColorListFromCursor(cursor);
    }

    public static ArrayList<ColorEntity> getAllShow() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME +" where displayidx > 101 order by displayidx desc", null);
        return getColorListFromCursor(cursor);
    }

    private static ArrayList<ColorEntity> getColorListFromCursor(Cursor cursor) {
        ArrayList<ColorEntity> list = new ArrayList<ColorEntity>();
        try {
            while (cursor.moveToNext()) {
                ColorEntity color = new ColorEntity();
                color.setId(cursor.getInt(cursor.getColumnIndex("id")));
                color.setRed(cursor.getInt(cursor.getColumnIndex("red")));
                color.setGreen(cursor.getInt(cursor.getColumnIndex("green")));
                color.setBlue(cursor.getInt(cursor.getColumnIndex("blue")));
                color.setName(cursor.getString(cursor.getColumnIndex("name")));
                list.add(color);
            }
        } catch (Exception e) {
            Log.e("myth", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

}
