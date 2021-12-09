package com.myth.cici;

import com.myth.cici.db.DBManager;
import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.Constant;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDatabase(getApplicationContext());
        Constant.init(getApplicationContext(),"cici", "ci.db");
    }

    @Override
    protected void openDB() {
        dataDB = DBManager.getNewDatabase();
        writingDB = DBManager.getDatabase();
    }

    @Override
    public boolean isCiApp() {
        return true;
    }
}
