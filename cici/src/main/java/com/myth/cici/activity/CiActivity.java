package com.myth.cici.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.myth.cici.R;
import com.myth.cici.db.CiDatabaseHelper;
import com.myth.cici.db.CipaiDatabaseHelper;
import com.myth.cici.entity.Ci;
import com.myth.cici.entity.Cipai;
import com.myth.poetrycommon.BaseActivity;
import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.activity.EditActivity;
import com.myth.poetrycommon.activity.ShareEditActivity;
import com.myth.poetrycommon.utils.OthersUtils;
import com.myth.poetrycommon.utils.ResizeUtils;
import com.myth.poetrycommon.view.CircleImageView;
import com.myth.poetrycommon.view.TouchEffectImageView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class CiActivity extends BaseActivity {

    private ArrayList<Ci> ciList = new ArrayList<Ci>();

    private Cipai cipai;

    private int num = -1;

    private TextView content;

    private Ci ci;

    private TextView title;

    private TextView title1;

    private CircleImageView editView;

    TouchEffectImageView prev;

    TouchEffectImageView next;

    int showMode;

    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_RANDOM = 1;
    private static final int TYPE_SEARCH = 2;

    private PopupWindow menu;

    int[] location;

    private View menuView;

    private TouchEffectImageView more;

    private TextToSpeech mSpeech;

    private boolean mTTSEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ci);

        if (getIntent().hasExtra("cipai")) {
            ciList = (ArrayList<Ci>) getIntent().getSerializableExtra("cilist");
            cipai = (Cipai) getIntent().getSerializableExtra("cipai");
            num = getIntent().getIntExtra("num", 0);
            ci = ciList.get(num);
            showMode = TYPE_DEFAULT;
        } else if (getIntent().hasExtra("id")) {
            ci = CiDatabaseHelper.getCiById(getIntent().getIntExtra("id", 0));
            cipai = CipaiDatabaseHelper.getCipaiById(ci.getCi_id());
            cipai.color = BaseApplication.instance.getRandomColor();
            ci.cipai = cipai;
            showMode = TYPE_SEARCH;
        } else {
            ciList = CiDatabaseHelper.getAllCi();
            getRandomCi();
            showMode = TYPE_RANDOM;
        }

        initView();

        mSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

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
    }

    private void getRandomCi() {
        ci = ciList.get(new Random().nextInt(ciList.size()));
        cipai = CipaiDatabaseHelper.getCipaiById(ci.getCi_id());
        cipai.color = BaseApplication.instance.getRandomColor();
        if (cipai.parent_id > 0) {
            Cipai cipai1 = CipaiDatabaseHelper.getCipaiById(cipai
                    .parent_id);
            cipai.source = (cipai1.source);
        }
        ci.cipai = cipai;
    }

    private void setColor() {
        editView.setColor(cipai.color);
    }

    private void initView() {
        LinearLayout topView = (LinearLayout) findViewById(R.id.right);
        LayoutParams param = new LayoutParams(
                ResizeUtils.getInstance().dip2px(80), ResizeUtils.getInstance().dip2px(
                80));
        editView = new CircleImageView(mActivity, R.color.white, R.drawable.edit_white);
        topView.addView(editView, 1, param);

        title = (TextView) findViewById(R.id.title);
        title.setTypeface(BaseApplication.instance.getTypeface());


        title.setTextSize(40);

        title1 = (TextView) findViewById(R.id.title1);
        title1.setTypeface(BaseApplication.instance.getTypeface());
        title1.setTextSize(40);


        content = (TextView) findViewById(R.id.content);
        content.setTypeface(BaseApplication.instance.getTypeface());
        content.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mActivity).setItems(
                        new String[]{getString(R.string.copy_text)},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if (which == 0) {
                                    OthersUtils.copy(title.getText() + "\n"
                                            + content.getText(), mActivity);
                                    Toast.makeText(mActivity,
                                            R.string.copy_text_done,
                                            Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

        findViewById(R.id.note).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mActivity).setItems(
                        new String[]{getString(R.string.copy_text)},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if (which == 0) {
                                    OthersUtils.copy(title.getText() + "\n"
                                            + content.getText(), mActivity);
                                    Toast.makeText(mActivity,
                                            R.string.copy_text_done,
                                            Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

        findViewById(R.id.share).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,
                        ShareEditActivity.class);
                ci.cipai = cipai;
                intent.putExtra("data", ci.toWriting());
                startActivity(intent);
            }
        });
        editView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, EditActivity.class);
                intent.putExtra("former", cipai);
                startActivity(intent);
            }
        });

        initBottomRightView();

        refreshRandomView();
        setBottomVisible();
    }

    private void initBottomRightView() {
        if (showMode == TYPE_RANDOM) {
            ImageView view = new TouchEffectImageView(mActivity, null);
            view.setImageResource(R.drawable.random);
            view.setScaleType(ScaleType.CENTER);
            addBottomRightView(view);
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    getRandomCi();
                    refreshRandomView();
                }
            });
        } else if (showMode == TYPE_DEFAULT) {
            prev = new TouchEffectImageView(mActivity, null);
            prev.setImageResource(R.drawable.prev);
            prev.setScaleType(ScaleType.FIT_XY);
            addBottomRightView(prev,
                    new LayoutParams(ResizeUtils.getInstance().dip2px(42),
                            ResizeUtils.getInstance().dip2px(42)));
            prev.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (num > 1) {
                        num--;
                        ci = ciList.get(num);
                        initContentView();
                    }
                }
            });

            next = new TouchEffectImageView(mActivity, null);
            next.setImageResource(R.drawable.next);
            next.setScaleType(ScaleType.FIT_XY);
            addBottomRightView(next,
                    new LayoutParams(ResizeUtils.getInstance().dip2px(42),
                            ResizeUtils.getInstance().dip2px(42)));
            next.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (num < ciList.size() - 1) {
                        num++;
                        ci = ciList.get(num);
                        initContentView();
                    }

                }
            });
        }

        more = new TouchEffectImageView(mActivity, null);
        more.setImageResource(R.drawable.setting);
        more.setScaleType(ScaleType.FIT_XY);
        addBottomRightView(more);
        more.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showMenu();
            }
        });

    }

    private void refreshRandomView() {
        if (!TextUtils.isEmpty(cipai.name)) {
            title.setText(cipai.name);
            if (cipai.name.length() > 5) {
                title1.setText(cipai.name.substring(0, 5));
                title.setText(cipai.name.substring(5));
            } else {
                title1.setText("");
            }
        }
        setColor();
        initContentView();
    }

    private void initContentView() {
        if (showMode == TYPE_DEFAULT) {
            if (num < ciList.size() - 1) {
                next.setClickEnable();
            } else {
                next.setClickDisable();
            }
            if (num > 1) {
                prev.setClickEnable();
            } else {
                prev.setClickDisable();
            }
        }
        String note = ci.getNote();
        if (note == null) {
            note = "";
        }
        content.setText(ci.getText());
        ((TextView) findViewById(R.id.note)).setText(note);
        if (!TextUtils.isEmpty(ci.getAuthor())) {
            ((TextView) findViewById(R.id.author)).setText(ci.getAuthor()
                    + "\n");
        }
        if (num == 0) {
            findViewById(R.id.share).setVisibility(View.GONE);
        } else {
            findViewById(R.id.share).setVisibility(View.VISIBLE);
        }
    }

    private void showMenu() {
        if (menu == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            menuView = inflater.inflate(R.layout.dialog_ci, null);

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

            menuView.setOnKeyListener(new View.OnKeyListener() {

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

            TextView collect = (TextView) menuView.findViewById(R.id.tv3);
            if (CiDatabaseHelper.isCollect(ci.id)) {
                collect.setText("取消收藏");
            } else {
                collect.setText("收藏");
            }
            collect.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    boolean isCollect = CiDatabaseHelper.isCollect(ci.id);
                    CiDatabaseHelper.updateCollect(ci.id,
                            !isCollect);
                    if (isCollect) {
                        Toast.makeText(mActivity, "已取消收藏", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        Toast.makeText(mActivity, "已收藏", Toast.LENGTH_LONG)
                                .show();
                    }
                    if (menu != null) {
                        menu.dismiss();
                    }
                }
            });
            menuView.findViewById(R.id.tv5).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (menu != null) {
                                menu.dismiss();
                            }
                            OthersUtils.goBaike(mActivity, ci.author);
                        }
                    });
            menuView.findViewById(R.id.tv6).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (menu != null) {
                                menu.dismiss();
                            }
                            OthersUtils.goBaike(mActivity, ci.cipai_name);
                        }
                    });
            menuView.findViewById(R.id.tv7).setOnClickListener(
                    new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            OthersUtils.copy(
                                    title.getText() + "\n" + content.getText(),
                                    mActivity);
                            Toast.makeText(mActivity, R.string.copy_text_done,
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
                                mSpeech.speak(ci.cipai_name + ci.author + ci.text, TextToSpeech.QUEUE_FLUSH,
                                        null);
                            } else {
                                Toast.makeText(mActivity, R.string.tts_unable,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


            menuView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int popupWidth = menuView.getMeasuredWidth();
            int popupHeight = menuView.getMeasuredHeight();

            more.getLocationOnScreen(location);

            location[0] = location[0] + more.getWidth() / 2 - popupWidth / 2 - ResizeUtils.getInstance().dip2px(5);
            location[1] = location[1] - popupHeight - ResizeUtils.getInstance().dip2px(20);

            menu.showAtLocation(more, Gravity.NO_GRAVITY, location[0],
                    location[1]);
            // 显示在某个位置

        } else {
            TextView collect = (TextView) menuView.findViewById(R.id.tv3);
            if (CiDatabaseHelper.isCollect(ci.id)) {
                collect.setText("取消收藏");
            } else {
                collect.setText("收藏");
            }
            menu.showAtLocation(more, Gravity.NO_GRAVITY, location[0],
                    location[1]);
        }

    }
}
