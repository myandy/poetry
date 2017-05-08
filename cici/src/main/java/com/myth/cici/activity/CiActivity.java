package com.myth.cici.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myth.cici.R;
import com.myth.cici.db.CiDatabaseHelper;
import com.myth.cici.db.CipaiDatabaseHelper;
import com.myth.cici.entity.Ci;
import com.myth.cici.entity.Cipai;
import com.myth.cici.wiget.CircleEditView;
import com.myth.poetrycommon.BaseActivity;
import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.activity.EditActivity;
import com.myth.poetrycommon.activity.ShareEditActivity;
import com.myth.poetrycommon.entity.Writing;
import com.myth.poetrycommon.utils.OthersUtils;
import com.myth.poetrycommon.utils.ResizeUtils;
import com.myth.poetrycommon.view.TouchEffectImageView;

import java.util.ArrayList;
import java.util.Random;

public class CiActivity extends BaseActivity {

    private ArrayList<Ci> ciList = new ArrayList<Ci>();

    private Cipai cipai;

    private int num;

    private TextView content;

    private boolean isRandom = false;

    private Ci ci;

    private TextView title;

    private TextView title1;

    private CircleEditView editView;

    TouchEffectImageView prev;

    TouchEffectImageView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ci);

        if (getIntent().hasExtra("cipai")) {
            ciList = (ArrayList<Ci>) getIntent().getSerializableExtra("cilist");
            cipai = (Cipai) getIntent().getSerializableExtra("cipai");
            num = getIntent().getIntExtra("num", 0);
            ci = ciList.get(num);
        } else {
            isRandom = true;
            ciList = CiDatabaseHelper.getAllCi();
            getRandomCi();
        }

        initView();
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
    }

    private void setColor() {
        editView.setColor(cipai.color);
    }

    private void initView() {
        LinearLayout topView = (LinearLayout) findViewById(R.id.right);
        LayoutParams param = new LayoutParams(
                ResizeUtils.getInstance().dip2px(80), ResizeUtils.getInstance().dip2px(
                80));
        editView = new CircleEditView(mActivity);
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
                        new String[]{"复制文本"},
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
                        new String[]{"复制文本"},
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
                Writing writing = new Writing();
                writing.content = ci.text;
                writing.author = ci.author;
                cipai = ci.getCipai();
                writing.former = cipai;
                intent.putExtra("data", writing);
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
        if (isRandom) {
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
        } else {
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
        if (!isRandom) {
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
    }

}
