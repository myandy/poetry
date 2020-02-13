package com.myth.cici.db;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myth.cici.entity.Ci;

public class CiDatabaseHelper
{
    private static final String TABLE_NAME = "example";

    public static ArrayList<Ci> getCiByCipaiId(int cipaiId)
    {
        SQLiteDatabase db = DBManager.getNewDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where ci_id=" + cipaiId, null);
        return getCiListFromCursor(cursor);
    }

    private static ArrayList<Ci> getCiListFromCursor(Cursor cursor)
    {
        ArrayList<Ci> list = new ArrayList<Ci>();
        while (cursor.moveToNext())
        {
            Ci ci = new Ci();
            ci.setId(cursor.getInt(cursor.getColumnIndex("id")));
            ci.setCi_id(cursor.getInt(cursor.getColumnIndex("ci_id")));
            ci.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
            ci.setNote(cursor.getString(cursor.getColumnIndex("note")));
            ci.setText(cursor.getString(cursor.getColumnIndex("text")));
            ci.collect = (cursor.getInt(cursor.getColumnIndex("collect")));
            ci.cipai_name = (cursor.getString(cursor.getColumnIndex("cipai_name")));
            list.add(ci);
        }
        return list;
    }

    public static ArrayList<Ci> getAllCi()
    {
        SQLiteDatabase db = DBManager.getNewDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME, null);
        return getCiListFromCursor(cursor);
    }

    public static ArrayList<Ci> getAllCollect() {
        Cursor cursor = DBManager.getNewDatabase().rawQuery("select * from " + TABLE_NAME + " where collect is " + 1 + "", null);
        return getCiListFromCursor(cursor);
    }

    public static void updateCollect(int id, boolean isCollect) {
        int collect = isCollect ? 1 : 0;
        DBManager.getNewDatabase().execSQL(" update " + TABLE_NAME + " set collect= " + collect + "  where id is " + id);
    }

    public static boolean isCollect(int id) {
        Cursor cursor = DBManager.getNewDatabase().rawQuery("select * from " + TABLE_NAME + " where id is " + id, null);
        ArrayList<Ci> list = getCiListFromCursor(cursor);
        if (list != null && list.size() > 0) {
            return list.get(0).collect == 1;
        }
        return false;
    }

    public static Ci getCiById(int id) {
        Cursor cursor = DBManager.getNewDatabase().rawQuery("select * from " + TABLE_NAME + " where id is " + id, null);
        ArrayList<Ci> list = getCiListFromCursor(cursor);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}
