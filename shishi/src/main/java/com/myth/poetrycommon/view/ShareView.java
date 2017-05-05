//package com.myth.poetrycommon.view;
//
//import android.content.Context;
//import android.graphics.drawable.BitmapDrawable;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import com.myth.poetrycommon.BaseApplication;
//import com.myth.poetrycommon.utils.ResizeUtils;
//import com.myth.poetrycommon.utils.StringUtils;
//import com.myth.poetrycommon.view.MirrorLoaderView;
//import com.myth.shishi.MyApplication;
//import com.myth.shishi.R;
//import com.myth.poetrycommon.db.FormerDatabaseHelper;
//import com.myth.poetrycommon.entity.Writing;
//
//public class ShareView extends ScrollView {
//
//    private Context mContext;
//
//    private Writing writing;
//
//    private TextView text;
//
//    private TextView title;
//
//    private TextView author;
//
//    private TextView desc;
//
//    public MirrorLoaderView getBackgroundView() {
//        return backgroundView;
//    }
//
//    private MirrorLoaderView backgroundView;
//
//    public ImageView getPictureView() {
//        return pictureView;
//    }
//
//    private ImageView pictureView;
//
//    public static final int TYPE_NORMAL = 0;
//    public static final int TYPE_BACKGROUND = 1;
//    public static final int TYPE_PICTURE = 2;
//
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    private int type;
//
//    public ShareView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        mContext = context;
//        initView();
//    }
//
//    public ShareView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mContext = context;
//        initView();
//    }
//
//    public ShareView(Context context, Writing writing) {
//        super(context);
//        mContext = context;
//        initView();
//        setWriting(writing);
//    }
//
//
//    public void setWriting(Writing writing) {
//        this.writing = writing;
//        if (writing.getFormer() == null) {
//            writing.setFormer(FormerDatabaseHelper.getFormerByName(writing.getFormerName()));
//        }
//        refresh();
//    }
//
//    private void initView() {
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        inflater.inflate(R.layout.layout_share, this);
//
//        backgroundView = (MirrorLoaderView) findViewById(R.id.background_view);
//        pictureView = (ImageView) findViewById(R.id.picture_view);
//
//        backgroundView.setMinimumHeight(ResizeUtils.getInstance().resize(540));
//        pictureView.setMinimumHeight(ResizeUtils.getInstance().resize(540));
//
//        title = (TextView) findViewById(R.id.title);
//        text = (TextView) findViewById(R.id.text);
//        author = (TextView) findViewById(R.id.author);
//        desc = (TextView) findViewById(R.id.desc);
//
//        title.setTypeface(MyApplication.instance.getTypeface());
//        text.setTypeface(MyApplication.instance.getTypeface());
//        author.setTypeface(MyApplication.instance.getTypeface());
//    }
//
//    public void refresh() {
//        if (type == TYPE_NORMAL) {
//            if (StringUtils.isNumeric(writing.bgimg)) {
//                backgroundView.setDrawableId(MyApplication.bgimgList[Integer.parseInt(writing.bgimg)]);
//            } else {
//                pictureView.setImageDrawable(new BitmapDrawable(getResources(), writing.bgimg));
//            }
//        } else if (type == TYPE_BACKGROUND) {
//            if (StringUtils.isNumeric(writing.bgimg)) {
//                backgroundView.setDrawableId(MyApplication.bgimgList[Integer.parseInt(writing.bgimg)]);
//            } else {
//                backgroundView.setDrawableId(MyApplication.bgimgList[0]);
//            }
//        } else {
//            if (TextUtils.isEmpty(writing.bgimg)) {
//                pictureView.setImageResource(R.drawable.zuibaichi);
//            } else {
//                pictureView.setImageDrawable(new BitmapDrawable(getResources(), writing.bgimg));
//            }
//        }
//
//        text.setText(writing.content);
//        title.setText(writing.title);
//
//        if (TextUtils.isEmpty(writing.getAuthor())) {
//            author.setText(BaseApplication.getDefaultUserName());
//        } else {
//            author.setText(writing.getAuthor());
//        }
//
//        if (TextUtils.isEmpty(writing.desc)) {
//            desc.setVisibility(View.GONE);
//        } else {
//            desc.setVisibility(View.VISIBLE);
//            desc.setText(writing.desc);
//        }
//
//        setTextSize();
//        setGravity();
//        setPadding();
//        setColor();
//        setAuthor();
//
//    }
//
//
//    public void setPadding() {
//        int margin = MyApplication.getDefaultSharePadding(mContext);
//        LinearLayout.LayoutParams lps = (LinearLayout.LayoutParams) text.getLayoutParams();
//        lps.leftMargin = margin;
//        text.setLayoutParams(lps);
//    }
//
//    public void setGravity() {
//        boolean isCenter = MyApplication.getDefaultShareGravity(mContext);
//        if (isCenter) {
//            text.setGravity(Gravity.CENTER_HORIZONTAL);
//        } else {
//            text.setGravity(Gravity.LEFT);
//        }
//    }
//
//    public void setTextSize() {
//        int size = MyApplication.getDefaultShareSize(mContext);
//        text.setTextSize(size);
//        title.setTextSize(size + 2);
//        author.setTextSize(size - 2);
//        desc.setTextSize(size - 2);
//    }
//
//    public void setColor() {
//
//        int color = MyApplication.getColorByPos(MyApplication.getDefaultShareColor(mContext));
//        text.setTextColor(color);
//        title.setTextColor(color);
//        author.setTextColor(color);
//        desc.setTextColor(color);
//    }
//
//    public void setAuthor() {
//        if (MyApplication.getDefaultShareAuthor(mContext)) {
//            author.setVisibility(View.VISIBLE);
//        } else {
//            author.setVisibility(View.GONE);
//        }
//    }
//
//}
