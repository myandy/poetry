package com.myth.poetrycommon.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.entity.Former;

import java.util.ArrayList;

public class FormerDatabaseHelper {
    private static String TABLE_NAME = "cipai";

    private static SQLiteDatabase getDB() {
        return BaseApplication.instance.getDataDB();
    }

    private static SQLiteDatabase getWritingDB() {
        return BaseApplication.instance.getWritingDB();
    }

    public static ArrayList<Former> getAll() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME
                + " order by id desc", null);
        ArrayList<Former> list = getFormerListFromCursor(cursor);
        Cursor cursor2 = getWritingDB().rawQuery("select * from " + TABLE_NAME
                + " order by id desc", null);
        ArrayList<Former> list2 = getFormerListFromCursor(cursor2);
        list2.addAll(list);
        return list2;
    }

    public static ArrayList<Former> getAllStartByCi() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME
                + " order by id ", null);
        ArrayList<Former> list = getFormerListFromCursor(cursor);
        Cursor cursor2 = getWritingDB().rawQuery("select * from " + TABLE_NAME
                + " order by id desc", null);
        ArrayList<Former> list2 = getFormerListFromCursor(cursor2);
        list2.addAll(list);
        return list2;
    }

    private static ArrayList<Former> getFormerListFromCursor(Cursor cursor) {
        ArrayList<Former> list = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                Former data = new Former();
                data.name = (cursor.getString(cursor.getColumnIndex("name")));
                data.pingze = (cursor.getString(cursor.getColumnIndex("pingze")));
                data.id = (cursor.getInt(cursor.getColumnIndex("id")));
                data.source = (cursor.getString(cursor.getColumnIndex("source")));
                data.type = (cursor.getInt(cursor.getColumnIndex("type")));

                if (cursor.getColumnIndex("wordcount") > 0) {
                    data.wordcount = (cursor.getInt(cursor.getColumnIndex("wordcount")));
                }
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

    public static void delete(Former data) {
        getWritingDB().execSQL("delete from " + TABLE_NAME + " where " + "id" + " = "
                + data.id);
        BaseApplication.instance.needBackup = true;
    }

    public static void update(Former data) {
        getWritingDB().execSQL(" update " + TABLE_NAME + " set pingze= '" + data.pingze + "' , name= '" + data.name + "' , source= '" + data.source
                + "'  where id = " + data.id);
        BaseApplication.instance.needBackup = true;
    }


    public static void add(Former data) {
        getWritingDB().execSQL(" insert into " + TABLE_NAME + " (id,name,pingze,source,type)  values ( "
                + data.id + ",'" + data.name + "','" + data.pingze + "','" + data.source + "'," + data.type + ")");
        BaseApplication.instance.needBackup = true;
    }

    public static Former getFormerById(int id) {
        if (id != -1) {
            Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME + " where id= " + id, null);
            ArrayList<Former> list = getFormerListFromCursor(cursor);
            if (list.size() > 0) {
                return list.get(0);
            }
            Cursor cursor2 = getWritingDB().rawQuery("select * from " + TABLE_NAME + " where id= " + id, null);
            ArrayList<Former> list2 = getFormerListFromCursor(cursor2);
            if (list2.size() > 0) {
                return list2.get(0);
            }
        }
        return new Former();
    }
}
