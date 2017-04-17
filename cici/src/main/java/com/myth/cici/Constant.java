package com.myth.cici;

import android.os.Environment;

import com.myth.poetrycommon.utils.FileUtils;

/**
 * Created by AndyMao on 17-4-17.
 */

public class Constant {


    /**
     * 根目录
     */
    public static final String ROOT_DIR = Environment.getExternalStorageDirectory() + "/cici";

    /**
     * 背景目录
     */
    public static final String BACKGROUND_DIR = ROOT_DIR + "/background";

    /**
     * 分享目录
     */
    public static final String SHARE_DIR = ROOT_DIR + "/share";


    static {
        FileUtils.createDir(BACKGROUND_DIR);
        FileUtils.createDir(SHARE_DIR);
    }
}
