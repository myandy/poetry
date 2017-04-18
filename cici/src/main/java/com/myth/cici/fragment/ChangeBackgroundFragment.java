package com.myth.cici.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.myth.cici.MyApplication;
import com.myth.cici.R;
import com.myth.cici.adapter.ImageAdapter;
import com.myth.cici.entity.Writing;
import com.myth.cici.wiget.HorizontalListView;
import com.myth.cici.wiget.ShareView;
import com.myth.poetrycommon.utils.ResizeUtil;
import com.myth.poetrycommon.utils.StringUtils;

public class ChangeBackgroundFragment extends Fragment {

    private Context mContext;

    private Writing writing;

    private int bg_index = 0;

    private ShareView shareView;

    private  HorizontalListView listView;

    public ChangeBackgroundFragment() {
    }

    public static ChangeBackgroundFragment getInstance(Writing writing) {
        ChangeBackgroundFragment fileViewFragment = new ChangeBackgroundFragment();
        fileViewFragment.writing = writing;
        if (StringUtils.isNumeric(writing.getBgimg())) {
            fileViewFragment.bg_index = Integer.parseInt(writing.getBgimg());
        }
        return fileViewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mContext = inflater.getContext();
        View view = inflater.inflate(R.layout.fragment_background, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onStop() {
        super.onStop();
        save();
    }

    public void save() {
        writing.setBgimg(bg_index + "");
    }

    private void refresh() {
        writing.setBgimg(bg_index + "");
        shareView.setWriting(writing);
    }


    private void initViews(View view) {
        listView = (HorizontalListView) view.findViewById(R.id.imgs);

        ImageAdapter adapter = new ImageAdapter(mContext);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bg_index = position;
                shareView.getBackgroundView().setDrawableId(MyApplication.bgimgList[position]);
            }
        });

        shareView = (ShareView) view.findViewById(R.id.share_view);
        shareView.setType(ShareView.TYPE_BACKGROUND);
        ResizeUtil.getInstance().layoutSquareView(shareView);
    }


}
