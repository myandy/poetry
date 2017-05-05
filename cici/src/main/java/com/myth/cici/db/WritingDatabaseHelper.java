//package com.myth.cici.db;
//
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//import com.myth.poetrycommon.entity.Writing;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//public class WritingDatabaseHelper {
//    private static final String TABLE_NAME = "writing";
//
//    public static ArrayList<Writing> getAllWriting() {
//        SQLiteDatabase db = DBManager.getDatabase();
//        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + "  order by update_dt ", null);
//        return getWritingFromCursor(cursor);
//    }
//
//    public static synchronized void saveWriting(Writing writing) {
//        deleteWriting(writing);
//        generateText(writing);
//        SQLiteDatabase db = DBManager.getDatabase();
//        String sqlStr = "insert into " + TABLE_NAME + " ( id,bgimg,ci_id,create_dt,text,update_dt) values ( "
//                + "?,?,?,?,?,?)";
//        db.execSQL(sqlStr,
//                new String[]{writing.getId() + "", writing.bgimg, writing.getCi_id() + "",
//                        writing.getCreate_dt() + "", writing.text, System.currentTimeMillis() + ""});
//        BackupTask.needBackup = true;
//    }
//
//    public static void deleteWriting(Writing writing) {
//        SQLiteDatabase db = DBManager.getDatabase();
//
//        db.execSQL("delete from " + TABLE_NAME + " where " + "id" + " = " + writing.getId());
//        BackupTask.needBackup = true;
//    }
//
//    private static ArrayList<Writing> getWritingFromCursor(Cursor cursor) {
//        ArrayList<Writing> list = new ArrayList<Writing>();
//        while (cursor.moveToNext()) {
//            Writing data = new Writing();
//            data.setId(cursor.getInt(cursor.getColumnIndex("id")));
//            data.setBgimg(cursor.getString(cursor.getColumnIndex("bgimg")));
//            data.setCi_id(cursor.getInt(cursor.getColumnIndex("ci_id")));
//            data.setCreate_dt(cursor.getLong(cursor.getColumnIndex("create_dt")));
//            data.setText(cursor.getString(cursor.getColumnIndex("text")));
//            data.setUpdate_dt(cursor.getLong(cursor.getColumnIndex("update_dt")));
//            parseText(data);
//            list.add(data);
//        }
//        return list;
//    }
//
//    private static void parseText(Writing writing) {
//        try {
//            JSONObject jb = new JSONObject(writing.text);
//            writing.author = jb.optString("author");
//            writing.title = jb.optString("title");
//            writing.desc = jb.optString("desc");
//            writing.content = jb.optString("content");
//        } catch (JSONException e) {
//            writing.content = writing.text;
//            e.printStackTrace();
//        }
//    }
//
//    public static void generateText(Writing writing) {
//        try {
//            JSONObject jb = new JSONObject();
//            jb.put("author", writing.author);
//            jb.put("title", writing.title);
//            if (writing.desc == null) {
//                writing.desc = "";
//            }
//            if (writing.content == null) {
//                writing.content = "";
//            }
//            jb.put("desc", writing.desc);
//            jb.put("content", writing.content);
//            writing.text = jb.toString();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
