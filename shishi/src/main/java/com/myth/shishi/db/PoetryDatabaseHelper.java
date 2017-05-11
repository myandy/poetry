package com.myth.shishi.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myth.poetrycommon.BaseApplication;
import com.myth.shishi.entity.Poetry;

import java.util.ArrayList;

public class PoetryDatabaseHelper {
    private static String TABLE_NAME = "t_poetry";

    private static SQLiteDatabase getDB() {
        return BaseApplication.instance.getDataDB();
    }

    public static ArrayList<Poetry> getAll() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME, null);
        return getPoetryListFromCursor(cursor);
    }

    public static ArrayList<Poetry> getRandom200() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME +" order by random() limit 200 ", null);
        return getPoetryListFromCursor(cursor);
    }

    public static ArrayList<Poetry> getAllByAuthor(String author) {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME + " where d_author like '" + author + "'", null);
        return getPoetryListFromCursor(cursor);
    }

    public static ArrayList<Poetry> getAllCollect() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME + " where collect is " + 1 + "", null);
        return getPoetryListFromCursor(cursor);
    }

    public static void updateCollect(int id, boolean isCollect) {
        int collect = isCollect ? 1 : 0;
        getDB().execSQL(" update " + TABLE_NAME + " set collect= " + collect + "  where d_num is " + id);
    }

    public static boolean isCollect(String poetry) {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME + " where d_poetry like '" + poetry + "'", null);
        ArrayList<Poetry> list = getPoetryListFromCursor(cursor);
        if (list != null && list.size() > 0) {
            return list.get(0).collect == 1;
        }
        return false;
    }

    public static boolean isCollect(int id) {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME + " where d_num is " + id, null);
        ArrayList<Poetry> list = getPoetryListFromCursor(cursor);
        if (list != null && list.size() > 0) {
            return list.get(0).collect == 1;
        }
        return false;
    }

    private static ArrayList<Poetry> getPoetryListFromCursor(Cursor cursor) {
        ArrayList<Poetry> list = new ArrayList<Poetry>();
        try {
            while (cursor.moveToNext()) {
                Poetry data = new Poetry();
                data.author = cursor.getString(cursor.getColumnIndex("d_author"));
                data.poetry = cursor.getString(cursor.getColumnIndex("d_poetry"));
                data.intro = cursor.getString(cursor.getColumnIndex("d_intro"));
                data.title = cursor.getString(cursor.getColumnIndex("d_title"));
                data.collect = cursor.getInt(cursor.getColumnIndex("collect"));
                data.id = cursor.getInt(cursor.getColumnIndex("d_num"));
                list.add(data);
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
