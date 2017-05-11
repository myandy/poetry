package com.myth.shishi.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myth.poetrycommon.BaseApplication;
import com.myth.shishi.entity.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorDatabaseHelper {
    private static String TABLE_NAME = "t_author";

    private static SQLiteDatabase getDB() {
        return BaseApplication.instance.getDataDB();
    }

    public static ArrayList<Author> getAll() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME + " order by d_num ", null);
        return getAuthorListFromCursor(cursor);
    }

    private static ArrayList<Author> getAuthorListFromCursor(Cursor cursor) {
        ArrayList<Author> list = new ArrayList<Author>();
        try {
            while (cursor.moveToNext()) {
                Author author = new Author();
                author.dynasty = cursor.getString(cursor.getColumnIndex("d_dynasty"));
                author.author = cursor.getString(cursor.getColumnIndex("d_author"));
                author.intro = cursor.getString(cursor.getColumnIndex("d_intro"));
                author.p_num = cursor.getInt(cursor.getColumnIndex("p_num"));
                author.en_name = (cursor.getString(cursor.getColumnIndex("en_name")));
                list.add(author);
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

    public static Author getAuthorByName(String name) {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME + " where d_author like '" + name + "'", null);
        List<Author> list = getAuthorListFromCursor(cursor);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return new Author();
    }

    public static ArrayList<Author> getAllAuthorByPNum() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME + " order by p_num desc", null);
        return getAuthorListFromCursor(cursor);
    }

    public static ArrayList<Author> getAll(int dynasty) {
        if (dynasty == 0) {
            return getAll();
        }
        SQLiteDatabase db = DBManager.getNewDatabase();
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where d_dynasty like '"
                + DynastyDatabaseHelper.dynastyArray[dynasty] + "' " + "order by d_num ", null);
        return getAuthorListFromCursor(cursor);
    }

    public static ArrayList<Author> getAllAuthorByPNum(int dynasty) {
        if (dynasty == 0) {
            return getAllAuthorByPNum();
        }
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME + " where d_dynasty like '"
                + DynastyDatabaseHelper.dynastyArray[dynasty] + "' " + " order by p_num desc", null);
        return getAuthorListFromCursor(cursor);
    }

}
