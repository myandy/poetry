package com.myth.poetrycommon.db;

import android.database.sqlite.SQLiteDatabase;


import android.database.Cursor;
import android.util.Log;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.entity.Former;

import java.util.ArrayList;

public class FormerDatabaseHelper {
    private static String TABLE_NAME = "cipai";

    private static SQLiteDatabase getDB() {
        return BaseApplication.instance.getDataDB();
    }

    public static ArrayList<Former> getAll() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME
                + " order by id", null);
        return getFormerListFromCursor(cursor);
    }

    private static ArrayList<Former> getFormerListFromCursor(Cursor cursor) {
        ArrayList<Former> list = new ArrayList<Former>();
        try {
            while (cursor.moveToNext()) {
                Former data = new Former();
                data.name=(cursor.getString(cursor.getColumnIndex("name")));
                data.pingze=(cursor.getString(cursor.getColumnIndex("yun")));
                data.id=(cursor.getInt(cursor.getColumnIndex("id")));
                data.source=(cursor.getString(cursor.getColumnIndex("source")));
                data.type=(cursor.getInt(cursor.getColumnIndex("type")));
                data.wordcount=(cursor.getInt(cursor.getColumnIndex("wordcount")));
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

    public static void update(Former data) {
        getDB().execSQL(" update " + TABLE_NAME + " set yun= '" + data.pingze
                + "'  where id = " + data.id);
    }


    public static void add(Former data) {
        getDB().execSQL(" insert into " + TABLE_NAME + " (name,yun)  values ( '"
                + data.name + "','" + data.pingze + "')");
    }

    public static Former getFormerById(int id) {
        if(id!=-1){
            Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME + " where id= " + id, null);
            ArrayList<Former> list = getFormerListFromCursor(cursor);
            if (list.size() > 0) {
                return list.get(0);
            }
        }
        return new Former();
    }
}
