package com.myth.poetrycommon.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.myth.poetrycommon.BaseActivity;
import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.Constant;
import com.myth.poetrycommon.R;
import com.myth.poetrycommon.db.FormerDatabaseHelper;
import com.myth.poetrycommon.db.WritingDatabaseHelper;
import com.myth.poetrycommon.entity.Former;
import com.myth.poetrycommon.entity.Writing;
import com.myth.poetrycommon.fragment.ChangeBackgroundFragment;
import com.myth.poetrycommon.fragment.ChangePictureFragment;
import com.myth.poetrycommon.fragment.EditFragment;
import com.myth.poetrycommon.utils.FileUtils;
import com.myth.poetrycommon.utils.ResizeUtils;
import com.myth.poetrycommon.utils.StringUtils;
import com.myth.poetrycommon.view.GCDialog;
import com.myth.poetrycommon.view.GCDialog.OnCustomDialogListener;
import com.myth.poetrycommon.view.TouchEffectImageView;

import java.io.File;
import java.util.ArrayList;

public class EditActivity extends BaseActivity {

    private Former former;

    public Writing writing;

    ChangeBackgroundFragment changeBackgroundFragment;

    EditFragment editFragment;

    ChangePictureFragment changePictureFragment;

    ArrayList<Fragment> fragments = new ArrayList<>();

    private int currentIndex = 0;

    private String oldText;

    private String oldTitle;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("former", former);
        outState.putSerializable("writing", writing);
        super.onSaveInstanceState(outState);
        Log.w(TAG, "onSaveInstanceState" + (former == null) + (writing == null));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.w(TAG, "onRestoreInstanceState" + (former == null) + (writing == null));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.w(TAG, "onCreate" + (savedInstanceState == null));
        setContentView(R.layout.activity_edit);

        if (savedInstanceState != null) {
            former = (Former) savedInstanceState.getSerializable("former");
            writing = (Writing) savedInstanceState.getSerializable("writing");
        } else {
            former = (Former) getIntent().getSerializableExtra("former");
            writing = (Writing) getIntent().getSerializableExtra("writing");
        }

        // 填新词
        if (writing == null) {
            writing = new Writing();
            writing.id = (int) System.currentTimeMillis();
            writing.formerId = former.id;
            writing.bgimg = ("0");
            writing.former = former;
        }
        // 旧词编辑
        else if (writing != null) {
            former = writing.former;
            if (former == null) {
                former = FormerDatabaseHelper.getFormerById(writing.formerId);
            }
        }

        if (TextUtils.isEmpty(writing.author)) {
            writing.author = BaseApplication.getDefaultUserName();
        }
        if (TextUtils.isEmpty(writing.title)) {
            writing.title = former.name;
        }
        WritingDatabaseHelper.generateText(writing);
        oldText = writing.text;
        oldTitle = writing.title;

        getBottomLeftView().setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                exit();
            }
        });
        setBottomGone();

        ImageView down = new TouchEffectImageView(mActivity, null);
        down.setImageResource(R.drawable.done);
        down.setScaleType(ScaleType.FIT_XY);
        addBottomRightView(down, new LayoutParams(50, 50));
        down.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (currentIndex == 1) {
                    changeBackgroundFragment.save();
                } else if (currentIndex == 2) {
                    changePictureFragment.save();
                } else if (currentIndex == 0) {
                    editFragment.save();
                }
                save();
                writing.bitmap = null;
                Intent intent = new Intent(mActivity, ShareActivity.class);
                intent.putExtra("writing", writing);
                startActivity(intent);
                finish();
            }
        });

        initView();
    }

    private void initView() {

        final ImageView edit = new TouchEffectImageView(mActivity, null);
        edit.setScaleType(ScaleType.FIT_XY);
        edit.setImageResource(R.drawable.layout_bg_edit_selected);

        final ImageView background = new TouchEffectImageView(mActivity, null);
        background.setScaleType(ScaleType.FIT_XY);
        background.setImageResource(R.drawable.layout_bg_paper);

        final ImageView picture = new TouchEffectImageView(mActivity, null);
        picture.setScaleType(ScaleType.FIT_XY);
        picture.setImageResource(R.drawable.layout_bg_album);

        edit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                changeFragment(0);
                edit.setImageResource(R.drawable.layout_bg_edit_selected);
                background.setImageResource(R.drawable.layout_bg_paper);
                picture.setImageResource(R.drawable.layout_bg_album);

            }
        });

        background.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                changeFragment(1);
                edit.setImageResource(R.drawable.layout_bg_edit);
                background.setImageResource(R.drawable.layout_bg_paper_selected);
                picture.setImageResource(R.drawable.layout_bg_album);
            }
        });

        picture.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                changeFragment(2);
                edit.setImageResource(R.drawable.layout_bg_edit);
                background.setImageResource(R.drawable.layout_bg_paper);
                picture.setImageResource(R.drawable.layout_bg_album_sel);
            }
        });

        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(ResizeUtils.getInstance().dip2px(40),
                ResizeUtils.getInstance().dip2px(45));
        lps.leftMargin = 20;
        addBottomCenterView(edit, lps);
        addBottomCenterView(background, lps);
        addBottomCenterView(picture, lps);

        // 创建修改实例
        editFragment = new EditFragment();
        changeBackgroundFragment = new ChangeBackgroundFragment();
        changePictureFragment = new ChangePictureFragment();

        fragments.clear();
        fragments.add(editFragment);
        fragments.add(changeBackgroundFragment);
        fragments.add(changePictureFragment);
        changeFragment(currentIndex);
    }

    public void changeFragment(int pos) {
        currentIndex = pos;
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragments.get(pos));
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        doSave();
    }

    private void doSave() {
        if (currentIndex == 1) {
            changeBackgroundFragment.save();
        } else if (currentIndex == 2) {
            changePictureFragment.save();
        }
        editFragment.save();
        if ((!StringUtils.isEmpty(writing.text) && !writing.text.replaceAll("\\\\n", "").equals(oldText.replaceAll("\\\\n", "")))
                || (!StringUtils.isEmpty(writing.title) && !writing.title.equals(oldTitle))) {
           save();
        }
    }

    public void exit() {
        finish();
    }

    private void save() {
        if (!StringUtils.isNumeric(writing.bgimg) && writing.bitmap != null) {
            File file = new File(Constant.BACKGROUND_DIR, writing.bitmap.hashCode() + ".jpg");
            FileUtils.saveBitmap(writing.bitmap, file);
            writing.bgimg = file.getAbsolutePath();
        }
        WritingDatabaseHelper.saveWriting(writing);
    }

    @Override
    public void onBackPressed() {
        exit();
    }

}
