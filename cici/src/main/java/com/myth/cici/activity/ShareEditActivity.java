//package com.myth.cici.activity;
//
//import android.app.Fragment;
//import android.app.FragmentTransaction;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.ImageView.ScaleType;
//import android.widget.LinearLayout;
//
//import com.myth.poetrycommon.Constant;
//import com.myth.cici.R;
//import com.myth.cici.entity.Ci;
//import com.myth.cici.entity.Cipai;
//import com.myth.poetrycommon.entity.Writing;
//import com.myth.cici.fragment.ChangeBackgroundFragment;
//import com.myth.cici.fragment.ChangePictureFragment;
//import com.myth.poetrycommon.BaseActivity;
//import com.myth.poetrycommon.utils.FileUtils;
//import com.myth.poetrycommon.utils.StringUtils;
//import com.myth.poetrycommon.view.TouchEffectImageView;
//
//import java.io.File;
//import java.util.ArrayList;
//
//public class ShareEditActivity extends BaseActivity {
//
//    private Cipai cipai;
//
//    private Writing writing;
//
//    private Ci ci;
//
//    ChangeBackgroundFragment changeBackgroundFrament;
//
//    ChangePictureFragment changePictureFragment;
//
//    ArrayList<Fragment> fragments = new ArrayList<Fragment>();
//
//    private int currentIndex = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit);
//
//        ci = (Ci) getIntent().getSerializableExtra("ci");
//
//        writing = new Writing();
//        writing.content=ci.getText();
//        writing.author=ci.getAuthor();
//        cipai = ci.getCipai();
//        writing.setCipai(cipai);
//
//        ImageView down = new TouchEffectImageView(mActivity, null);
//        down.setImageResource(R.drawable.done);
//        down.setScaleType(ScaleType.CENTER);
//        addBottomRightView(down);
//        down.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (currentIndex == 0) {
//                    changeBackgroundFrament.save();
//                } else {
//                    changePictureFragment.save();
//                }
//                if (!StringUtils.isNumeric(writing.bgimg) && writing.bitmap != null) {
//                    File file = new File(Constant.BACKGROUND_DIR, writing.bitmap.hashCode()+"");
//                    FileUtils.saveBitmap(writing.bitmap, file);
//                    writing.setBgimg(file.getAbsolutePath());
//                }
//                writing.setBitmap(null);
//                writing.setUpdate_dt(ci.getId());
//                Intent intent = new Intent(mActivity, ShareActivity.class);
//                intent.putExtra("writing", writing);
//                startActivity(intent);
//                finish();
//            }
//        });
//        setBottomVisible();
//        initView();
//    }
//
//    private void initView() {
//
//        final ImageView background = new TouchEffectImageView(mActivity, null);
//        background.setScaleType(ScaleType.FIT_XY);
//        background.setImageResource(R.drawable.layout_bg_paper_selected);
//
//        final ImageView picture = new TouchEffectImageView(mActivity, null);
//        picture.setScaleType(ScaleType.FIT_XY);
//        picture.setImageResource(R.drawable.layout_bg_album);
//
//        background.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                changeFragment(0);
//                background.setImageResource(R.drawable.layout_bg_paper_selected);
//                picture.setImageResource(R.drawable.layout_bg_album);
//            }
//        });
//
//        picture.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                changeFragment(1);
//                background.setImageResource(R.drawable.layout_bg_paper);
//                picture.setImageResource(R.drawable.layout_bg_album_sel);
//            }
//        });
//
//        LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(99, 114);
//        lps.leftMargin = 20;
//        addBottomCenterView(background, lps);
//        addBottomCenterView(picture, lps);
//
//        changeBackgroundFrament = ChangeBackgroundFragment.getInstance(writing);
//        changePictureFragment = ChangePictureFragment.getInstance(writing);
//
//        fragments.add(changeBackgroundFrament);
//        fragments.add(changePictureFragment);
//        changeFragment(currentIndex);
//    }
//
//    public void changeFragment(int pos) {
//        currentIndex = pos;
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, fragments.get(pos));
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
//
//    @Override
//    public void onBackPressed() {
//        finish();
//    }
//
//}
