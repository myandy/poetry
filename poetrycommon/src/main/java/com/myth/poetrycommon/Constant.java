package com.myth.poetrycommon;

import android.content.Context;
import android.os.Environment;

import com.myth.poetrycommon.utils.FileUtils;

import java.io.File;

/**
 * Created by AndyMao on 17-4-17.
 */

public class Constant {

    /**
     * 根目录
     */
    public static String ROOT_DIR;

    /**
     * 背景目录
     */
    public static String BACKGROUND_DIR;

    /**
     * 分享目录
     */
    public static String SHARE_DIR;

    public static String DBName;

    public static void init(Context context, String name, String dbName) {
        ROOT_DIR = Environment.getExternalStorageDirectory() + File.separator + name;
        BACKGROUND_DIR = context.getExternalFilesDir("background").getAbsolutePath();
        SHARE_DIR = context.getExternalFilesDir("share").getAbsolutePath();

        DBName = dbName;
        FileUtils.createDir(BACKGROUND_DIR);
        FileUtils.createDir(SHARE_DIR);
    }
}
