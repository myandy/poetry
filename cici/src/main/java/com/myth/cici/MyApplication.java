package com.myth.cici;

import com.myth.cici.db.DBManager;
import com.myth.poetrycommon.BaseApplication;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDatabase(getApplicationContext());

        dataDB = DBManager.getNewDatabase();
        writingDB = DBManager.getDatabase();
    }

}
