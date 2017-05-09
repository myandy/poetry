package com.myth.shishi.wiget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.activity.ShareEditActivity;
import com.myth.poetrycommon.utils.OthersUtils;
import com.myth.poetrycommon.utils.ResizeUtils;
import com.myth.poetrycommon.utils.StringUtils;
import com.myth.poetrycommon.view.CircleImageView;
import com.myth.shishi.R;
import com.myth.shishi.activity.PoetrySearchActivity;
import com.myth.shishi.activity.WebviewActivity;
import com.myth.shishi.db.PoetryDatabaseHelper;
import com.myth.shishi.entity.Author;
import com.myth.shishi.entity.Poetry;

import java.util.Locale;

public class PoetryView extends LinearLayout {

    private Author author;

    private Poetry poetry;

    private Context mContext;

    private View root;

    private String page;

    private View more;

    private View menuView;


    public PoetryView(Context context, Author author, Poetry poetry, String page) {
        super(context);
        this.poetry = poetry;
        this.author = author;
        this.page = page;
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.layout_poetry, null);
        initView(root);
        addView(root);
    }

    private TextView content;

    private TextView title;

    CircleImageView shareView;

    private PopupWindow menu;

    int[] location;

    int color;

    private TextToSpeech mSpeech;

    private boolean mTTSEnable = false;

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mSpeech != null) {
            mSpeech.stop();
            mSpeech.shutdown();
        }
    }

    private void initView(View root) {

        mSpeech = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    } else {
                        mTTSEnable = true;
                        mSpeech.setSpeechRate(0.8f);
                    }
                }
            }
        });

        LinearLayout topView = (LinearLayout) root.findViewById(R.id.right);
        LayoutParams param = new LayoutParams(ResizeUtils.getInstance().dip2px(80),
                ResizeUtils.getInstance().dip2px(120));

        color = author.color;
        shareView = new CircleImageView(mContext, color,
                R.drawable.share3_white);
        topView.addView(shareView, 1, param);

        shareView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,
                        ShareEditActivity.class);
                intent.putExtra("data", poetry.toWriting());
                mContext.startActivity(intent);
            }
        });

        title = (TextView) root.findViewById(R.id.title);
        title.setTypeface(BaseApplication.instance.getTypeface());
        title.setText(poetry.getAuthor());

        content = (TextView) root.findViewById(R.id.content);
        content.setTypeface(BaseApplication.instance.getTypeface());
        ((TextView) root.findViewById(R.id.note)).setTypeface(BaseApplication.instance
                .getTypeface());

        root.findViewById(R.id.content).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(mContext).setItems(
                                new String[]{"复制文本"},
                                new DialogInterface.OnClickListener() {
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {

                                        if (which == 0) {

                                        }
                                        dialog.dismiss();
                                    }
                                }).show();

                    }
                });

        ((TextView) root.findViewById(R.id.author)).setTypeface(BaseApplication.instance
                .getTypeface());

        ((TextView) root.findViewById(R.id.page)).setText(page);

        more = root.findViewById(R.id.more);

        more.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showMenu();

            }
        });

        refreshView();

    }

    private void refreshView() {

        poetry.setTitle(poetry.getTitle().replaceAll("（.*）", "").trim());
        poetry.setPoetry(poetry.getPoetry().replaceAll("【.*】", "").trim());

        shareView.setColor(color);
        String note = poetry.getIntro();
        if (note != null && note.length() > 10) {
            ((TextView) root.findViewById(R.id.note)).setText(note);
        }
        poetry.setPoetry(StringUtils.autoLineFeed(poetry.getPoetry()));
        content.setText(poetry.getPoetry());
        ((TextView) root.findViewById(R.id.author))
                .setText(poetry.getTitle() == null ? "" : poetry.getTitle()
                        + "\n");

        setTextSize();

    }

    public void isAddTextSize(boolean add) {
        int size = BaseApplication.getDefaultTextSize(mContext);
        if (add) {
            size += 2;
        } else {
            size -= 2;
        }
        BaseApplication.setDefaultTextSize(mContext, size);
        setTextSize();
    }

    public void setTextSize() {

        int size = BaseApplication.getDefaultTextSize(mContext);
        ((TextView) root.findViewById(R.id.author)).setTextSize(size);
        content.setTextSize(size);
        ((TextView) root.findViewById(R.id.note)).setTextSize(size - 2);
    }

    private void showMenu() {
        if (menu == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            menuView = inflater.inflate(R.layout.dialog_poetry, null);

            // PopupWindow定义，显示view，以及初始化长和宽
            menu = new PopupWindow(menuView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);

            // 必须设置，否则获得焦点后页面上其他地方点击无响应
            menu.setBackgroundDrawable(new BitmapDrawable());
            // 设置焦点，必须设置，否则listView无法响应
            menu.setFocusable(true);
            // 设置点击其他地方 popupWindow消失
            menu.setOutsideTouchable(true);

            menu.setAnimationStyle(R.style.popwindow_anim_style);

            // 让view可以响应菜单事件
            menuView.setFocusableInTouchMode(true);

            menuView.setOnKeyListener(new OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_MENU) {
                        if (menu != null) {
                            menu.dismiss();
                        }
                        return true;
                    }
                    return false;
                }
            });
            location = new int[2];

            menuView.findViewById(R.id.tv1).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            isAddTextSize(true);
                            if (menu != null) {
                                menu.dismiss();
                            }
                        }
                    });
            menuView.findViewById(R.id.tv2).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            isAddTextSize(false);
                            if (menu != null) {
                                menu.dismiss();
                            }
                        }
                    });
            TextView collect = (TextView) menuView.findViewById(R.id.tv3);

            if (PoetryDatabaseHelper.isCollect(poetry.getPoetry())) {
                collect.setText("取消收藏");
            } else {
                collect.setText("收藏");
            }
            collect.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    boolean isCollect = PoetryDatabaseHelper
                            .isCollect(poetry.getPoetry());
                    PoetryDatabaseHelper.updateCollect(poetry.getId(),
                            !isCollect);
                    if (isCollect) {
                        Toast.makeText(mContext, "已取消收藏", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        Toast.makeText(mContext, "已收藏", Toast.LENGTH_LONG)
                                .show();
                    }
                    if (menu != null) {
                        menu.dismiss();
                    }
                }
            });
            menuView.findViewById(R.id.tv4).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,
                                    PoetrySearchActivity.class);
                            intent.putExtra("author", author);
                            mContext.startActivity(intent);
                            if (menu != null) {
                                menu.dismiss();
                            }
                        }
                    });

            menuView.findViewById(R.id.tv5).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,
                                    WebviewActivity.class);
                            intent.putExtra("string", poetry.getAuthor());
                            mContext.startActivity(intent);
                            if (menu != null) {
                                menu.dismiss();
                            }
                        }
                    });
            menuView.findViewById(R.id.tv6).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(mContext,
                                    WebviewActivity.class);
                            intent.putExtra("string", poetry.getTitle());
                            mContext.startActivity(intent);
                            if (menu != null) {
                                menu.dismiss();
                            }
                        }
                    });

            menuView.findViewById(R.id.tv7).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            OthersUtils.copy(
                                    title.getText() + "\n" + content.getText(),
                                    mContext);
                            Toast.makeText(mContext, R.string.copy_text_done,
                                    Toast.LENGTH_SHORT).show();
                            if (menu != null) {
                                menu.dismiss();
                            }
                        }
                    });

            menuView.findViewById(R.id.tv8).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (mTTSEnable) {
                                mSpeech.speak(poetry.getTitle() + "\n" + poetry.getPoetry().replaceAll("[\\[\\]0-9]", ""), TextToSpeech.QUEUE_FLUSH,
                                        null);
                            } else {
                                Toast.makeText(mContext, R.string.tts_unable,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            menuView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            int popupWidth = menuView.getMeasuredWidth();
            int popupHeight = menuView.getMeasuredHeight();

            more.getLocationOnScreen(location);

            location[0] = location[0] + more.getWidth() / 2 - popupWidth / 2;
            location[1] = location[1] - popupHeight;

            menu.showAtLocation(more, Gravity.NO_GRAVITY, location[0],
                    location[1]);
            // 显示在某个位置

        } else {
            TextView collect = (TextView) menuView.findViewById(R.id.tv3);
            if (PoetryDatabaseHelper.isCollect(poetry.getPoetry())) {
                collect.setText("取消收藏");
            } else {
                collect.setText("收藏");
            }
            menu.showAtLocation(more, Gravity.NO_GRAVITY, location[0],
                    location[1]);
        }

    }
}
