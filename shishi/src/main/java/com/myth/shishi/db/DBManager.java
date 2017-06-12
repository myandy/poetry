package com.myth.shishi.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.myth.shishi.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBManager {
    private final static int BUFFER_SIZE = 400000;

    public static final String DB_NAME = "sqlite.db"; // 保存的数据库文件名

    public static final String DB_NEW_NAME = "shi_new.db"; // 新的数据库文件名，只保存内容数据

    public static final String PACKAGE_NAME = "com.myth.shishi";

    public static final String DB_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME + "/" + DB_NAME; // 在手机里存放数据库的位置

    public static final String DB_NEW_PATH = "/data"
            + Environment.getDataDirectory().getAbsolutePath() + "/"
            + PACKAGE_NAME + "/" + DB_NEW_NAME; // 在手机里存放数据库的位置

    /**
     * The Constant VERSION.
     */
    public static final int DB_VERSION = 3;

    public static void initDatabase(Context context) {
        try {

            File file = new File(DB_NEW_PATH);
            boolean copy = true;
            if (file.exists()) {
                if (getNewDatabase().getVersion() >= DB_VERSION) {
                    copy = false;
                }
            }

            if (copy) {
                // 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                InputStream is = context.getResources().openRawResource(
                        R.raw.shishi); // 欲导入的数据库
                FileOutputStream fos = new FileOutputStream(DB_NEW_PATH);
                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
                getNewDatabase().setVersion(DB_VERSION);
            }

            if (!new File(DB_PATH).exists()) {
                if (!BackupTask.restoreDatabase(context)) {
                    // 判断数据库文件是否存在，若不存在则执行导入，否则直接打开数据库
                    InputStream is = context.getResources().openRawResource(
                            R.raw.writing); // 欲导入的数据库
                    FileOutputStream fos = new FileOutputStream(DB_PATH);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int count = 0;
                    while ((count = is.read(buffer)) > 0) {
                        fos.write(buffer, 0, count);
                    }
                    fos.close();
                    is.close();

                    getDatabase().setVersion(DB_VERSION);
                }
            }
            doUpdate();

        } catch (FileNotFoundException e) {
            Log.e("Database", "File not found");
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Database", "IO exception");
            e.printStackTrace();
        }
    }

    private static void doUpdate() {
        SQLiteDatabase db = getDatabase();
        if (db.getVersion() == 0) {
            // old version former_name is not null,need to be drop
            db.beginTransaction();
            try {
                db.execSQL(" create table writing1(id integer,ci_id integer,title text,text text, bgimg char(40), layout integer,create_dt DATETIME DEFAULT CURRENT_TIMESTAMP,update_dt DATETIME DEFAULT CURRENT_TIMESTAMP, primary key (id) )");
                db.execSQL(" INSERT INTO writing1 (id,title,text,bgimg,layout,create_dt,update_dt) SELECT id,title,text,bgimg,layout,create_dt,update_dt FROM writing"); //将旧表的内容插入到新表中
                db.execSQL(" DROP TABLE writing");
                db.execSQL(" alter table writing1 rename to writing");

                db.execSQL("CREATE TABLE \"cipai\" (\"id\" INTEGER PRIMARY KEY  NOT NULL ,\"name\" NVARCHAR(100) NOT NULL ,\"source\" TEXT,\"pingze\" TEXT,\"type\" INTEGER)");
                db.setVersion(DB_VERSION);
                db.setTransactionSuccessful();

                Log.d("myth", "DB update to 2");
            } catch (Exception e) {

            } finally {
                db.endTransaction();
            }
        }
    }

    public static SQLiteDatabase getNewDatabase() {
        return SQLiteDatabase.openOrCreateDatabase(DB_NEW_PATH, null);
    }

    public static SQLiteDatabase getDatabase() {
        return SQLiteDatabase.openOrCreateDatabase(DB_PATH, null);
    }


    /**
     * 判断某表里某字段是否存在
     *
     * @param db
     * @param tableName
     * @param fieldName
     * @return
     */
    private static boolean isFieldExist(SQLiteDatabase db, String tableName, String fieldName) {
        String queryStr = "select sql from sqlite_master where type = 'table' and name = '%s'";
        queryStr = String.format(queryStr, tableName);
        Cursor c = db.rawQuery(queryStr, null);
        String tableCreateSql = null;
        try {
            if (c != null && c.moveToFirst()) {
                tableCreateSql = c.getString(c.getColumnIndex("sql"));
            }
        } finally {
            if (c != null)
                c.close();
        }
        if (tableCreateSql != null && tableCreateSql.contains(fieldName))
            return true;
        return false;
    }
}
