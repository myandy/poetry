package com.myth.cici.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myth.cici.entity.Cipai;
import com.myth.poetrycommon.BaseApplication;

import java.util.ArrayList;

public class CipaiDatabaseHelper {
    private static final String TABLE_NAME = "cipai";

    private static SQLiteDatabase getDB() {
        return BaseApplication.instance.getDataDB();
    }

    public static ArrayList<Cipai> getAllShowCipai() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME
                + " where type=0 and parent_id is null order by cast( color_id as int)", null);
        return getCipaiListFromCursor(cursor);
    }

    public static ArrayList<Cipai> getAllCipaiByWordCount() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME + " where type=0 and parent_id is null order by wordcount desc",
                null);
        return getCipaiListFromCursor(cursor);
    }

    public static ArrayList<Cipai> getAllCipai() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME, null);
        return getCipaiListFromCursor(cursor);
    }

    public static Cipai getCipaiById(int id) {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME + " where id= " + id, null);
        ArrayList<Cipai> list = getCipaiListFromCursor(cursor);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return new Cipai();
        }
    }

    public static ArrayList<Cipai> getParentCipaiById(int id) {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME + " where id= " + id + "  or parent_id= " + id, null);
        return getCipaiListFromCursor(cursor);

    }

    private static ArrayList<Cipai> getCipaiListFromCursor(Cursor cursor) {
        ArrayList<Cipai> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Cipai cipai = new Cipai();
            cipai.id = (cursor.getInt(cursor.getColumnIndex("id")));
            cipai.alias = (cursor.getString(cursor.getColumnIndex("alias")));
            cipai.enname = (cursor.getString(cursor.getColumnIndex("enname")));
            cipai.name = (cursor.getString(cursor.getColumnIndex("name")));
            cipai.parent_id = (cursor.getInt(cursor.getColumnIndex("parent_id")));
            cipai.pingze = (cursor.getString(cursor.getColumnIndex("pingze")));
            cipai.source = (cursor.getString(cursor.getColumnIndex("source")));
            cipai.tone_type = (cursor.getInt(cursor.getColumnIndex("tone_type")));
            cipai.wordcount = (cursor.getInt(cursor.getColumnIndex("wordcount")));
            list.add(cipai);
        }
        System.out.println("myth" + list.size());
        return list;
    }

}
