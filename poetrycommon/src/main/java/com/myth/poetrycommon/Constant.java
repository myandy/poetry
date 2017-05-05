package com.myth.poetrycommon;

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

    public static void init(String name) {
        ROOT_DIR = Environment.getExternalStorageDirectory() + File.pathSeparator + name;
        BACKGROUND_DIR = ROOT_DIR + "/background";
        SHARE_DIR = ROOT_DIR + "/share";
        FileUtils.createDir(BACKGROUND_DIR);
        FileUtils.createDir(SHARE_DIR);
    }
}
