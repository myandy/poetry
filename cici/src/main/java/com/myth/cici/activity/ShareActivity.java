//package com.myth.cici.activity;
//
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.drawable.BitmapDrawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.MeasureSpec;
//import android.view.View.OnClickListener;
//import android.view.View.OnKeyListener;
//import android.view.ViewGroup;
//import android.view.animation.Animation;
//import android.view.animation.AnimationSet;
//import android.view.animation.RotateAnimation;
//import android.view.animation.ScaleAnimation;
//import android.widget.ImageView;
//import android.widget.PopupWindow;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.myth.poetrycommon.Constant;
//import com.myth.cici.R;
//import com.myth.cici.db.ColorDatabaseHelper;
//import com.myth.cici.entity.Cipai;
//import com.myth.poetrycommon.entity.Writing;
//import com.myth.poetrycommon.view.ShareView;
//import com.myth.poetrycommon.BaseActivity;
//import com.myth.poetrycommon.BaseApplication;
//import com.myth.poetrycommon.entity.ColorEntity;
//import com.myth.poetrycommon.utils.FileUtils;
//import com.myth.poetrycommon.utils.OthersUtils;
//import com.myth.poetrycommon.utils.ResizeUtils;
//import com.myth.poetrycommon.view.TouchEffectImageView;
//
//import java.io.File;
//import java.util.List;
//
//public class ShareActivity extends BaseActivity {
//
//    private Cipai cipai;
//
//    private Writing writing;
//
//    private PopupWindow menu;
//
//    int[] location;
//
//    private View menuView;
//
//    private ImageView setting;
//
//    private View contentLL;
//    private ShareView shareView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_share);
//
//        writing = (Writing) getIntent().getSerializableExtra("writing");
//        if (writing != null) {
//            cipai = writing.getCipai();
//        } else {
//            finish();
//        }
//
//        setting = new TouchEffectImageView(mActivity, null);
//        setting.setImageResource(R.drawable.setting);
//        addBottomRightView(setting);
//        setting.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                showMenu();
//            }
//        });
//        setBottomVisible();
//        initView();
//    }
//
//    private void initView() {
//        shareView = (ShareView) findViewById(R.id.share_view);
//        contentLL = findViewById(R.id.content_linear);
//        contentLL.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                new AlertDialog.Builder(mActivity).setItems(new String[]{"复制文本", "保存图片", "分享"},
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                if (which == 0) {
//                                    OthersUtils.copy(writing.getCipai()name + "\n" + writing.content, mActivity);
//                                    Toast.makeText(mActivity, R.string.copy_text_done, Toast.LENGTH_SHORT).show();
//                                } else if (which == 1) {
//                                    String filePath = saveImage();
//                                    if (!TextUtils.isEmpty(filePath)) {
//                                        Toast.makeText(mActivity, "图片已保存在：" + filePath, Toast.LENGTH_SHORT).show();
//                                    }
//
//                                } else if (which == 2) {
//                                    String filePath = saveImage();
//                                    if (!TextUtils.isEmpty(filePath)) {
//                                        OthersUtils.shareMsg(mActivity, "词Ci", "share", "content", filePath);
//                                    }
//
//                                }
//                                dialog.dismiss();
//                            }
//                        }).show();
//
//            }
//        });
//
//        shareView.setWriting(writing);
//        ResizeUtils.getInstance().layoutSquareView(shareView);
//        scaleRotateIn(shareView, 1000, 0);
//    }
//
//
//    public final int rela1 = Animation.RELATIVE_TO_SELF;
//
//    public void scaleRotateIn(View view, long durationMillis, long delayMillis) {
//        view.setVisibility(View.VISIBLE);
//        ScaleAnimation animation1 = new ScaleAnimation(0, 1, 0, 1, rela1, 0.5f, rela1, 0.5f);
//        RotateAnimation animation2 = new RotateAnimation(0, 357, rela1, 0.5f, rela1, 0.5f);
//        AnimationSet animation = new AnimationSet(false);
//        animation.addAnimation(animation1);
//        animation.addAnimation(animation2);
//        animation.setFillAfter(true);
//        animation.setDuration(durationMillis);
//        animation.setStartOffset(delayMillis);
//        view.setAnimation(animation);
//    }
//
//    private String saveImage() {
//        String filename = cipai.getEnname() + writing.getUpdate_dt();
//        File file = new File(Constant.SHARE_DIR, filename);
//        FileUtils.saveBitmap(OthersUtils.createViewBitmap(shareView), file);
//        updateMediaFile(mActivity, file.getAbsolutePath());
//        return file.getAbsolutePath();
//    }
//
//    /**
//     * 通知媒体库更新文件
//     *
//     * @param context
//     * @param filePath 文件全路径
//     */
//    private static void updateMediaFile(Context context, String filePath) {
//        Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        scanIntent.setData(Uri.fromFile(new File(filePath)));
//        context.sendBroadcast(scanIntent);
//    }
//
//    public void isAddTextSize(boolean add) {
//        int size = BaseApplication.getDefaultShareSize(mActivity);
//        if (add) {
//            size += 2;
//        } else {
//            size -= 2;
//        }
//        BaseApplication.setDefaultShareSize(mActivity, size);
//        shareView.setTextSize();
//    }
//
//
//    private void setGravity(boolean isCenter) {
//        BaseApplication.setDefaultShareGravity(mActivity, isCenter);
//        shareView.setGravity();
//    }
//
//
//    private void setAuthor(boolean showAuthor) {
//        BaseApplication.setDefaultShareAuthor(mActivity, showAuthor);
//        shareView.setAuthor();
//    }
//
//    private void setPadding(boolean isAdd) {
//        int margin = BaseApplication.getDefaultSharePadding(mActivity);
//        if (isAdd) {
//            margin += 10;
//        } else {
//            margin -= 10;
//        }
//        BaseApplication.setDefaultSharePadding(mActivity, margin);
//        shareView.setPadding();
//    }
//
//
//    private void setColor(int color) {
//        BaseApplication.setDefaultShareColor(mActivity, color);
//        shareView.setColor();
//    }
//
//    private void showMenu() {
//        if (menu == null) {
//            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            menuView = inflater.inflate(R.layout.dialog_share, null);
//
//            // PopupWindow定义，显示view，以及初始化长和宽
//            menu = new PopupWindow(menuView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
//                    true);
//
//            // 必须设置，否则获得焦点后页面上其他地方点击无响应
//            menu.setBackgroundDrawable(new BitmapDrawable());
//            // 设置焦点，必须设置，否则listView无法响应
//            menu.setFocusable(true);
//            // 设置点击其他地方 popupWindow消失
//            menu.setOutsideTouchable(true);
//
//            // 让view可以响应菜单事件
//            menuView.setFocusableInTouchMode(true);
//
//            menuView.setOnKeyListener(new OnKeyListener() {
//
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    if (keyCode == KeyEvent.KEYCODE_MENU) {
//                        if (menu != null) {
//                            menu.dismiss();
//                        }
//                        return true;
//                    }
//                    return false;
//                }
//            });
//            location = new int[2];
//
//            menuView.findViewById(R.id.tv1).setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    isAddTextSize(true);
//                    if (menu != null) {
//                        menu.dismiss();
//                    }
//                }
//            });
//            menuView.findViewById(R.id.tv2).setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    isAddTextSize(false);
//                    if (menu != null) {
//                        menu.dismiss();
//                    }
//                }
//            });
//            menuView.findViewById(R.id.tv3).setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    setGravity(true);
//                    if (menu != null) {
//                        menu.dismiss();
//                    }
//                }
//            });
//            menuView.findViewById(R.id.tv4).setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    setGravity(false);
//                    if (menu != null) {
//                        menu.dismiss();
//                    }
//                }
//            });
//            menuView.findViewById(R.id.tv5).setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    setPadding(false);
//                    if (menu != null) {
//                        menu.dismiss();
//                    }
//                }
//            });
//            menuView.findViewById(R.id.tv6).setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    setPadding(true);
//                    if (menu != null) {
//                        menu.dismiss();
//                    }
//                }
//            });
//            menuView.findViewById(R.id.tv7).setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    final List<ColorEntity> list = ColorDatabaseHelper.getAll();
//                    String s[] = new String[list.size() + 1];
//                    s[0] = "黑色";
//                    for (int i = 1; i < list.size() + 1; i++) {
//                        s[i] = list.get(i - 1)name;
//                    }
//                    int color = BaseApplication.getDefaultShareColor(mActivity);
//                    new AlertDialog.Builder(mActivity).setSingleChoiceItems(s, color, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            setColor(which);
//                            dialog.dismiss();
//                        }
//                    }).show();
//                    if (menu != null) {
//                        menu.dismiss();
//                    }
//                }
//            });
//
//            menuView.findViewById(R.id.tv8).setOnClickListener(new OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    boolean isCollect = BaseApplication.getDefaultShareAuthor(mActivity);
//                    setAuthor(!isCollect);
//                    if (menu != null) {
//                        menu.dismiss();
//                    }
//                }
//            });
//
//            if (BaseApplication.getDefaultShareAuthor(mActivity)) {
//                ((TextView) menuView.findViewById(R.id.tv8)).setText("隐藏作者");
//            } else {
//                ((TextView) menuView.findViewById(R.id.tv8)).setText("显示作者");
//            }
//
//            menuView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
//            int popupWidth = menuView.getMeasuredWidth();
//            int popupHeight = menuView.getMeasuredHeight();
//
//            setting.getLocationOnScreen(location);
//
//            location[0] = location[0] + setting.getWidth() / 2 - popupWidth / 2;
//            location[1] = location[1] - popupHeight;
//
//            menu.showAtLocation(setting, Gravity.NO_GRAVITY, location[0], location[1]);
//            // 显示在某个位置
//
//        } else {
//            if (BaseApplication.getDefaultShareAuthor(mActivity)) {
//                ((TextView) menuView.findViewById(R.id.tv8)).setText("隐藏作者");
//            } else {
//                ((TextView) menuView.findViewById(R.id.tv8)).setText("显示作者");
//            }
//            menu.showAtLocation(setting, Gravity.NO_GRAVITY, location[0], location[1]);
//        }
//
//    }
//
//}
