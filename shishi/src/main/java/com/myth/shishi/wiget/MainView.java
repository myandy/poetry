package com.myth.shishi.wiget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.myth.poetrycommon.activity.CommunityActivity;
import com.myth.poetrycommon.activity.WebViewActivity;
import com.myth.poetrycommon.activity.WritingSearchActivity;
import com.myth.shishi.R;
import com.myth.shishi.activity.AIPoetryActivity;
import com.myth.shishi.activity.AuthorListActivity;
import com.myth.shishi.activity.AuthorSearchActivity;
import com.myth.shishi.activity.PoetryActivity;
import com.myth.shishi.activity.PoetrySearchActivity;

public class MainView extends FrameLayout {

    private Context mContext;

    public MainView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MainView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View root = inflater.inflate(R.layout.layout_main, this);

        ViewGroup showAll = (ViewGroup) root.findViewById(R.id.show_all);
        showAll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,
                        AuthorListActivity.class));
            }
        });

        root.findViewById(R.id.duishi).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AIPoetryActivity.class);
                mContext.startActivity(intent);
            }
        });

        root.findViewById(R.id.duilian).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                WebViewActivity.start(mContext, "https://ai.binwang.me/couplet");
            }
        });

        TextView showOne = (TextView) root.findViewById(R.id.show_one);
        showOne.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PoetryActivity.class);
                mContext.startActivity(intent);
            }
        });

        TextView search = (TextView) root.findViewById(R.id.search);
        search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext).setItems(
                        new String[]{mContext.getString(R.string.search_author), mContext.getString(R.string.search_poetry), mContext.getString(R.string.search_collect), mContext.getString(R.string.search_writing)},
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if (which == 0) {
                                    Intent intent = new Intent(mContext,
                                            AuthorSearchActivity.class);
                                    mContext.startActivity(intent);
                                } else if (which == 1) {
                                    Intent intent = new Intent(mContext,
                                            PoetrySearchActivity.class);
                                    mContext.startActivity(intent);
                                } else if (which == 2) {
                                    Intent intent = new Intent(mContext,
                                            PoetrySearchActivity.class);
                                    intent.putExtra("collect", true);
                                    mContext.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(mContext,
                                            WritingSearchActivity.class);
                                    mContext.startActivity(intent);
                                }
                                dialog.dismiss();
                            }
                        }).show();

            }
        });

        TextView share = (TextView) root.findViewById(R.id.share);
        share.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommunityActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

}
