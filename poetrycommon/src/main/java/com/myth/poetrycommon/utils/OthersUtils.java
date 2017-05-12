package com.myth.poetrycommon.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.text.ClipboardManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.myth.poetrycommon.R;
import com.myth.poetrycommon.activity.CommunityActivity;

import java.io.File;
import java.util.List;

public class OthersUtils {

    public static boolean isValidIntent(Context context, Intent intent) {
        PackageManager packageManager;
        List<ResolveInfo> resolveInfoList;
        boolean ret = false;

        packageManager = context.getPackageManager();
        resolveInfoList = packageManager.queryIntentActivities(intent, 0);

        if ((resolveInfoList != null) && (resolveInfoList.size() > 0)) {
            ret = true;
        }

        return ret;
    }

    /**
     * 实现文本复制功能 add by wangqianzhou
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }

    /**
     * 分享功能
     *
     * @param context       上下文
     * @param activityTitle Activity的名字
     * @param msgTitle      消息标题
     * @param msgText       消息内容
     * @param imgPath       图片路径，不分享图片则传null
     */
    public static void shareMsg(Context context, String activityTitle, String msgTitle, String msgText, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, activityTitle));
    }

    public static Bitmap createViewBitmap(View v) {
        if (v instanceof ScrollView) {
            v = ((ScrollView) v).getChildAt(0);
        }
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.layout(0, 0, v.getWidth(), v.getHeight());
        v.draw(canvas);

        Matrix matrix = new Matrix();
        float scale = 1.0f * 600 / v.getWidth();
        matrix.setScale(scale, scale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
        return bitmap;
    }

    public static boolean isChinese(char c) {

        String chinese = "[\u4e00-\u9fa5]";
        return (c + "").matches(chinese);
    }

    public static String getFirstChinese(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (isChinese(input.charAt(i))) {
                return input.charAt(i) + "";
            }
        }
        return "";

    }

    /**
     * 获取屏幕宽度
     *
     * @param
     * @return
     */
    public static int getDisplayWidth(Context context) {
        int width = 0;
        if (context == null) {
            return 0;
        }
        // 如果context是Activity
        if (context instanceof Activity) {
            width = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        } else
        // 否则，通过系统服务来获取屏幕宽度
        {
            width = getDisplayWidthBySystemService(context);
        }
        return width;
    }

    /**
     * 获取屏幕宽度(通过系统服务)
     *
     * @param context
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static int getDisplayWidthBySystemService(Context context) {
        int width = 0;
        try {
            WindowManager wm = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE));
            width = wm.getDefaultDisplay().getWidth();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return width;
    }

    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0";
    }


    /**
     * 为程序创建桌面快捷方式
     */
    public static void addShortcut(Context context) {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getResources().getString(R.string.community_name));
        shortcut.putExtra("duplicate", false);

        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setClassName(context, CommunityActivity.class.getName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);

        Intent.ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(context, R.mipmap.community_shotcut);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        context.sendBroadcast(shortcut);
    }
}
