package com.myth.poetrycommon.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {


    public static String saveBitmap(Bitmap bm, File file) {
        if (!file.exists()) {
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                bos.flush();
                bos.close();
            } catch (Exception e) {

            }
        }
        return file.getAbsolutePath();
    }

    public static void createDir(String dir) {
        File root = new File(dir);
        if (!root.exists()) {
            root.mkdirs();
        }
    }

    /**
     * 通知媒体库更新文件
     *
     * @param context
     * @param filePath 文件全路径
     */
    public static void updateMediaFile(Context context, String filePath) {
        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scanIntent.setData(Uri.fromFile(new File(filePath)));
        context.sendBroadcast(scanIntent);
    }
}
