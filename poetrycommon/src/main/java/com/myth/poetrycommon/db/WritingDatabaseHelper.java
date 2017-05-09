package com.myth.poetrycommon.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.entity.Writing;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class WritingDatabaseHelper {
    private static String TABLE_NAME = "writing";

    private static SQLiteDatabase getDB() {
        return BaseApplication.instance.getWritingDB();
    }

    public static ArrayList<Writing> getAllWriting() {
        Cursor cursor = getDB().rawQuery("select * from " + TABLE_NAME
                + "  order by update_dt ", null);
        return getWritingFromCursor(cursor);
    }

    public static synchronized void saveWriting(Writing writing) {
        deleteWriting(writing);
        generateText(writing);
        String sqlStr = "insert into "
                + TABLE_NAME
                + " (id,bgimg,create_dt,text,update_dt) values ( "
                + "?,?,?,?,?)";
        getDB().execSQL(
                sqlStr,
                new String[]{writing.id + "",
                        writing.bgimg,
                        writing.create_dt + "", writing.text,
                        System.currentTimeMillis() + ""});

        BaseApplication.instance.needBackup = true;
    }

    public static void deleteWriting(Writing writing) {

        getDB().execSQL("delete from " + TABLE_NAME + " where " + "id" + " = "
                + writing.id);

        BaseApplication.instance.needBackup = true;
    }

    private static ArrayList<Writing> getWritingFromCursor(Cursor cursor) {
        ArrayList<Writing> list = new ArrayList<Writing>();
        while (cursor.moveToNext()) {
            Writing data = new Writing();
            data.id = (cursor.getInt(cursor.getColumnIndex("id")));

            int titleId = cursor
                    .getColumnIndex("title");
            String titleString = "";
            if (titleId != -1) {
                titleString = cursor.getString(titleId);
            }
            data.title = (titleString);
            data.bgimg = (cursor.getString(cursor.getColumnIndex("bgimg")));

            int ci_id_index = cursor.getColumnIndex("ci_id");
            if (ci_id_index != -1) {
                data.formerId = cursor.getInt(ci_id_index);
            }
            data.create_dt = (cursor.getLong(cursor.getColumnIndex("create_dt")));
            String contentString = cursor.getString(cursor
                    .getColumnIndex("text"));
            if (contentString == null) {
                contentString = "";
            }
            data.text = (contentString);
            data.update_dt = (cursor.getLong(cursor.getColumnIndex("update_dt")));
            parseText(data);
            list.add(data);
        }
        return list;
    }

    private static void parseText(Writing writing) {
        try {
            JSONObject jb = new JSONObject(writing.text);
            writing.author = jb.optString("author");
            writing.desc = jb.optString("desc");
            writing.content = jb.optString("content");
            writing.title = jb.optString("title");
            writing.formerId = jb.optInt("ci_id");
        } catch (JSONException e) {
            writing.content = writing.text;
            e.printStackTrace();
        }
    }

    public static void generateText(Writing writing) {
        try {
            JSONObject jb = new JSONObject();
            jb.put("author", writing.author);
            if (writing.desc == null) {
                writing.desc = "";
            }
            if (writing.content == null) {
                writing.content = "";
            }
            jb.put("desc", writing.desc);
            jb.put("content", writing.content);
            jb.put("title", writing.title);
            jb.put("ci_id", writing.formerId);
            writing.text = jb.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
