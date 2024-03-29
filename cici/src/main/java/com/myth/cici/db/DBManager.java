package com.myth.cici.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myth.cici.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DBManager {
    private static final String TAG = "DBManager";

    private final static int BUFFER_SIZE = 400000;

    public static final String DB_NAME = "ci.db"; // 保存的数据库文件名，今天用于保存用户数据

    public static final String DB_NEW_NAME = "ci_version_8.db"; // 新的数据库文件名，只保存内容数据

    public static final String PACKAGE_NAME = "com.myth.cici";

    public static final String DB_PATH = "/data/data/"
            + PACKAGE_NAME + "/" + DB_NAME; // 在手机里存放数据库的位置

    public static final String DB_NEW_PATH = "/data/data/"
            + PACKAGE_NAME + "/" + DB_NEW_NAME; // 在手机里存放数据库的位置

    /**
     * The Constant VERSION.
     */
    public static final int DB_VERSION = 8;

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
                        R.raw.ci_new); // 欲导入的数据库
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
        if (db.getVersion() < 4) {
            db.execSQL("CREATE TABLE \"cipai\" (\"id\" INTEGER PRIMARY KEY  NOT NULL ,\"name\" NVARCHAR(100) NOT NULL ,\"source\" TEXT,\"pingze\" TEXT,\"type\" INTEGER)");
            db.setVersion(4);
        }
        if (db.getVersion() == 4) {
            if (!checkColumnExist(db, "writing", "ci_id")) {
                db.execSQL("alter TABLE writing add column ci_id integer");
                db.setVersion(DB_VERSION);
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
     * 方法1：检查某表列是否存在
     *
     * @param db
     * @param tableName  表名
     * @param columnName 列名
     * @return
     */
    private static boolean checkColumnExist(SQLiteDatabase db, String tableName
            , String columnName) {
        boolean result = false;
        Cursor cursor = null;
        try {
            //查询一行
            cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0"
                    , null);
            result = cursor != null && cursor.getColumnIndex(columnName) != -1;
        } catch (Exception e) {
            Log.e(TAG, "checkColumnExists1..." + e.getMessage());
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }
}
