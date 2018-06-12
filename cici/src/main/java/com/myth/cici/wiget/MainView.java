package com.myth.cici.wiget;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myth.cici.R;
import com.myth.cici.activity.CiActivity;
import com.myth.cici.activity.CipaiListActivity;
import com.myth.poetrycommon.activity.CommunityActivity;
import com.myth.poetrycommon.activity.WritingSearchActivity;
import com.myth.poetrycommon.db.WritingDatabaseHelper;
import com.myth.poetrycommon.entity.Writing;

import java.util.List;

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
                Intent intent = new Intent(mContext, CommunityActivity.class);
                mContext.startActivity(intent);
            }
        });

        TextView writing = (TextView) findViewById(R.id.writing);
        writing.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                List<Writing> writings = WritingDatabaseHelper.getAllWriting();
                if (writings.isEmpty()) {
                    Toast.makeText(mContext, R.string.writing_empty, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(mContext, WritingSearchActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }

}
