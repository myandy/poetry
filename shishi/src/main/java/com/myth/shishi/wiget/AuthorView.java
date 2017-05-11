package com.myth.shishi.wiget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myth.poetrycommon.BaseApplication;
import com.myth.poetrycommon.utils.ResizeUtils;
import com.myth.poetrycommon.view.CircleImageView;
import com.myth.shishi.R;
import com.myth.shishi.activity.PoetrySearchActivity;
import com.myth.shishi.entity.Author;


public class AuthorView extends RelativeLayout {

    private Context mContext;

    private Author author;

    private View root;

    public AuthorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public AuthorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AuthorView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public AuthorView(Context context, Author author) {
        super(context);
        this.author = author;
        mContext = context;
        initView();
    }


    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.layout_author, null);

        LinearLayout topView = (LinearLayout) root.findViewById(R.id.right);
        LayoutParams param = new LayoutParams(ResizeUtils.getInstance().dip2px(80), ResizeUtils.getInstance().dip2px(120));
        CircleImageView dirView = new CircleImageView(mContext, author.color, R.drawable.director);
        topView.addView(dirView, 1, param);

        dirView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PoetrySearchActivity.class);
                intent.putExtra("author", author);
                mContext.startActivity(intent);
            }
        });

        TextView content = (TextView) root.findViewById(R.id.content);

        content.setText(author.intro);

        TextView title = (TextView) root.findViewById(R.id.title);

        title.setText(author.author);

        addView(root, new LayoutParams(-1, -1));
        setTextSize();
    }

    private void setTextSize() {
        int size = BaseApplication.getDefaultTextSize(mContext);
        ((TextView) root.findViewById(R.id.content)).setTextSize(size);
    }

}
