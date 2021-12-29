package com.myth.cici.wiget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.myth.cici.R;
import com.myth.cici.activity.CiActivity;
import com.myth.cici.activity.CiSearchActivity;
import com.myth.cici.activity.CipaiListActivity;
import com.myth.poetrycommon.activity.WritingSearchActivity;
import com.myth.poetrycommon.utils.OthersUtils;

public class MainView extends FrameLayout {

    private Context mContext;

    public MainView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    private void initView() {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.layout_main, this);

        ViewGroup showAll = (ViewGroup) findViewById(R.id.show_all);
        showAll.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, CipaiListActivity.class));
            }
        });

        TextView showOne = (TextView) findViewById(R.id.show_one);
        showOne.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CiActivity.class);
                mContext.startActivity(intent);
            }
        });

        TextView share = (TextView) findViewById(R.id.community);
        share.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                OthersUtils.startCommunity(mContext);
            }
        });

        TextView writing = (TextView) findViewById(R.id.writing);
        writing.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext).setItems(
                        new String[]{mContext.getString(R.string.search_ci), mContext.getString(R.string.search_collect), mContext.getString(R.string.search_writing)},
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                if (which == 0) {
                                    Intent intent = new Intent(mContext,
                                            CiSearchActivity.class);
                                    mContext.startActivity(intent);
                                } else if (which == 1) {
                                    Intent intent = new Intent(mContext,
                                            CiSearchActivity.class);
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
    }

}
