package com.myth.cici;

import com.myth.cici.db.DBManager;
import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.Constant;
import com.umeng.socialize.PlatformConfig;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.initDatabase(getApplicationContext());
        Constant.init("cici", "ci.db");
        PlatformConfig.setWeixin("wx96110a1e3af63a39", "c60e3d3ff109a5d17013df272df99199");
        PlatformConfig.setSinaWeibo("2655542749", "d3c6e64eb912183bdf2ecc299ddfe3a7");
        PlatformConfig.setQQZone("1104396282", "KEYwA42NSJxWzHJjHRe");
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
