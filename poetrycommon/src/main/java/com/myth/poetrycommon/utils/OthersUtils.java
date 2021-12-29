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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;

import androidx.core.content.FileProvider;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.activity.CommunityActivity;
import com.myth.poetrycommon.activity.WebViewActivity;
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.net.URLEncoder;
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
                intent.setType("image/*");
                Uri uri = FileProvider.getUriForFile(
                        context, context.getPackageName() + ".provider",
                        new File(imgPath));
                intent.putExtra(Intent.EXTRA_STREAM, uri);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
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


    final static String BAIKE_URL_PRE = "http://wapbaike.baidu.com/search?submit=%E8%BF%9B%E5%85%A5%E8%AF%8D%E6%9D%A1&uid=bk_1345472299_718&ssid=&st=1&bd_page_type=1&bk_fr=srch&word=";

    public static void goBaike(Context context, String word) {
        WebViewActivity.start(context, BAIKE_URL_PRE + URLEncoder.encode(word));
    }

    public static void fullScreen(final Activity activity, final boolean enable) {
        if (activity == null) {
            return;
        }
        Window window = activity.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (enable) {
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            layoutParams.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(layoutParams);
        } else {
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
            layoutParams.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.setAttributes(layoutParams);
        }
        window.setAttributes(layoutParams);
    }

    public static void startCommunity(Context context) {
        String appId = BaseApplication.instance.isCiApp() ? "wxfd03886a09d79515" : "wxfd03886a09d79515";
        IWXAPI api = WXAPIFactory.createWXAPI(context, appId);
        WXLaunchMiniProgram.Req req = new WXLaunchMiniProgram.Req();
        req.userName = "gh_86ec102f0613";
        req.path = "pages/topics/topics?group_id=454122845288";
        req.miniprogramType = WXLaunchMiniProgram.Req.MINIPTOGRAM_TYPE_RELEASE;
        boolean b = BaseApplication.instance.isCiApp() && api.sendReq(req);
        if (!b) {
            Intent intent = new Intent(context, CommunityActivity.class);
            context.startActivity(intent);
        }
    }
}
