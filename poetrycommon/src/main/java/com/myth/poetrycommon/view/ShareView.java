package com.myth.poetrycommon.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.R;
import com.myth.poetrycommon.db.FormerDatabaseHelper;
import com.myth.poetrycommon.entity.Writing;
import com.myth.poetrycommon.utils.ResizeUtils;
import com.myth.poetrycommon.utils.StringUtils;


public class ShareView extends ScrollView {

    private Context mContext;

    private Writing writing;

    private TextView text;

    private TextView title;

    private TextView author;

    private TextView desc;

    public MirrorLoaderView getBackgroundView() {
        return backgroundView;
    }

    private MirrorLoaderView backgroundView;

    public ImageView getPictureView() {
        return pictureView;
    }

    private ImageView pictureView;

    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_BACKGROUND = 1;
    public static final int TYPE_PICTURE = 2;


    public void setType(int type) {
        this.type = type;
    }

    private int type;

    public ShareView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initView();
    }

    public ShareView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public ShareView(Context context, Writing writing) {
        super(context);
        mContext = context;
        initView();
        setWriting(writing);
    }


    public void setWriting(Writing writing) {
        this.writing = writing;
        if (writing.former == null) {
            writing.former = FormerDatabaseHelper.getFormerById(writing.formerId);
        }
        refresh();
    }

    private void initView() {
        setFillViewport(true);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_share, this);

        backgroundView = (MirrorLoaderView) findViewById(R.id.background_view);
        pictureView = (ImageView) findViewById(R.id.picture_view);

        backgroundView.setMinimumHeight(ResizeUtils.getInstance().resize(540));
        pictureView.setMinimumHeight(ResizeUtils.getInstance().resize(540));

        title = (TextView) findViewById(R.id.title);
        text = (TextView) findViewById(R.id.text);
        author = (TextView) findViewById(R.id.author);
        desc = (TextView) findViewById(R.id.desc);
    }

    public void refresh() {
        if (type == TYPE_NORMAL) {
            if (StringUtils.isNumeric(writing.bgimg)) {
                backgroundView.setDrawableId(BaseApplication.bgImgList[Integer.parseInt(writing.bgimg)]);
            } else {
                pictureView.setImageDrawable(new BitmapDrawable(getResources(), writing.bgimg));
            }
        } else if (type == TYPE_BACKGROUND) {
            if (StringUtils.isNumeric(writing.bgimg)) {
                backgroundView.setDrawableId(BaseApplication.bgImgList[Integer.parseInt(writing.bgimg)]);
            } else {
                backgroundView.setDrawableId(BaseApplication.bgImgList[0]);
            }
        } else {
            if (TextUtils.isEmpty(writing.bgimg)) {
                pictureView.setImageResource(R.drawable.zuibaichi);
            } else {
                pictureView.setImageDrawable(new BitmapDrawable(getResources(), writing.bgimg));
            }
        }

        text.setText(writing.content);
        if (TextUtils.isEmpty(writing.title)) {
            title.setText(writing.former.name);
        } else {
            title.setText(writing.title);
        }

        if (TextUtils.isEmpty(writing.author)) {
            author.setText(BaseApplication.getDefaultUserName());
        } else {
            author.setText(writing.author);
        }

        if (TextUtils.isEmpty(writing.desc)) {
            desc.setVisibility(View.GONE);
        } else {
            desc.setVisibility(View.VISIBLE);
            desc.setText(writing.desc);
        }

        setTextSize();
        setGravity();
        setPadding();
        setColor();
        setAuthor();
    }


    public void setPadding() {
        int margin = BaseApplication.getDefaultSharePadding(mContext);
        LinearLayout.LayoutParams lps = (LinearLayout.LayoutParams) text.getLayoutParams();
        lps.leftMargin = margin;
        text.setLayoutParams(lps);
    }

    public void setGravity() {
        boolean isCenter = BaseApplication.getDefaultShareGravity(mContext);
        if (isCenter) {
            text.setGravity(Gravity.CENTER_HORIZONTAL);
        } else {
            text.setGravity(Gravity.LEFT);
        }
    }

    public void setTextSize() {
        int size = BaseApplication.getDefaultShareSize(mContext);
        text.setTextSize(size);
        title.setTextSize(size + 2);
        author.setTextSize(size - 2);
        desc.setTextSize(size - 2);
    }

    public void setColor() {

        int color = BaseApplication.instance.getColorByPos(BaseApplication.getDefaultShareColor(mContext));
        text.setTextColor(color);
        title.setTextColor(color);
        author.setTextColor(color);
        desc.setTextColor(color);
    }

    public void setAuthor() {
        if (BaseApplication.getDefaultShareAuthor(mContext)) {
            author.setVisibility(View.VISIBLE);
        } else {
            author.setVisibility(View.GONE);
        }
    }

}
